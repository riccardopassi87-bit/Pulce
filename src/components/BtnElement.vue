<template>
    <div id="buttons">
        <button>Save</button>
        <button>Exit</button>
    </div>
</template>

<style scoped>
    #buttons{
        width: 100%;
        height: 10%;
        display: flex;
        justify-content: space-evenly;
    }
    button{
        all:unset;
        flex: 1;
        border-style: solid;
        border-width: 1px;
        border-color: #444;
        background-color: #333;
        text-align: center;
        font-size: 1.2rem;
        color: antiquewhite;
    }
    button:hover{
        cursor: pointer;
        background-color: #444;
    }
</style>