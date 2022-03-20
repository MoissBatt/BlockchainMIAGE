<script setup>
import DonutChart from '../components/statsview/DonutChart.vue';
import StatsInfo from '../components/statsview/StatsInfo.vue';
import BarChart from '../components/statsview/BarChart.vue';
</script>

<template>
  <div class="content d-flex pt-5 justify-content-around me-4">
    <DonutChart class ="d-flex"/>
    <div class ="d-flex flex-column">
      <StatsInfo/>
      <BarChart/>
    </div>
  </div>
</template>

<style>

</style>
