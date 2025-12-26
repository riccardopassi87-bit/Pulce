<template>
    <div id="addPizza">
        <div><p>Name: </p> <input/></div>
        <div><p>Selling Price: </p> <input placeholder="in euro"/></div>
        <div><p>Production Price: </p> <input placeholder="in euro"/></div>
        <div><p>Type: </p> <input placeholder="normal, vegetarian, vegan"/></div>
    </div>
</template>

<style scoped>
    #addPizza{
        width: 100%;
        height: 100%;
        display: flex;
        flex-direction: column;
        gap: 5%;
        color: white;
        font-size: 1.2rem;
    }
    p{
        margin-bottom: 1%;
    }
</style>