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