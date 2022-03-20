<script setup>
import Transaction from '@/components/blocksview/Transaction.vue'
import { ref, reactive } from 'vue'
const showModal = ref(false)
const props = defineProps(['blocks'])
const state = reactive({ total: 0 })

async function total(block){
  state.total = 0
  for (let pas = 0; pas < block.data.length; pas++) {
        state.total = state.total + block.data[pas].cost
  }
  state.total = Math.round((state.total + Number.EPSILON) * 100) / 100
}
</script>

<template>
    <div class="table-area mx-4 custom-scroll">
        <table class="table table-striped table-responsive mb-0">
            <thead class="bg-light sticky-top center">
                <tr id="thead" scope="col">
                    <th scope="col" class="fw-bold">#</th>
                    <th scope="col" class="fw-bold">Time</th>
                    <th scope="col" class="fw-bold">Miner</th>
                    <th scope="col" class="fw-bold">Hash</th>
                    <th scope="col" class="fw-bold">Nounce</th>
                    <th scope="col" class="fw-bold">Transactions</th>
                </tr>
            </thead>
            <tbody class="center">
                <tr
                v-for="block in props.blocks"
                :key="block.index">
                    <th scope="row">{{block.index}}</th>
                    <td>{{(new Date(block.timestamp)).toLocaleString()}}</td>
                    <td>{{block.creator}}</td>
                    <td>{{block.hash}}</td>
                    <td>{{block.nonce}}</td>
                    <td class="d-flex">
                        <button id="show-modal" class="btn btn-color center" @click="showModal = true, toShow=block" v-on:click="total(block)">
                            <i class="bi bi-info-circle text-white"></i>
                        </button>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>

<Teleport to="body">
    <transaction :show="showModal" :toShow="block" @close="showModal = false">
        <template #header>
            <h3>Transactions of block #{{toShow.index}}</h3>
        </template>
        <template #body>
            <div class="table-area custom-scroll mb-3">
                <table class="table table-striped table-responsive  mb-0">
                    <thead class="bg-light sticky-top">
                        <tr scope="col">
                        <th scope="col" class="fw-bold">Sender</th>
                        <th scope="col" class="fw-bold">Receiver</th>
                        <th scope="col" class="fw-bold">Cost</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr
                        v-for="transac in toShow.data"
                        :key="transac.sender">
                            <th scope="row">{{transac.sender}}</th>
                            <td>{{transac.receiver}}</td>
                            <td>{{transac.cost}}€</td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="float-end total">
                <p class="fw-bold">Total : {{state.total}} €</p>
            </div>
        </template>
    </transaction>
  </Teleport>
</template>

<style>

.table-area {
    overflow-y: auto;
    border-radius: 6px;
}
.center{
    margin:auto;
    text-align: center;
}

tr{
    background-color: #d9dbf1 !important;
}

#thead{
    background-color: #BFC3E7 !important;
}

.btn-color{
  background-color: #183A41 !important;
}

.total{
    color: #d9dbf1;
}
</style>