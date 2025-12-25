<script setup>
import Header from './commonStart/Header.vue';
import StartingPage from './commonStart/StartingPage.vue';
</script>

<template>
  <div id="main">
    <Header />
    <StartingPage />
  </div>
</template>

<style scoped>
  #main{
    width: 100vw;
    height: 100vh;
    display: flex;
    flex-direction: column;
  }
</style>

