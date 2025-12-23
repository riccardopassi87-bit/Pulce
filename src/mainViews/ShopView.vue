<script setup>
    import ControlPanelShop from '../componentsShop/ControlPanelShop.vue';
    import Visualization from '../commonViews/Visualization.vue';
</script>

<template>
    <div id="main-shop">
        <div id="left">
            <ControlPanelShop />
        </div>
        <div id="right">
            <Visualization />
        </div>
    </div>        
</template>

<style scoped>
    #main-shop{
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