<script setup>
import Header from './commonStart/Header.vue';
import StartingPage from './commonStart/StartingPage.vue';

// EASTER EGG!!!!!
import { watch } from 'vue';
import { apiSettings } from './api/apiService';

watch(() => apiSettings.performanceMode, (isSlow) => {
  const root = document.documentElement;
  
  if (isSlow) {
    root.style.setProperty('--font-color', '#bb9650'); 
  } else {
    root.style.setProperty('--font-color', 'rebeccapurple');
  }
}, { immediate: true });
// END
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

