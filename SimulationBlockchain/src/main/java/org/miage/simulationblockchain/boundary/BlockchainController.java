package org.miage.simulationblockchain.boundary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.miage.simulationblockchain.entity.Block;
import org.miage.simulationblockchain.entity.Miner;
import org.miage.simulationblockchain.entity.Transaction;
import org.miage.simulationblockchain.repositories.Network;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping(path = "/blockchain", produces = MediaType.APPLICATION_JSON_VALUE)
public class BlockchainController {
    private static Network network = new Network();

    @GetMapping(value = "/miners/{name}")
    public ResponseEntity<Miner> getMiner(@PathVariable("name") String name) {
        Miner miner = network.getMiner(name);
        if (miner == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(miner);
    }

    @DeleteMapping(value = "/miners/{name}")
    public ResponseEntity<Void> deleteMiner(@PathVariable("name") String name) {
        network.deleteMiner(name);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/miners/{name}/port/{port}")
    public ResponseEntity<Miner> addMiner(@PathVariable("name") String name, @PathVariable("port") int port) {
        Miner created = network.addMiner(name, port);
        if (created == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.created(URI.create("/miners/" + created.getName())).body(created);
    }

    @PostMapping(value = "/miners/testSample")
    public ResponseEntity<URI> addMiners() {
        network.addMiner("Valentine", 33980);
        network.addMiner("Camille", 33990);
        network.addMiner("Myriam", 33992);
        network.addMiner("Jérémy", 33960);
        network.addMiner("Vincent", 33991);
        return ResponseEntity.created(URI.create("/miners/")).build();
    }

    @GetMapping(value = "/miners")
    public ResponseEntity<String> getAllMiners() throws JsonProcessingException {
        List<Miner> miners = network.getMiners();
        ObjectMapper mapper = new ObjectMapper();
        return ResponseEntity.ok(mapper.writeValueAsString(miners));
    }

    @GetMapping(value = "/blocks")
    public ResponseEntity<String> getAllBlocks() throws JsonProcessingException {
        List<Miner> miners = network.getMiners();
        ObjectMapper mapper = new ObjectMapper();
        if (miners.isEmpty())
            return ResponseEntity.ok(mapper.writeValueAsString(new ArrayList<>()));
        return ResponseEntity.ok(mapper.writeValueAsString(miners.get(0).getBlocks()));
    }

    @GetMapping(value = "/blocks/miners")
    public ResponseEntity<String> getBlocksPerMiner() throws JsonProcessingException {
        List<Miner> miners = network.getMiners();
        ObjectMapper mapper = new ObjectMapper();
        if (miners.isEmpty())
            return ResponseEntity.ok(mapper.writeValueAsString(new ArrayList<>()));
        List<Block> blocks = miners.get(0).getBlocks();
        Map<String, List<Block>> result = blocks.stream()
                .collect(groupingBy(Block::getCreator));
        Map<String, Integer> finalResult = result.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().size())
                );
        finalResult = finalResult.entrySet().stream().sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
        finalResult.remove("ORIGIN");
        return ResponseEntity.ok(mapper.writeValueAsString(finalResult));
    }

    @DeleteMapping(value = "/all")
    public ResponseEntity<Void> deleteBlockchain() {
        network.deleteAllMiners();
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/average/miningTime")
    public ResponseEntity<Float> getAverageMiningTime() {
        List<Miner> miners = network.getMiners();
        if (miners.isEmpty())
            return ResponseEntity.ok(0F);
        List<Block> blocks = miners.get(0).getBlocks();

        long totalTime = blocks.get(blocks.size() - 1).getTimestamp() - blocks.get(0).getTimestamp();
        float result = (float) totalTime / (float) blocks.size() / 1000;
        result = Math.round(result * 100)/100F;
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/last/miningTime")
    public ResponseEntity<Float> getLastMiningTime() {
        List<Miner> miners = network.getMiners();
        if (miners.isEmpty() || miners.get(0).getBlocks().size() < 2)
            return ResponseEntity.ok(0F);

        List<Block> blocks = miners.get(0).getBlocks();
        long totalTime = blocks.get(blocks.size() - 1).getTimestamp() - blocks.get(blocks.size() - 2).getTimestamp();
        float result = Math.round(totalTime / 1000F * 100)/100F;
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/average/transactions")
    public ResponseEntity<Float> getAverageTransactions() {
        List<Miner> miners = network.getMiners();
        if (miners.isEmpty() || miners.get(0).getBlocks().size() == 1)
            return ResponseEntity.ok(0F);

        List<Block> blocks = miners.get(0).getBlocks().subList(1, miners.get(0).getBlocks().size());
        List<Transaction> transactions = blocks.stream()
                .map(Block::getData)
                .reduce(new ArrayList<>(), (acc, element) -> Stream.concat(acc.stream(), element.stream())
                        .toList());

        float result = transactions.stream()
                .map(Transaction::getCost)
                .reduce(0.0F, Float::sum);
        result =  Math.round(result / blocks.size() * 100)/100F;
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/last/transactions")
    public ResponseEntity<Float> getLastTransactions() {
        List<Miner> miners = network.getMiners();
        if (miners.isEmpty() || miners.get(0).getBlocks().size() <= 1)
            return ResponseEntity.ok(0F);

        List<Block> blocks = miners.get(0).getBlocks();
        List<Transaction> transactions = blocks.get(blocks.size() - 1).getData();
        float result = transactions.stream()
                .map(Transaction::getCost)
                .reduce(0.0F, Float::sum);
        result =  Math.round(result * 100)/100F;
        return ResponseEntity.ok(result);
    }

}
