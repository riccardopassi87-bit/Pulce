<script setup>
import { useRouter } from 'vue-router';

const router = useRouter()

function goNext(){
    router.push('/configure');
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
        width: 30%;
        height: 50%;
        background-color: yellow;
        border-radius: 5px;
        display: flex;
        justify-content: center;
        align-items: center;
    }

    button{
        all: unset;
        text-align: center;
        width: 100%;
        height: 100%;
    }

    button:hover{
        cursor: pointer;
    }
</style>