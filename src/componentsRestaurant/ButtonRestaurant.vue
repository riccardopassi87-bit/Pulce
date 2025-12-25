<script setup>
import { commonRouter } from '../router/composable/commonRouter';
const { goNext } = commonRouter();
</script>

<template>
        <button type="button" id="export-html">Export HTLM file</button>
        <button type="button" id="print" @click="goNext('print')">Print Selection</button>
</template>