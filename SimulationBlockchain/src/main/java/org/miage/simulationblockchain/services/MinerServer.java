package org.miage.simulationblockchain.services;

import org.apache.log4j.Logger;
import org.miage.simulationblockchain.entity.Message;
import org.miage.simulationblockchain.entity.MessageEnum;
import org.miage.simulationblockchain.entity.Miner;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MinerServer extends Thread {
    private static final Logger log = Logger.getLogger(MinerServer.class);
    private final Miner miner;
    private final Socket client;

    public MinerServer(final Miner miner, final Socket client) {
        super(miner.getName() + System.currentTimeMillis());
        this.miner = miner;
        this.client = client;
    }

    @Override
    public void run() {
        try (
                ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
                final ObjectInputStream in = new ObjectInputStream(client.getInputStream())) {
            Message message = Message.builder().sender(miner.getPort()).type(MessageEnum.READY).build();
            out.writeObject(message);
            Object fromClient;
            boolean stop = false;
            while (!stop && (fromClient = in.readObject()) != null) {
                if (fromClient instanceof Message msg) {
                    log.info(String.format("%d received: %s", miner.getPort(), fromClient));
                    if (MessageEnum.NEW_BLOCK_CREATED == msg.getType()) {
                        if (msg.getBlocks().size() != 1) {
                            log.error("Invalid block received: " + msg.getBlocks());
                        }
                        synchronized (miner) {
                            miner.stopHost();
                            miner.addBlock(msg.getBlocks().get(0));
                            miner.startHost();
                        }
                        stop = true;
                    } else if (MessageEnum.REQUEST_BLOCKCHAIN == msg.getType()) {
                        out.writeObject(Message.builder()
                                .sender(miner.getPort()).type(MessageEnum.RESPONSE_BLOCKCHAIN)
                                .blocks(miner.getBlocks()).build());
                        stop = true;
                    }
                }
            }
            client.close();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
}
