<script setup>
    import LeftButtons from '../commonViews/LeftButtons.vue';
    import ButtonShop from './ButtonShop.vue';
</script>

<template>
    <div id="main">
        <LeftButtons />
        <ButtonShop />
    </div>
</template>

<style scoped>
    #main{
        width: 100%;
        height: 100%;
        display: flex;
        flex-direction: column;
        justify-content: start;
        align-items: center;
        background-color: #333;
    }
</style>