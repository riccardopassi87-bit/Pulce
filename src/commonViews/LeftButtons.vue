<script setup>
    import { commonRouter } from '../router/composable/commonRouter';
    const { goNext, addButtonText, searchButtonText, getButtonClass } = commonRouter();
</script>

<template>
    <button id="add" @click="goNext('addItem')" :class="getButtonClass('addItem')">{{ addButtonText }}</button>
    <button id="search" @click="goNext('search')" :class="getButtonClass('search')">{{ searchButtonText }}</button>
</template>