<script setup>
import ButtonsFooter from './ButtonsFooter.vue';
</script>

<template>
    <div id="panel">
        <router-view />
    </div>
    <div id="bottomButtons">
        <ButtonsFooter />
    </div>
</template>

<style scoped>
#panel{
    height: 90%;
    padding: 3%;
    background-color: #222;
    border-color: #444;
}
#bottomButtons{
    height: 10%;
    width: 100%;
    display: flex;
}
</style>
