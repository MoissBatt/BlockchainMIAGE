package org.miage.simulationblockchain.repositories;

import lombok.Getter;
import lombok.Setter;
import org.miage.simulationblockchain.entity.Block;
import org.miage.simulationblockchain.services.HashService;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class Blockchain implements Serializable {
    private final Object lock = new Object();
    private List<Block> blocks = new LinkedList<>();

    public Blockchain() {
        Block root = Block.builder()
                .creator("ORIGIN").index(0)
                .timestamp(System.currentTimeMillis()).nonce(0).hash(HashService.hash256("Genesis"))
                .previousHash("0000000000000000000000000000000000000000000000000000000000000000")
                .build();
        add(root);
    }

    public void add(Block block) {
        synchronized (lock) {
            blocks.add(block);
        }
    }

    public void add(List<Block> blockList) {
        synchronized (lock) {
            for (Block block : blockList) {
                boolean found = false;
                for (Block old : blocks) {
                    if (old.getHash().equals(block.getHash())) {
                        found = true;
                        break;
                    }
                }
                if (found) {
                    continue;
                }
                blocks.add(block);
            }
        }
    }

    public boolean isEmpty() {
        return blocks.isEmpty();
    }

    public Block getLatestBlock() {
        return blocks.get(blocks.size() - 1);
    }

    public List<Block> getBlocks() {
        return blocks;
    }
}
