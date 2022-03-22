# An educational blockchain
## Run
The blockchain and the web interface are in the same docker container and can be runned in the same time with this command :
```docker-compose up --build``` → in the main repository.
Open ```http://localhost:3000``` and enjoy !

## Context
This project aims to implement a naive blockchain to analyse its behaviour in a local and educational environment. This project implements a Spring Boot API to retrieve all data related to the Blockchain and a Vue.js web interface to visualize and monitor everything.
This three-weeks-project was made during our MIAGE Master's degree (2022) at the school [IDMC](https://idmc.univ-lorraine.fr/) in Nancy and was supervised by our professor K. Smaili.
The contributors are Camille BEIRAO, Valentine BOUCHÉ and Myriam AIT HSAINE.

### Miners
A miner is a peer in the network who can store and mine blocks. Each miner is connected to other miners in the
network.
1. Send a message to other miners, with the aim of spreading the mined block
2. Receive a message from other miners, in order to save the mined blocks of other miners
3. Mining, validating blocks on its own blockchain
4. Synchronise with the latest updated blockchain with other miners

The mining algorithm is the key to the proof of work of this blockchain, here we will only use the SHA256 function for this. The mining difficulty is set at 16 binary zeros on the left of the block's but it evolves one by one when adding or removing a miner.

### API Requests
Port of the API : ```http://localhost:8080/blockchain```

#### Get a miner with his name
GET ```/miners/{name}```

#### Delete a miner with his name
DELETE ```/miners/{name}```

#### Add a miner with his name and port
POST ```/miners/{name}/port/{port}```

#### Add 5 sample miners
POST ```/miners/testSample```

#### Get all miners
GET ```/miners```

#### Get all blocks
GET ```/blocks```

#### Delete every miners and therefore the blockchain
DELETE ```/all```

#### Get the number of blocks mined per miner
GET ```/blocks/miners```

#### Get the average mining time of all blocks
GET ```/average/miningTime```

#### Get the last mining time
GET ```/last/miningTime```

#### Get the average costs of the transactions per block
GET ```/average/transactions```

#### Get the costs of the transactions of the last block
GET ```/last/transactions```

This project is inspired by [naivechain](https://github.com/lhartikk/naivechain) and [blockchain](https://github.com/Will1229/Blockchain). 
