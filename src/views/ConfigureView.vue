<script setup>
    import ControlPanel from '@/components/ControlPanel.vue';
    import Visualization from '@/components/Visualization.vue';
    import BtnElement from '@/components/BtnElement.vue';
</script>

<template>
    <div id="main">
        <div id="left">
            <ControlPanel />
        </div>

        <div id="right">
            <Visualization />
            <BtnElement />
        </div>
    </div>        
</template>

<style scoped>
    #main{
        height: 85%;
        width: 100%;
        background-color: aqua;
        display: flex;
        flex-direction: row;
        justify-content: space-evenly;
    }
    #left{
        background-color: aquamarine;
        flex: 1;
        display: flex;
        flex-direction: column;
    }
    #right{
        background-color: bisque;
        flex: 2;
    }
</style>