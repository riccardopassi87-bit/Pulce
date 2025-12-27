<script setup>
    import LeftButtons from '../commonViews/LeftButtons.vue';
    import ButtonRestaurant from './ButtonRestaurant.vue';
</script>

<template>
    <div class="fsf" id="main">
        <LeftButtons />
        <ButtonRestaurant />
    </div>
</template>

<style scoped>
    #main{
        flex-direction: column;
        justify-content: start;
        align-items: center;
        background-color: #333;;
    }
</style>