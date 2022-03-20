<script setup>
import Chart from "chart.js/auto"
import {ref, onMounted} from "vue"
import http from "../../http-common";

  let blocks = [];
  let chartRef = null
  let costs = []
  let labels = []

  const bar = {
    id: 'bar',
    type: 'bar',
    data: {
      labels: [],
      datasets: [{
        data: [],
        backgroundColor: 'rgb(31, 87, 120)',
        borderColor: 'rgb(31, 87, 120)',
        borderRadius: 6
      }]
    }, 
    options: {
      animation: false,
      plugins: {
        title: {
          display: true,
          text: 'Transactions costs per block',
          font: {
            size: 30
          },
          color: '#183A41'
        },
        legend: {
          display: false
        }
      },
      scales: {
        x: {
          ticks: {
            color :'#183A41'
          },
          grid: {
            color :'rgb(96, 143, 171)'
          }
        },
        y: {
          ticks: {
            color :'#183A41'
          },
          grid: {
            color :'rgb(96, 143, 171)'
          }
        }
      }
    }
  }

  const updateChart = () => {
      bar.data.labels = labels
      bar.data.datasets = [{
          data: costs,
          backgroundColor: 'rgb(31, 87, 120)',
          borderColor: 'rgb(31, 87, 120)',
          borderRadius: 6
        }
      ]
      chartRef.update()
    }

	async function getBlocks(){
    let response = await http.get("/blocks")
    if(response.data.length != 0){
      blocks = response.data.length<10?response.data:response.data.slice(-10)

      if(blocks[0].index == 0){
        blocks.shift();
      }
      if(blocks.length < 10){
        costs =  []
        labels = []
      }

      blocks.forEach(element => {
        let total = 0
        element.data.forEach(element => {
          total+=element.cost
        })
        costs.push(total)
        labels.push(element.index)
      })

      while(costs.length > 10){
        costs.shift();
        labels.shift();
      }
    }else{
      costs =  []
      labels = []
    }
    updateChart()
	}

  onMounted(() => {
    const ctx = document.getElementById('bar-chart')
    chartRef = new Chart(ctx, bar)
    getBlocks()
    setInterval(() => {
      getBlocks()}
   ,3000);
})
</script>

<template>
  <div style="height:600px;width: 600px;display: flex;flex-direction:column;">
    <canvas id="bar-chart"></canvas>
  </div>
</template>