<script setup>
import AppHeader from './commonStart/AppHeader.vue';
import StartingView from './commonStart/StartingView.vue';
</script>

<template>
  <div id="main">
    <AppHeader />
    <StartingView />
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

