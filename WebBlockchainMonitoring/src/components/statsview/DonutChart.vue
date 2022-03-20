<script setup>
import Chart from "chart.js/auto"
import {ref, onMounted} from "vue"
import http from "../../http-common";

  const blocksPerMiner = [];
  let chartRef = null

  const doughnut = {
    id: 'doughnut',
    type: 'doughnut',
    data: {
      labels: [],
      datasets: [{
        data: [],
        backgroundColor: [
          'rgb(96, 143, 171)',
          'rgb(31, 87, 120)',
          'rgb(146, 222, 209)',
          'rgb(226, 111, 93)',
          'rgb(177, 86, 98)',
          'rgb(96, 171, 119)',
          'rgb(46, 122, 70)',
          'rgb(170, 222, 146)',
          'rgb(194, 93, 226)',
          'rgb(132, 96, 171)'
        ],
        borderColor : '#6AB7C8'
      }]
    }, 
    options: {
      plugins: {
        legend: {
          labels: {
              color: '#183A41'
          }
        },
        title: {
          display: true,
          text: 'Mined blocks per miner',
          font: {
            size: 30
          },
          color: '#183A41'
        }
      }
    }
  }

	async function getBlocksPerMiner(){
    let response = await http.get("/blocks/miners")
    blocksPerMiner.value = response.data
    doughnut.data.labels = Object.keys(blocksPerMiner.value)
    doughnut.data.datasets[0].data = Object.values(blocksPerMiner.value)
    chartRef.update()

	}

  onMounted(() => {
    const ctx = document.getElementById('doughnut-chart')
    chartRef = new Chart(ctx, doughnut)
    getBlocksPerMiner()
    setInterval(() => {
      getBlocksPerMiner()}
   ,3000);
})
</script>

<template>
  <div class="donut d-flex flex-column">
    <canvas id="doughnut-chart"></canvas>
  </div>
</template>

<style>

.donut{
  height: 500px;
  width: 500px;
}

</style>