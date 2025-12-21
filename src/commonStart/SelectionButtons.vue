<script setup>
import { useRouter } from 'vue-router';

const route = useRouter()

function goNext(routePath){
    route.push(routePath);
}
</script>

<template>
    <div class="button">
        <button @click="goNext('/shop')">
            Shop Manager
        </button>
    </div>

    <div class="button">
        <button @click="goNext('/restaurant')">
            Menu Generator
        </button>
    </div>
</template>

<style scoped>
    .button{
        flex: 1;
        height: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
        border-style: solid;
        border-left-width: 1px;
        border-color: #444;
    }

    .button:hover{
        cursor: pointer;
        background-color: #444;
    }
    
    button{
        all: unset;
        font-size: 2rem;
        font-weight: 100;
        color: white;
    }
</style>