package org.miage.simulationblockchain.entity;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Getter
@Builder
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    private int sender;
    private int receiver;
    private MessageEnum type;
    private List<Block> blocks;

    @Override
    public String toString() {
        return String.format("Message {type=%s, sender=%d, receiver=%d, blocks=%s}", type, sender, receiver, blocks);
    }
}
