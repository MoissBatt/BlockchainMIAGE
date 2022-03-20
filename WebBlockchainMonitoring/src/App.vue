<script setup>
import { RouterLink, RouterView } from 'vue-router'
import NavBar from '@/components/NavBar.vue'
</script>

<template>
    <div id="app-wrapper" class="container shadow mt-4 px-0 bg-blue">
      <NavBar />
      <RouterView />
    </div>
</template>

<style>
@import '@/assets/base.css';

#app {
  width: 100%;
  margin: auto;
  font-weight: normal;
  min-height: 95vh;
  max-height: 95vh;
  height: 95vh;
}

body{
  background-color: #183A41 !important;
}

header {
  line-height: 1.5;
}

.logo {
  display: block;
  margin: 0 auto 2rem;
}

.bg-blue{
  background-color: #6AB7C8;
  border-top-left-radius: 6px;
  border-top-right-radius: 6px;
}

#app-wrapper {
  height: 95vh;
  max-height: 95vh;
  min-height: 95vh;
  border-radius: 6px;
}

.content {
  max-height: 90%;
}

</style>
