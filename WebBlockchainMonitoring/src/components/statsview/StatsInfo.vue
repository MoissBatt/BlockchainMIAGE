<script setup>
import http from "../../http-common";
import { ref, onMounted, reactive } from 'vue'
const state = reactive({ 
    averageMiningTime: 0, 
    lastMiningTime:0,
    lastTransactions:0,
    averageTransactions:0 })

const avMiningTime = ref();
const laMiningTime = ref();
const laTransactions = ref();
const avTransactions = ref();

async function getValues(){
  avMiningTime.value = await http.get("/average/miningTime")
  laMiningTime.value = await http.get("/last/miningTime")
  laTransactions.value = await http.get("/last/transactions")
  avTransactions.value = await http.get("/average/transactions")
  state.averageMiningTime = avMiningTime.value.data
  state.lastMiningTime = laMiningTime.value.data
  state.lastTransactions = laTransactions.value.data
  state.averageTransactions = avTransactions.value.data
}

onMounted(() => {
    getValues()
    setInterval(() => {
      getValues()}
   ,3000);
})
</script>

<template>
    <div class="content">
        <div>
        <h1 class="title-stats">A few stats</h1> 
        </div>
        <div class="table-area mb-4">
            <table class="table table-striped table-responsive mb-0">
            <tbody >
                <tr>
                    <th class="fw-bold" scope="row">
                        <i class="bi bi-coin"></i>
                        Average cost of the blocks</th>
                    <td>{{ state.averageTransactions }} €</td>
                </tr>
                <tr>
                    <th class="fw-bold" scope="row">
                        <i class="bi bi-coin"></i>
                        Cost of the last block</th>
                    <td>{{ state.lastTransactions }} €</td>
                </tr>
                <tr>
                    <th class="fw-bold" scope="row">
                        <i class="bi bi-stopwatch"></i>
                        Average mining time of the blocks</th>
                    <td>{{ state.averageMiningTime }} seconds</td>
                </tr>
                <tr>
                    <th class="fw-bold" scope="row">
                        <i class="bi bi-stopwatch"></i>
                        Mining time of the last block</th>
                    <td>{{ state.lastMiningTime }} seconds</td>
                </tr>
            </tbody>
            </table>
        </div>
    </div>
</template>

<style> 
.table-area {
    border-radius: 6px;
}

.title-stats{
    text-align: center;
    font-size: 2rem;
}
</style>
