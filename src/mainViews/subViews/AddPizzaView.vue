<script setup>
    import SearchPrompt from '@/commonViews/SearchPrompt.vue';
</script>

<template>
    <div class="fsf" id="main-pizza">
        <div class="fsf" id="create-pizza">
            <div><p>Name: </p> <input/></div>
            <div><p>Selling Price: </p> <input placeholder="in euro"/></div>
            <div><p>Production Price: </p> <input placeholder="in euro"/></div>
            <div><p>Type: </p> <input placeholder="normal, vegetarian, vegan"/></div>
        </div>
        <div class="fsf" id="add-ingredients">
            <p>Add Ingredient</p>
            <SearchPrompt />
        </div>
    </div>
    <div class="fsf" id="preview-pizza">
        
    </div>

</template>

<style scoped>
    #main-pizza{
        height: 55%;
    }
    #create-pizza{
        flex: 1;
        flex-direction: column;
        justify-content: space-between;
        font-size: 1.2rem;
        padding-bottom: 2%;
    }
    #add-ingredients{
        flex: 1;
        display: flex;
        flex-direction: column;
        font-size: 1.2rem;
    }
    #create-pizza input{
        width: 95%;
    }
    #preview-pizza{    
        height: 45%;
        border-radius: 5px;
        background-color: #333;
    }
</style>