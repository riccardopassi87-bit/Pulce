<script setup>
    import LeftButtons from '../commonViews/LeftButtons.vue';
    import ButtonShop from './ButtonShop.vue';
</script>

<template>
    <div class="fsf" id="main">
        <LeftButtons />
        <ButtonShop />
    </div>
</template>

<style scoped>
    #main{
        flex-direction: column;
        justify-content: start;
        align-items: center;
        background-color: #333;
    }
</style>