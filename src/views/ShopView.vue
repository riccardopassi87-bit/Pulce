<script setup>
    import ControlPanelShop from '@/componentsShop/ControlPanelShop.vue';
    import Visualization from '@/commonViews/Visualization.vue';
    import ButtonElement from '@/commonViews/ButtonElement.vue';
</script>

<template>
    <div id="main">
        <div id="left">
            <ControlPanelShop />
        </div>

        <div id="right">
            <Visualization />
            <ButtonElement />
        </div>
    </div>        
</template>

<style scoped>
    #main{
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