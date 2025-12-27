<script setup>
import { commonRouter } from '../router/composable/commonRouter';
const { goNext, isParentActive} = commonRouter();
</script>

<template>
    <button @click="goNext('/shop')" :class="isParentActive('/shop') ? 'button-active' : 'button'">
        Shop Manager
    </button>
    <button @click="goNext('/restaurant')" :class="isParentActive('/restaurant') ? 'button-active' : 'button'">
        Menu Generator
    </button>
</template>

<style scoped>

    button{
        height: 100%;
        flex: 1;
    }
</style>