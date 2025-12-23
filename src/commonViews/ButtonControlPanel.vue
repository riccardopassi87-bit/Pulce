<script setup>
import { commonRouter } from '../router/composable/commonRouter';
const { goNext } = commonRouter();
</script>

<template>
    <button id="add" @click="goNext('/addItem')">Add Product</button>
    <button id="search" @click="goNext('/search')">Search Product</button>
    <input type="text" placeholder="type your product here" />
</template>

<style scoped>
    input{
        all: unset;
        width: 100%;
        height: 10%;
        background-color: #333;
        border-style: solid;
        border-width: 1px;
        border-color: #444;
        color: white;
    }

    input::placeholder{
        text-align: center;
    }
</style>