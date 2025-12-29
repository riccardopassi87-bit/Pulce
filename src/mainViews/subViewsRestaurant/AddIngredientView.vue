<template>
    <div class="fsf" id="addItem">
        <div><p>Name: </p> <input/></div>
        <div><p>Portion Price: </p> <input placeholder="in euro"/></div>
        <div><p>Kg Price: </p> <input placeholder="in euro"/></div>
        <div><p>Type: </p> <input placeholder="Veggie, Cheese, Meat, Others"/></div>
        <div><p>Allergene: </p> <input placeholder="A,B,C..."/></div>
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