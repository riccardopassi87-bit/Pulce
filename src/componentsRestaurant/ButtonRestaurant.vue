<script setup>
        import { commonRouter } from '../router/composable/commonRouter';
        const { goNext, getButtonClass } = commonRouter();
</script>

<template>
        <button type="button" id="addIngredient" @click="goNext('addIngredient')" :class="getButtonClass('addIngredient')">Add Ingredient</button>
        <button type="button" id="searchIngredient" @click="goNext('srcIngredient')" :class="getButtonClass('srcIngredient')">Search Ingredient</button>
        <button type="button" id="print" @click="goNext('print')" :class="getButtonClass('print')">Print Selection</button>
        <button type="button" id="export-html">Export HTML file</button>
</template>