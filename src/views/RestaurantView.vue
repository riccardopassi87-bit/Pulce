<script setup>
    import ControlPanelRestaurant from '../componentsRestaurant/ControlPanelRestaurant.vue';
    import Visualization from '../commonViews/Visualization.vue';
    import ButtonElement from '../commonViews/ButtonElement.vue';
</script>

<template>
    <div id="main-restaurant">
        <div id="left">
            <ControlPanelRestaurant />
        </div>

        <div id="right">
            <Visualization />
            <ButtonElement />
        </div>
    </div> 
</template>

<style scoped>
    #main-restaurant{
        height: 100%;
        width: 100%;
        display: flex;
        flex-direction: row;
        justify-content: space-evenly;
    }
    #left{
        flex: 1;
    }
    #right{
        flex: 2;
    }
</style>