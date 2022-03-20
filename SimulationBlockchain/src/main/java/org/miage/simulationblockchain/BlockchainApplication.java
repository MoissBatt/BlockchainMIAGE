package org.miage.simulationblockchain;

import org.miage.simulationblockchain.services.ProofOfWork;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlockchainApplication {
    public static void main(String[] args) {
        ProofOfWork.changeDifficulty(0);
        SpringApplication.run(BlockchainApplication.class, args);
    }
}
