<script setup>
import BlockTable from '@/components/blocksview/BlockTable.vue'
import Blockchain from '@/components/blocksview/Blockchain.vue'
import http from "../http-common";
import {ref, onMounted} from "vue";
const blocks = ref([]);
const slicedBlocks = ref([]);

async function getBlocks(){
  let response = await http.get("/blocks")
  slicedBlocks.value = response.data.slice(-4)
  blocks.value = response.data.reverse();
}

onMounted(() => {
  getBlocks();
  setInterval(() => {
   getBlocks()}
   ,3000);
})
</script>

<template>
  <div class="content d-flex flex-column">
    <Blockchain
    :blocks="slicedBlocks"/>
    <BlockTable
    :blocks="blocks"/>
  </div>
</template>
