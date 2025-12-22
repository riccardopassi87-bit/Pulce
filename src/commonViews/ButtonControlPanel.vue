<template>
    <button id="add">Add Product</button>
    <button id="search">Search Product</button>
    <input type="text" placeholder="type your product here" />
</template>

<style scoped>
  
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

    input{
        all: unset;
        width: 100%;
        height: 10%;
        background-color: #333;
        border-style: solid;
        border-width: 0 0 1px 0;
        border-color: #444;
        color: white;
    }

    input::placeholder{
        text-align: center;
    }
</style>