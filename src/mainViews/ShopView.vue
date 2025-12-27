<script setup>
    import ControlPanelShop from '../componentsShop/ControlPanelShop.vue';
    import Visualization from '../commonViews/Visualization.vue';
</script>

<template>
    <div class="fsf" id="main-shop">
        <div id="left">
            <ControlPanelShop />
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