<template>
    <div class="fsf" id="addItem">
        <div><p>Name: </p> <input/></div>
        <div><p>Original Price: </p> <input placeholder="in euro"/></div>
        <div><p>Selling Price: </p> <input placeholder="in euro"/></div>
        <div><p>Type: </p> <input placeholder="alcoholic, antialcoholic, etc..."/></div>
        <div><p>Expiration Date: </p> <input placeholder="full date"/></div>
        <div><p>Amount: </p> <input/></div>
    </div>
</template>

<style scoped>
    #addItem{
        flex-direction: column;
        gap: 5%;
        font-size: 1.2rem;
    }
    p{
        margin-bottom: 1%;
    }
</style>