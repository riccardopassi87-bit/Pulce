<script setup>
import { commonRouter } from '../router/composable/commonRouter';
const { goNext } = commonRouter();
</script>

<template>
        <button @click="goNext('/shop')">
            Shop Manager
        </button>
        <button @click="goNext('/restaurant')">
            Menu Generator
        </button>
</template>

<style scoped>

    button{
        height: 100%;
        flex: 1;
    }
</style>