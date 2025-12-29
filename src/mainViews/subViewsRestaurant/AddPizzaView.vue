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
            <SearchPrompt>
                <template #filter>
                    <p>Veggies</p>
                    <p>Cheese</p>
                    <p>Meat</p>
                    <p>Others</p>
                </template>
            </SearchPrompt>
        </div>
    </div>
    <div class="fsf" id="preview-pizza">
        <div class="fsf" id="ingredient-list">

        </div>
        <div id="suggested-price">

        </div>
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
        margin-right: 3%;
    }
    #add-ingredients{
        height: 96%;
        flex: 1;
        display: flex;
        flex-direction: column;
        font-size: 1.2rem;
    }
    #create-pizza input{
        width: 98%;
    }
    #preview-pizza{    
        height: 45%;
        border-radius: 5px;
        background-color: #333;
        display: flex;
    }
    #ingredient-list{
        flex: 1;
        background-color: aquamarine;
    }
    #suggested-price{
        flex: 1;
        background-color: lightgreen;
    }
</style>