package org.miage.simulationblockchain.services;

import org.miage.simulationblockchain.entity.Block;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ProofOfWork implements Serializable {
    public static final int INITIAL_DIFFICULTY = 16;
    private static int difficulty;
    private Block block;
    private BigInteger target;
    private final Random rand = new Random();

    public ProofOfWork(Block block) {
        this.block = block;
        target = BigInteger.ONE;
        target = target.shiftLeft(256 - ProofOfWork.difficulty);
    }

    public static void changeDifficulty(int numberMiners) {
        ProofOfWork.difficulty = INITIAL_DIFFICULTY + numberMiners;
    }

    private String prepareData(int nonce) {
        return block.getPreviousHash() +
                block.getData().toString() +
                block.getTimestamp() +
                Integer.toHexString(target.intValue()) +
                Integer.toHexString(nonce);
    }

    public Map<String, String> run() {
        String hash = "";
        int nonce = rand.nextInt(500000);
        boolean solved = true;
        while (solved) {
            hash = HashService.hash256(prepareData(nonce));
            BigInteger hashInt = new BigInteger(hash, 16);
            if (hashInt.compareTo(target) < 0) {
                solved = false;
            } else {
                nonce++;
            }
        }
        String validation = block.getPreviousHash() +
                block.getData().toString() +
                block.getTimestamp() +
                Integer.toHexString(target.intValue()) +
                Integer.toHexString(nonce);
        if (HashService.hash256(validation).equals(hash)) {
            Map<String, String> map = new HashMap<>(100);
            map.put("nonce", nonce + "");
            map.put("hash", hash);
            return map;
        } else {
            return Collections.emptyMap();
        }
    }
}
