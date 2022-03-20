<script setup>
import http from "../../http-common";
import {ref, onMounted, onBeforeUpdate} from "vue";

const miners = ref([]);

async function getMiners(){
  let response = await http.get("/miners")
  miners.value = response.data;
}

async function deleteMiners(miner){
  http.delete("/miners/"+miner)
  console.log("The miner "+ miner +" has been deleted")
}

async function addMiner(){
    var name
    var port
    do{
      name = Math.random().toString(36).slice(2);
      port = "33"+ (Math.floor(Math.random() * (900 - 100) + 1) + 100)
    } while(!checkUnique(name,port))
    http.post("/miners/"+name+"/port/"+port)
    console.log("The miner "+name+ " has been created on the network port "+port)
}

function checkUnique(miner, port){
    var results = miners.value.filter(element => {
      return (element.name == miner || element.port == port);
    })
    return results.length == 0
}

onMounted(() => {
    getMiners();
})

onBeforeUpdate(() => {
    getMiners();
})
</script>

<template>
  <div class="table-area mx-5 custom-scroll col-8">
      <table class="table table-striped table-responsive mb-0">
        <thead class="bg-light sticky-top center">
            <tr id="thead" scope="col">
            <th scope="col" class="fw-bold">Id</th>
            <th scope="col" class="fw-bold">Name</th>
            <th scope="col" class="fw-bold">Port</th>
            <th scope="col" class="fw-bold">Action</th>
            </tr>
        </thead>
        <tbody class="center">
            <tr v-for="item in miners" :key='item.id'>
              <td>{{ item.id }} </td>
              <td>{{ item.name }}</td>
              <td>{{ item.port }}</td>
              <td>
                <button id="delete" class="btn center btn-color"  v-on:click="deleteMiners(item.name)">
                        <i class="bi bi-trash3-fill text-white"></i>
                </button>
              </td>
            </tr>
        </tbody>
        </table>
  </div>
  <div class="align-self-start ms-4">
    <button id="add" class="btn btn-color center text-white" v-on:click="addMiner()">
        <i class="bi bi-person-plus-fill text-white"></i> 
        Add a miner
    </button>
  </div>
</template>

<style>
.table-area {
    overflow-y: auto;
    border-radius: 6px;
}

.btn-color{
  background-color: #183A41 !important;
}

</style>
