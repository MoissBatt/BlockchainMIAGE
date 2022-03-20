package org.miage.simulationblockchain.entity;

import lombok.*;
import org.miage.simulationblockchain.services.ProofOfWork;
import org.miage.simulationblockchain.services.HashService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Block implements Serializable {
    private static final long serialVersionUID = 1L;

    protected int index;
    protected Long timestamp;
    protected String hash;
    protected String previousHash;
    protected String creator;
    protected Integer nonce;
    private List<Transaction> data;

    @Getter(AccessLevel.NONE)
    private ProofOfWork proofOfWork;
    @Getter(AccessLevel.NONE)
    private Random rand = new Random();

    public Block(int index, String preHash, String creator) {
        this.index = index;
        this.previousHash = preHash;
        this.creator = creator;
        this.data = generateRandomTransactions();
        timestamp = System.currentTimeMillis();
        hash = HashService.hash256(index + previousHash + timestamp);

        proofOfWork = new ProofOfWork(this);
        Map<String, String> map = proofOfWork.run();

        if (!map.isEmpty()) {
            this.nonce = Integer.parseInt(map.get("nonce"));
            this.hash = map.get("hash");
        }

    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    private List<Transaction> generateRandomTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        int number = 1 + rand.nextInt(9);
        for (int i = 0; i < number; i++) {
            transactions.add(new Transaction(rand));
        }
        return transactions;
    }

    @Override
    public String toString() {
        return "Block{index=" + index + ", timestamp=" + timestamp +
                ", creator=" + creator + ", hash='" + hash + ", previousHash='" + previousHash +
                ", nonce = " + nonce + "}";
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Block block = (Block) o;
        return index == block.index
                && timestamp.equals(block.timestamp)
                && hash.equals(block.hash)
                && previousHash.equals(block.previousHash)
                && creator.equals(block.creator);
    }

    @Override
    public int hashCode() {
        int result = index;
        result = 31 * result + timestamp.hashCode();
        result = 31 * result + hash.hashCode();
        result = 31 * result + previousHash.hashCode();
        result = 31 * result + creator.hashCode();
        return result;
    }
}
