<template>
    <div id="main-body">
        <router-view />
    </div>
</template>

<style scoped>
    #main-body{
        height: 100%;
        display: flex;
        flex-direction: column;
        justify-content: space-evenly;
        align-items: center;
        background-color: #222;
    }
</style>