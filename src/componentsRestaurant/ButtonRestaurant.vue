<script setup>
import { commonRouter } from '../router/composable/commonRouter';
const { goNext, getButtonClass } = commonRouter();
</script>

<template>
        <button type="button" id="addIngredient">Add Ingredient</button>
        <button type="button" id="searchIngredient">Search Ingredient</button>
        <button type="button" id="print" @click="goNext('print')" :class="getButtonClass('print')">Print Selection</button>
        <button type="button" id="export-html">Export HTML file</button>
</template>