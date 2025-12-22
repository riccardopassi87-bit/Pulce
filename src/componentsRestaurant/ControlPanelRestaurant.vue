<script setup>
    import ButtonControlPanel from '../commonViews/ButtonControlPanel.vue';
    import ButtonRestaurant from './ButtonRestaurant.vue';
</script>

<template>
    <div id="main">
        <ButtonControlPanel />
        <ButtonRestaurant />
    </div>

</template>

<style scoped>
    #main{
        width: 100%;
        height: 100%;
        display: flex;
        flex-direction: column;
        justify-content: start;
        background-color: #333;;
    }
</style>