package org.miage.simulationblockchain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import org.apache.log4j.Logger;
import org.miage.simulationblockchain.services.MinerServer;
import org.miage.simulationblockchain.repositories.Blockchain;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class Miner {
    @Getter(AccessLevel.NONE)
    private static final Logger log = Logger.getLogger(Miner.class);
    @Getter(AccessLevel.NONE)
    private final ThreadFactory factory = new ThreadFactory() {
        private AtomicInteger cnt = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "sync-" + cnt.getAndIncrement());
        }
    };
    private final String id;
    private String name;
    private String address;
    private int port;
    @Getter(AccessLevel.NONE)
    private List<Miner> peers;
    private boolean listening = true;
    @Getter(AccessLevel.NONE)
    private Blockchain blockchain;
    @Getter(AccessLevel.NONE)
    private ServerSocket serverSocket;
    @Getter(AccessLevel.NONE)
    private ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(10);

    public Miner() {
        id = UUID.randomUUID().toString();
    }

    public Miner(final String name, final String address, final int port, final List<Miner> miners) {
        id = UUID.randomUUID().toString();
        this.name = name;
        this.address = address;
        this.port = port;
        this.peers = miners;
        blockchain = new Blockchain();
    }

    public void createBlock() {
        Block previousBlock = getLatestBlock();
        if (blockchain.isEmpty() || previousBlock == null)
            return;
        final int index = previousBlock.getIndex() + 1;
        final Block block = new Block(index, previousBlock.getHash(), name);
        broadcast(MessageEnum.NEW_BLOCK_CREATED, block);
    }

    public void startHost() {
        executor.execute(() -> {
            try {
                serverSocket = new ServerSocket(port);
                log.info(String.format("Server %s started", serverSocket.getLocalPort()));
                listening = true;
                while (listening) {
                    final MinerServer thread = new MinerServer(Miner.this, serverSocket.accept());
                    thread.start();
                }
                serverSocket.close();
            } catch (IOException e) {
                log.error("Could not listen to port " + port);
            }
        });
        broadcast(MessageEnum.REQUEST_BLOCKCHAIN, null);
    }

    public void stopHost() {
        listening = false;
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startMine() {
        executor.execute(() -> {
            try {
                while (true) {
                    createBlock();
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                log.warn("Interrupted!", e);
                Thread.currentThread().interrupt();
            }
        });
    }

    private void broadcast(MessageEnum type, final Block block) {
        peers.forEach(peer -> sendMessage(type, peer.getAddress(), peer.getPort(), block));
    }

    private void sendMessage(MessageEnum type, String address, int port, Block... blocks) {
        try (
                final Socket peer = new Socket(address, port);
                final ObjectOutputStream out = new ObjectOutputStream(peer.getOutputStream());
                final ObjectInputStream in = new ObjectInputStream(peer.getInputStream())) {
            Object fromPeer;
            while ((fromPeer = in.readObject()) != null) {
                if (fromPeer instanceof Message msg) {
                    log.info(String.format("%d received: %s", this.port, msg));
                    if (MessageEnum.READY == msg.getType()) {
                        Message message = new Message(this.port, port, type, Arrays.asList(blocks));
                        out.writeObject(message);
                    } else if (MessageEnum.RESPONSE_BLOCKCHAIN == msg.getType()) {
                        if (!msg.getBlocks().isEmpty()) {
                            blockchain.add(msg.getBlocks());
                        }
                        break;
                    }
                }
            }
        } catch (UnknownHostException e) {
            log.error(String.format("Unknown host %s %d", address, port));
        } catch (IOException e) {
            log.error(String.format("%s couldn't get I/O for the connection to %s. Retrying...%n", getPort(), port));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e1) {
                log.warn("Interrupted!", e1);
                Thread.currentThread().interrupt();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Block getLatestBlock() {
        if (blockchain.isEmpty()) {
            return null;
        }
        return blockchain.getLatestBlock();
    }

    public void addBlock(Block block) {
        if (isBlockValid(block)) {
            blockchain.add(block);
        }
    }

    private boolean isBlockValid(final Block block) {
        final Block latestBlock = getLatestBlock();
        if (latestBlock == null)
            return false;
        final int expected = latestBlock.getIndex() + 1;
        if (block.getIndex() != expected) {
            log.info(String.format("Invalid index. Expected: %s Actual: %s", expected, block.getIndex()));
            return false;
        }
        if (!Objects.equals(block.getPreviousHash(), latestBlock.getHash())) {
            log.info("Unmatched hash code");
            return false;
        }
        return true;
    }

    @JsonIgnore
    public List<Block> getBlocks() {
        return blockchain.getBlocks();
    }

    public void deletePeer(Miner miner) {
        int index = peers.indexOf(miner);
        if (index != -1)
            peers.get(index).stopHost();
        peers.remove(miner);
    }
}
