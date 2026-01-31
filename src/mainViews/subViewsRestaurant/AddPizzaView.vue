<script setup>
    import { ref, watch, onMounted, computed } from 'vue';
    import { api, nameLoader } from '@/api/apiService';
    import { pizzaRules } from '@/constants/ruleSets';
    import { PIZZA_TYPE, INGREDIENT_TYPE } from '@/constants/types';

    import SearchPrompt from '@/commonViews/SearchPrompt.vue';
    import ButtonsFooter from '@/commonViews/ButtonsFooter.vue';
    import FormField from '@/commonViews/FormField.vue';

    import { useFormValidation } from '@/router/composable/useFormValidation';

    const API_BASE = 'http://localhost:8080/api/pizza'
    const pizzaBase = 8;
    const search = ref('');
    const selectedType = ref('');
    const ingredients = ref([]);
    const selectedIngredients = ref([]);
    const existingName = ref([]);

    onMounted(() => nameLoader(existingName, API_BASE))

    const schema = pizzaRules(existingName)

    const { form, errors, submit, submitted, validateField, resetForm} = useFormValidation(
        schema.initialState,
        schema.rules,
        async (data) => {
            try{
                await api.post(API_BASE, data);
                alert('Pizza saved successfully ✅');
                existingName.value.push(data.name);
                resetView();
            } catch (e) {
                alert('Failed to save pizza ❌');
            }
        }
    );

    const fetchIngredients = async () => {
        if (!search.value && !selectedType.value){
            ingredients.value = [];
            return;
        }
        const params = new URLSearchParams();
        if (search.value) params.append ('name', search.value);
        if (selectedType.value) params.append ('type', selectedType.value);

        try {
            ingredients.value = await api.get(`http://localhost:8080/api/ingredient/search?${params.toString()}`);
        } catch (e) {
            console.error(e);
        }
    };

    watch([search, selectedType], fetchIngredients, { immediate: true});

    watch(selectedIngredients, (newList) => {
        form.ingredientIds = newList.map (i => i.id);
        if (submitted.value) {
            validateField('ingredientIds');
        }
    }, { deep: true });

    const addIngredient = (ing) => {
        if(selectedIngredients.value.length >= 7) return;
        if (selectedIngredients.value.find(i => i.id === ing.id)) return;
        selectedIngredients.value.push(ing);
    };

    const removeIngredient = (id) => {
        selectedIngredients.value = selectedIngredients.value.filter(i => i.id !== id);
    };

    const suggestedPrice = computed(() => {
        const ingredientCost = selectedIngredients.value.reduce(
            (sum, i) => sum + Number(i.portionPrice),
            0
        );
        return Number((pizzaBase + ingredientCost).toFixed(2));
    });

    const resetView = () => {
        resetForm();
        selectedIngredients.value = [];
        search.value = '';
        selectedType.value = '';
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
                                <option disabled selected hidden></option>
                                <option v-for="t in INGREDIENT_TYPE" :key="t" :value="t">
                                    {{ t }}
                                </option>
                            </select>
                        </template>
                        <template #results>
                            <ul>
                                <li v-for="i in ingredients" :key="i.id">
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
                    <ul id>
                        <p v-if="submitted && errors.ingredientIds" class="error">{{ errors.ingredientIds }}</p>
                        <li v-for="i in selectedIngredients" :key="i.id" @click="removeIngredient(i.id)">
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
        min-height: 0;
        max-height: 100%;
        overflow-y: auto;
        padding: 1%;
        background-color: #111;
        border-bottom-left-radius: 5px;
        border-bottom-right-radius: 5px;
    }
</style>