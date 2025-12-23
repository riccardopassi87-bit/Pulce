<script setup>
import ButtonElement from './ButtonElement.vue';
</script>

<template>
    <div id="panel">
        <router-view />
    </div>
    <div id="bottomButtons">
        <ButtonElement />
    </div>
</template>

<style scoped>
#panel{
    height: 90%;
    background-color: #222;
    border-color: #444;
}
#bottomButtons{
    height: 10%;
    width: 100%;
    display: flex;
}
</style>
