<script setup>
    import NavButtons from './NavButtons.vue';
</script>

<template>
    <header>
        <div id="logo">
            <img src="C:\Users\Dell\Desktop\Abschluss projekt\shop-manager\src\assets\Logo.png">
        </div>
        <div id="buttons">
            <NavButtons />
        </div>
    </header>
</template>

<style scoped>
    header {
        width: 100%;
        height: 15%;
        background-color: #333;
        display: flex;
        align-items: center;
    }

    #logo{
        flex: 1;
        height: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
        background-color: #9a9a9a;
    }

    #logo img{
        height: 70%;
    }

    #buttons{
        flex: 2;
        height: 100%;
        display: flex;
    }
</style>