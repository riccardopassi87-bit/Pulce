<script setup>
    import ControlPanelRestaurant from '../componentsRestaurant/ControlPanelRestaurant.vue';
    import Visualization from '../commonViews/Visualization.vue';
</script>

<template>
    <div class="fsf" id="main-restaurant">
        <div id="left">
            <ControlPanelRestaurant />
        </div>
        <div id="right">
            <Visualization />
        </div>
    </div> 
</template>

<style scoped>
    #left{
        flex: 1;
    }
    #right{
        flex: 2;
    }
</style>