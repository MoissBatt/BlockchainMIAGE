<template>
    <div class=" mt-4 px-4">
        <div class="d-flex">
            <div>
                <h1 class="title">About this website</h1>
                <h2 class="title2">Context</h2>
                <p class="text">This website is a project of our Master's Degree at the IDMC supervised by our teacher K. Smaili.
                    We implemented a case study following a literature review about the blockchain. The main goal is to be able to monitor a 
                    blockchain in order to understand how it works with a total control on the networks nodes.
                </p>
            </div>
            <div class="ms-auto">
                    <a href="https://idmc.univ-lorraine.fr/" target="_blank">
                        <img class="logo" src="@/assets/idmc.png" alt="IDMC">
                    </a>
            </div>
        </div>
        <div>
            <h2 class="title2">Uses</h2>
            <p class="text">This web interface is meant to be locally connected to an API that you can find <a href="https://www.youtube.com/watch?v=dQw4w9WgXcQ" target="_blank">
                    here</a>. In this app, it is possible to add a miner, delete one, add a sample of five miners ("Bootstrap" button) and
                    delete the blockchain ("Delete" button). It is possible to monitor the creation of the blocks and their transactions in
                    the "blocks" tab and to follow some statistics in the "stats" tab with two charts : the amount of blocks mined by every miners
                    and the costs of the transactions for every blocks. It is also possible to check the average mining time for example.
            </p>
        </div>
        
    </div>
</template>

<style>
.logo{
    width: 13rem;
}

.title{
    color:#183A41;
    font-size: 30px;
}

.title2{
    color:#183A41;
    font-size: 20px;
}

.text{
    color:#183A41; 
    text-align: justify;
}

</style>
