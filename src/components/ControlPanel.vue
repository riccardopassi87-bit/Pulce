<script setup>
    import { useRouter } from 'vue-router';

    const route = useRouter()

    function goNext(){
    route.push('/');
}
</script>


<template>
    <div id="main-btn">
        <p>buttons will follow</p>
        <button>Add Product</button>
        <button>Find Product</button>
        <button @click="goNext">Back</button>
    </div>
</template>

<style scoped>
    #main-btn{
        background-color: #333;
        height: 100%;
        width: 100%;
        display: flex;
        flex-direction: column;
        justify-content: space-evenly;
    }
</style>