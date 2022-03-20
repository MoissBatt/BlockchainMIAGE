package org.miage.simulationblockchain.repositories;

import lombok.Getter;
import org.miage.simulationblockchain.services.ProofOfWork;
import org.miage.simulationblockchain.entity.Miner;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Network {
    private List<Miner> miners = new ArrayList<>();

    public Miner addMiner(String name, int port) {
        if (!checkNameAndPortAvailability(name, port))
            return null;
        Miner miner = new Miner(name, "localhost", port, miners);
        miner.startHost();
        miner.startMine();
        miners.add(miner);
        ProofOfWork.changeDifficulty(miners.size());
        return miner;
    }

    private boolean checkNameAndPortAvailability(String name, int port) {
        Miner miner1 = miners.stream().filter(element ->
                element.getName().equals(name)).findAny().orElse(null);
        Miner miner2 = miners.stream().filter(element ->
                element.getPort() == port).findAny().orElse(null);
        return (miner1 == null && miner2 == null);
    }

    public Miner getMiner(String name) {
        return miners.stream().filter(element ->
                element.getName().equals(name)).findAny().orElse(null);
    }

    public void deleteMiner(String name) {
        final Miner miner = getMiner(name);
        if (miner != null) {
            miners.remove(miner);
            miner.stopHost();
            for (Miner toClean : miners) {
                toClean.deletePeer(miner);
            }
        }
        ProofOfWork.changeDifficulty(miners.size());
    }

    public void deleteAllMiners() {
        for (Miner miner : miners) {
            miner.stopHost();
        }
        miners.clear();
        ProofOfWork.changeDifficulty(0);
    }
}
