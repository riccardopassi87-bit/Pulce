<template>
    <div id="common-buttons">
        <button id="add">Add Product</button>
        <button id="search">Search Product</button>
    </div>
</template>

<style scoped>
    #common-buttons{
        width: 100%;
        height: auto;
        display: flex;
        flex-direction: column;
        justify-content: start;
        align-items: center;
        background-color: violet;
    }

    button{
        all: unset;
        width: 100%;
        height: 15%;
        text-align: center;
        border-style: solid;
        border-width: 0 0 1px 0;
        border-color: #444;
        background-color: #333;
        font-size: 1.2rem;
        color: white;
    }

    button:hover{
        cursor: pointer;
        background-color: #444;
    }
</style>