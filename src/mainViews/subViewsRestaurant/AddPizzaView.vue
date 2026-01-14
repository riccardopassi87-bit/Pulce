<script setup>
    import SearchPrompt from '@/commonViews/SearchPrompt.vue';
    import ButtonsFooter from '@/commonViews/ButtonsFooter.vue';
    import { useFormValidation, validators } from '@/router/composable/useFormValidation';

    const TYPE = ['Normal', 'Vegetarian', 'Vegan']
    const ingredientTYPE = ['Veggies', 'Cheese', 'Meat', 'Others']

    const { form, errors, submitted, validateField, submit } = useFormValidation({
        name: '',
        sellingPrice: null,
        productionPrice: null,
        type: ''
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
            { validator: validators.oneOf(TYPE), message: 'Type is not valid'}]
    },
    async (data) => {
        alert('ooonoooooooefoqef')
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
                        :class="{invalid: submitted && errors.sellingPrice}"/></div>
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
                            <option disabled.value=""></option>
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
                        <template #filter>
                            <select id="ingredient-select">
                                <option disabled.value=""></option>
                                <option v-for="t in ingredientTYPE" :key="t" :value="t">
                                    {{ t }}
                                </option>
                            </select>
                        </template>
                    </SearchPrompt>
                </div>
            </div>
            <div class="fsf" id="preview-pizza">
                <div id="name-price" contenteditable="true" data-placeholder="Name, Vegetarian/Vegan/symbol, ........., Price">

                </div>
                <div id="ingredient-list" contenteditable="true" data-placeholder="List of Ingredients">
<p>oooooooooh noooooooooooooooo</p>
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
        flex-direction: column;
    }
    #main-pizza{
        height: 55%;
        flex: 6;
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
        height: 45%;
        border-radius: 5px;
        background-color: #333;
        display: flex;
        flex-direction: column;
        flex: 3;
    }
    #name-price{
        flex: 1;
    }
    #name-price:empty{
        display: flex;
        justify-content: center;
        align-items: center;
    }
    #name-price:empty::before{
        content: attr(data-placeholder);
        color: #777;
        pointer-events: none;
    }
    #ingredient-list{
        flex: 2;;
    }
    #ingredient-list:empty{
        display: flex;
        justify-content: center;
        align-items: center;
    }
    #ingredient-list:empty::before{
        content: attr(data-placeholder);
        color: #777;
        font-size: 2rem;
    }
    #footer-buttons{
        flex: 1;
    }
</style>