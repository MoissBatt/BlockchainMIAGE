<script setup>
import http from "../http-common";
import {ref} from "vue";

async function createBlockchain(){
  http.post("/miners/testSample")
}

async function deleteBlockchain(){
  http.delete("/all")
}
</script>

<template>
<nav class="navbar navbar-expand-lg">
  <div class="container-fluid">
    <RouterLink class="navbar-brand active" to="/">BlockchainTools</RouterLink>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav">
        <li class="nav-item">
          <RouterLink class="nav-link active" to="/blocks">
            <i class="bi bi-box"></i>
            Blocks
          </RouterLink>
        </li>
        <li class="nav-item">
          <RouterLink class="nav-link" to="/miners">
            <i class="bi bi-hammer"></i>
            Miners
          </RouterLink>
        </li>
        <li class="nav-item">
          <RouterLink class="nav-link" to="/stats">
            <i class="bi bi-bar-chart-fill"></i>
            Stats
          </RouterLink>
        </li>
        <li class="nav-item">
          <RouterLink class="nav-link" to="/info">
            <i class="bi bi-info-circle-fill"></i>
            Info
          </RouterLink>
        </li>
      </ul>
      <div class="ms-auto">
        <label type="button" id="launch-button" class="btn text-white" title="Add 5 miners, launches the blockchain if no miner was already present" v-on:click="createBlockchain">
          <i class="bi bi-play-fill"></i>
          Bootstrap
        </label>
        <label type="button" id="stop-button" class="btn text-white ms-4" v-on:click="deleteBlockchain" title="Delete every miners and therefore the blockchain">
          <i class="bi bi-stop-circle"></i>
          Delete
        </label>
      </div>
    </div>
  </div>
</nav>
</template>

<style>

.navbar {
   background-color: #388697;
   border-top-left-radius: 6px;
   border-top-right-radius: 6px;
}
.nav-link, .navbar-brand{
   color: #fafafa !important;
   font-weight: bold;
}

#launch-button {
  background-color: #62A87C;
}

#launch-button:hover {
  background-color: #509168;
}

#stop-button {
  background-color: #b12e3b;
}

#stop-button:hover {
  background-color: #882a33;
}
    
</style>