<script setup>
import ButtonsFooter from './ButtonsFooter.vue';
</script>

<template>
    <div id="panel">
        <router-view />
    </div>
    <div class="fsf" id="bottomButtons">
        <ButtonsFooter />
    </div>
</template>

<style scoped>

#panel{
    height: 90%;
    padding: 3%;
    /*background-color: #222;*/
}
#bottomButtons{
    height: 10%;
}
</style>
