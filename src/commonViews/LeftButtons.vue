<script setup>
    import { commonRouter } from '../router/composable/commonRouter';
    const { goNext } = commonRouter();
</script>

<template>
    <button id="add" @click="goNext('addItem')">Add Product</button>
    <button id="search" @click="goNext('search')">Search Product</button>
</template>