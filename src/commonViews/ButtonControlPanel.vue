<script setup>
    import { commonRouter } from '../router/composable/commonRouter';
    const { goNext } = commonRouter();
</script>

<template>
    <button id="add" @click="goNext('addItem')">Add Product</button>
    <button id="search" @click="goNext('search')">Search Product</button>
    <input type="text" placeholder="type your product here" />
</template>

<style scoped>
    input{
        all: unset;
        width: 95%;
        height: 5%;
        background-color: #222;
        border-radius: 5px;
        color: white;
    }

    input::placeholder{
        text-align: center;
    }
</style>