<script setup>
import { useRouter } from 'vue-router';

const route = useRouter()

function goNext(){
    route.push('/configure');
}
</script>

<template>
    <div class="button">
        <button @click="goNext">
            Shop Manager
        </button>
    </div>

    <div class="button">
        <button @click="goNext">
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