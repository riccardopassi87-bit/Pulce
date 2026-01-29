<script setup>
    import SearchPrompt from '@/commonViews/SearchPrompt.vue';
    import ButtonsFooter from '@/commonViews/ButtonsFooter.vue';
    import { useFormValidation, validators } from '@/router/composable/useFormValidation';
    import { ref, computed, watch} from 'vue';
    import { createIngredient } from '@/api/ingredientApi';

    const API_BASE = 'http://localhost:8080/api/pizza'
    const search = ref('')
    const selectedType = ref('')
    const ingredients = ref([])
    const selectedIngredients = ref([])
    const pizzaBase = 8;

    watch(selectedIngredients, (list) => {
        form.ingredientIds = list.map(i => i.id)
        validateField('ingredientIds')
        }, { deep: true})

    const fetchIngredients = async () => {
        if (!search.value && !selectedType.value) {
        ingredients.value = []
        return
        }

        const params = new URLSearchParams()
        if (search.value) params.append('name', search.value)
        if (selectedType.value) params.append('type', selectedType.value)

        const res = await fetch(
            `http://localhost:8080/api/ingredient/search?${params.toString()}`
        )
        ingredients.value = await res.json()
    }
    watch([search, selectedType], fetchIngredients, { immediate: true})

    const addIngredient = (ingredients) => {
        if (selectedIngredients.value.find(i => i.id === ingredients.id)) return
        if (selectedIngredients.value.length >= 7) return

        selectedIngredients.value.push(ingredients)
    }

    const removeIngredient = (id) => {
        selectedIngredients.value = selectedIngredients.value.filter(i => i.id !== id)
    }

    const totalPrice = computed(() =>
        selectedIngredients.value.reduce(
            (sum, i) => sum + Number(i.portionPrice),
            0
        )
    )

    const TYPE = ['Normal', 'Vegetarian', 'Vegan']
    const ingredientTYPE = ['Veggie', 'Cheese', 'Meat', 'Base', 'Others']

    const buildPizzaPayload = () => {
        return {
            name: form.name,
            sellingPrice: form.sellingPrice,
            productionPrice: form. productionPrice,
            type: form.type,
            ingredientIds: selectedIngredients.value.map(i => i.id)
        }
    }

    const { form, errors, submitted, validateField, submit } = useFormValidation({
        name: '',
        sellingPrice: null,
        productionPrice: null,
        type: '',
        ingredientIds: []
    },
    {
        name: [{ validator: validators.required, message: 'Name is required'}],
        sellingPrice: [
            { validator: validators.required, message: 'Required'},
            { validator: validators.number, message: 'Must be a number'},
            { validator: validators.priceIsValid('productionPrice'), message: 'Impossible' }],
        productionPrice: [
            { validator: validators.required, message: 'Required'},
            { validator: validators.number, message: 'Must be a number'}
            ],
        type: [
            { validator: validators.required, message: 'Type is required'},
            { validator: validators.oneOf(TYPE), message: 'Type is not valid'}],
        ingredientIds: [
            { validator: validators.minLength(1), message: 'Select at least 1 ingredient'}
        ]
    },
    async () => {
        const payload = buildPizzaPayload()

        try {
        await createIngredient(API_BASE, payload)

        selectedIngredients.value = []
        search.value = ''
        selectedType.value = ''
        
        alert('Pizza saved successfully ✅')
        } catch (e) {
            alert('Failed to save pizza ❌')
        }
    }
    )
</script>

<template>
    <form class="parent-view fsf" @submit.prevent="submit">
        <div class="fsf" id="general-pizza">
            <div class="fsf" id="main-pizza">
                <div class="fsf" id="create-pizza">
                    <div class="field">
                        <div class="pee"><p>Name: </p></div>
                        <div class="pee-input"><input v-model="form.name" @blur="validateField('name')"
                        :class="{invalid: submitted && errors.name}"/></div>
                        <div class="pee"><p v-if="submitted && errors.name" class="error">
                            {{ errors.name }}
                        </p></div>
                    </div>
                    <div class="field">
                        <div class="pee"><p>Selling Price: </p></div>
                        <div class="pee-input"><input type="number" min="0" step="0.01" v-model.number="form.sellingPrice" @blur="validateField('sellingPrice')"
                        :class="{invalid: submitted && errors.sellingPrice}"
                        :placeholder="(totalPrice + pizzaBase).toFixed(2)"/></div>
                        <div class="pee"><p v-if="submitted && errors.sellingPrice" class="error">
                            {{ errors.sellingPrice }}
                        </p></div>
                    </div>
                    <div class="field">
                        <div class="pee"><p>Production Price: </p></div>
                        <div class="pee-input"><input type="number" min="0" step="0.01" v-model.number="form.productionPrice" @blur="validateField('productionPrice'); validateField('sellingPrice')"
                        :class="{invalid: submitted && errors.productionPrice}"/></div>
                        <div class="pee"><p v-if="submitted && errors.productionPrice" class="error">
                            {{ errors.productionPrice }}
                        </p></div>
                    </div>
                    <div class="field">
                        <div class="pee"><p>Type: </p></div>
                        <div class="pee-input"><select v-model="form.type" @blur="validateField('type')"
                        :class="{invalid: submitted && errors.type}">
                            <option disabled selected hidden></option>
                            <option v-for="t in TYPE" :key="t" :value="t">
                                {{ t }}
                            </option>
                        </select></div>
                        <div class="pee"><p v-if="submitted && errors.type" class="error">
                            {{ errors.type }}
                        </p></div>
                    </div>
                </div>
                <div class="fsf" id="add-ingredients">
                    <p>Add Ingredient</p>
                    <SearchPrompt>
                        <template #input>
                            <input class="own-input" v-model="search" placeholder="search by name"/>
                        </template>
                        <template #filter>
                            <select v-model="selectedType" id="ingredient-select">
                                <option disabled selected hidden></option>
                                <option v-for="t in ingredientTYPE" :key="t" :value="t">
                                    {{ t }}
                                </option>
                            </select>
                        </template>
                        <template #results>
                            <ul>
                                <li v-for="i in ingredients" :key="i.id">
                                    <p @click="addIngredient(i)">{{ i.name }} - {{ i.portionPrice }} €</p>
                                </li>
                            </ul>
                        </template>
                    </SearchPrompt>
                </div>
            </div>
            <div class="fsf" id="preview-pizza">
                <div id="name-price">
                    <p>Ingredient price: <span id="ingredient-price-preview">{{ totalPrice.toFixed(2) }} €</span></p>
                    <p>Pizza base price: {{ pizzaBase.toFixed(2) }} €</p>
                    <p>Total suggested price: <span id="total-suggested-price">{{ (totalPrice + pizzaBase).toFixed(2) }} €</span></p>
                </div>
                <div id="ingredient-list" :class="{ invalid: submitted && errors.ingredientIds}">
                    <ul id>
                        <p v-if="submitted && errors.ingredientIds" class="error"></p>
                        <li v-for="i in selectedIngredients" :key="i.id">
                            <p @click="removeIngredient(i.id)">{{ i.name }}</p>
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
    #total-suggested-price{
        font-size: 1.5rem;
        font-weight: bold;
        color: red;
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