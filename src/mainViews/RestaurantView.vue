<script setup>
    import ControlPanelRestaurant from '../componentsRestaurant/ControlPanelRestaurant.vue';
    import Visualization from '../commonViews/Visualization.vue';
</script>

<template>
    <div id="main-restaurant">
        <div id="left">
            <ControlPanelRestaurant />
        </div>
        <div id="right">
            <Visualization />
        </div>
    </div> 
</template>

<style scoped>
    #main-restaurant{
        height: 100%;
        width: 100%;
        display: flex;
        flex-direction: row;
    }
    #left{
        flex: 1;
    }
    #right{
        flex: 2;
    }
</style>