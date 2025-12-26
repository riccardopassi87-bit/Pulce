<script setup>
    import { commonRouter } from '../router/composable/commonRouter';
    const { goNext, addButtonText, searchButtonText } = commonRouter();
</script>

<template>
    <button id="add" @click="goNext('addItem')">{{ addButtonText }}</button>
    <button id="search" @click="goNext('search')">{{ searchButtonText }}</button>
</template>