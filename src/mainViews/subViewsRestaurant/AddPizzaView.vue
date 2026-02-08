<script setup>
    import { ref, watch, computed } from 'vue';
    import { api } from '@/api/apiService';
    import { pizzaRules } from '@/constants/ruleSets';
    import { PIZZA_TYPE, INGREDIENT_TYPE } from '@/constants/types';
    import { useForm } from '@/router/composable/useForm';

    import SearchPrompt from '@/commonViews/SearchPrompt.vue';
    import ButtonsFooter from '@/commonViews/ButtonsFooter.vue';
    import FormField from '@/commonViews/FormField.vue';

    const API_BASE = 'http://localhost:8080/api/pizza'
    const ING_SEARCH_URL = 'http://localhost:8080/api/ingredient';
    const pizzaBase = 8;
    const existingNames = ref([])
    const selectedIngredients = ref([]);

    const schema = pizzaRules(existingNames);

    const { form, errors, submit, submitted, search, selectedType,
        searchResults, validateField, reset, fetchSearchResults
    } = useForm({
        initialState: schema.initialState,
        rules: schema.rules,
        existingNamesRef: existingNames,
        API_BASE: API_BASE,
        SEARCH_URL: ING_SEARCH_URL,
        onSubmit: async (data) => {
            try {
                await api.post(API_BASE, data);
                alert('Pizza saved succesfully ✅');
                existingNames.value.push(data.name);
                handleReset();
            } catch (e) { alert('Error saving'); }
        }
    });

    watch([search, selectedType], fetchSearchResults);

    const addIngredient = (ing) => {
        if(selectedIngredients.value.length >= 6) return;
        if (selectedIngredients.value.find(i => i.id === ing.id)) return;
        selectedIngredients.value.push(ing);
        form.ingredientIds.push(ing.id);
        if (submitted.value) validateField('ingredientIds');
    };

    const removeIngredient = (id) => {
        selectedIngredients.value = selectedIngredients.value.filter(i => i.id !== id);
        form.ingredientIds = form.ingredientIds.filter(itemId => itemId !== id);
        if (submitted.value) validateField('ingredientIds');
    };

    const suggestedPrice = computed(() => {
        const ingredientCost = selectedIngredients.value.reduce(
            (sum, i) => sum + Number(i.portionPrice),
            0
        );
        return Number((pizzaBase + ingredientCost).toFixed(2));
    });

    const handleReset = () => {
        reset();
        selectedIngredients.value = [];
    };
</script>

<template>
    <form class="parent-view fsf" @submit.prevent="submit">
        <div class="fsf" id="general-pizza">
            <div class="fsf" id="main-pizza">
                <div class="fsf" id="create-pizza">
                    <FormField label="Name:" :error="errors.name" :submitted="submitted" v-slot="{ isInvalid }">
                        <input v-model="form.name" @input="validateField('name')" :class="{ invalid: isInvalid}"/>
                    </FormField>
                    <FormField label="Selling Price:" :error="errors.sellingPrice" :submitted="submitted" v-slot="{ isInvalid }">
                        <input type="number" min="0" step="0.01"
                            v-model.number="form.sellingPrice" @input="validateField('sellingPrice')" :class="{ invalid: isInvalid}"
                            :placeholder="suggestedPrice.toFixed(2)"/>
                    </FormField>
                    <FormField label="Production Price:" :error="errors.productionPrice" :submitted="submitted" v-slot="{ isInvalid }">
                        <input type="number" min="0" step="0.01" v-model.number="form.productionPrice" @input="validateField('productionPrice'); validateField('sellingPrice')"
                        :class="{ invalid: isInvalid }"/>
                    </FormField>
                    <FormField label="Type:" :error="errors.type" :submitted="submitted" v-slot="{ isInvalid }">
                        <select v-model="form.type" @change="validateField('type')" :class="{ invalid: isInvalid }">
                            <option value=""></option>
                            <option v-for="p in PIZZA_TYPE" :key="p" :value="p">{{ p }}</option>
                        </select>
                    </FormField>
                </div>
                <div class="fsf" id="add-ingredients">
                    <p>Add Ingredient (Max 6)</p>
                    <SearchPrompt>
                        <template #input>
                            <input class="own-input" v-model="search" placeholder="search by name"/>
                        </template>
                        <template #filter>
                            <select v-model="selectedType" id="ingredient-select">
                                <option value=""></option>
                                <option v-for="t in INGREDIENT_TYPE" :key="t" :value="t">
                                    {{ t }}
                                </option>
                            </select>
                        </template>
                        <template #results>
                            <ul>
                                <li v-for="i in searchResults" :key="i.id">
                                    <p @click="addIngredient(i)">{{ i.name }} - {{ i.portionPrice.toFixed(2) }} €</p>
                                </li>
                            </ul>
                        </template>
                    </SearchPrompt>
                </div>
            </div>
            <div class="fsf" id="preview-pizza">
                <div id="name-price">
                    <p>Ingredient price: <span id="ingredient-price-preview">{{ (suggestedPrice - pizzaBase).toFixed(2) }} €</span></p>
                    <p>Pizza base price: {{ pizzaBase.toFixed(2) }} €</p>
                    <p>Total suggested price: <span id="total-suggested-price">{{ suggestedPrice.toFixed(2) }} €</span></p>
                </div>
                <div id="ingredient-list" :class="{ invalid: submitted && errors.ingredientIds}">
                    <ul class="fsf">
                        <p v-if="submitted && errors.ingredientIds" class="error">{{ errors.ingredientIds }}</p>
                        <li id="pizza-ingredient" v-for="i in selectedIngredients" :key="i.id" @click="removeIngredient(i.id)">
                            <p>{{ i.name }}</p>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="footer-buttons">
                <ButtonsFooter
                :show-save="true"
                :show-modify="false"
                :show-remove="false"/>
            </div>
        </div>
    </form>
</template>

<style scoped>
    #general-pizza{
        flex: 1;
        flex-direction: column;
    }
    #main-pizza{
        flex: 5;
    }
    #create-pizza{
        flex: 1;
        flex-direction: column;
        margin-right: 3%;
    }
    select{
        width: 100%;
    }
    #ingredient-select{
        height: 80%;
        margin-bottom: 1%;
        background-color: #222;
    }
    #add-ingredients{
        height: 94%;
        flex: 1;
        display: flex;
        flex-direction: column;
    }
    #create-pizza input{
        width: 98%;
    }
    #preview-pizza{    
        flex: 3;
        border-radius: 5px;
        display: flex;
        flex-direction: column;
        background-color: #333;
    }
    #name-price{
        flex: 1;
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 0 1%;
        font-size: 1.2rem;
    }
    #name-price p{
        flex: 1;
    }
    #total-suggested-price{
        font-size: 1.2rem;
        font-weight: bold;
        color: rgb(255, 170, 0);
    }
    #ingredient-price-preview{
        color: rgb(187, 150, 80);
        font-weight: bold;
    }
    #ingredient-list{
        flex: 3;
        padding: 1%;
        background-color: #111;
        border-bottom-left-radius: 5px;
        border-bottom-right-radius: 5px;
    }
    #ingredient-list ul{
        gap: 0.5%;
        flex-wrap: wrap;
    }
    #pizza-ingredient{
        width: 33%;
        height: 47%;
        display: flex;
        justify-content: center;
        align-items: center;
        font-size: 2rem;
        font-weight: bolder;
        background-color: #222;
    }
</style>