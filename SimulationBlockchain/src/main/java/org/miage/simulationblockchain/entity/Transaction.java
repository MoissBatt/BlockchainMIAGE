package org.miage.simulationblockchain.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

@Getter
@Setter
public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;
    private String sender;
    private String receiver;
    private float cost;

    public Transaction(Random rand) {
        this.sender = (UUID.randomUUID()).toString();
        this.receiver = (UUID.randomUUID()).toString();
        this.cost = Math.round(1 + rand.nextFloat() * (100 - 1)*100)/100F;
    }

    public String toString() {
        return sender + " -> " + receiver + " : " + cost;
    }
}
