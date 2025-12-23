<script setup>
import { commonRouter } from '../router/composable/commonRouter';
const { goNext } = commonRouter();
</script>

<template>
        <button id="export-html">Export HTLM file</button>
        <button id="print" @click="goNext('/print')">Print Selection</button>
</template>