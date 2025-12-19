<script setup>
import AppHeader from './components/AppHeader.vue';
</script>

<template>
  <div id="main">
    <AppHeader />
    <router-view />
  </div>
</template>

<style scoped>
  #main{
    width: 100vw;
    height: 100vh;
    display: flex;
    flex-direction: column;
    justify-content: space-evenly;
  }
</style>

