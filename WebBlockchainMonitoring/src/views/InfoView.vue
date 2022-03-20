<script setup>
import ProjectInfo from '@/components/infoview/ProjectInfo.vue'
import Trombinoscope from '@/components/infoview/Trombinoscope.vue'
</script>

<template>
  <div class="content">
    <ProjectInfo/>
    <Trombinoscope
    class="size"/>
  </div>
</template>

<style> 
.size{
  padding-left: 200px;
  padding-right: 200px;
}
</style>