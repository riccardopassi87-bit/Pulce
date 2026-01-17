<script setup>
    import ButtonsFooter from '@/commonViews/ButtonsFooter.vue';
    import { useFormValidation, validators } from '@/router/composable/useFormValidation';
    import { createIngredient } from '@/api/ingredientApi';

    const API_BASE = 'http://localhost:8080/api/item'

    const TYPE = ['Alpe', 'Alpenzu', 'Bisquits', 'Snacks', 'Drinks', 'Pesto', 'Pasta', 'Antipasti', 'Olives', 'Wine', 'Others' ]
    
    const { form, errors, submitted, validateField, submit } = useFormValidation({
        name: '',
        originalPrice: null,
        sellingPrice: null,
        type: '',
        expirationDate: '',
        amount: null
    },{
        name: [{ validator: validators.required, message: 'Name is required'}],
        originalPrice: [
            { validator: validators.required, message: 'Required'},
            { validator: validators.number, message: 'Must be a number'}],
        sellingPrice: [
            { validator: validators.required, message: 'Required'},
            { validator: validators.number, message: 'Must be a number'},
            { validator: validators.priceIsValid('originalPrice'), message:'We are loosing money here...'}],
        type: [
            { validator: validators.required, message: 'Type required'},
            { validator: validators.oneOf(TYPE), message: 'Invalid type'}],
        expirationDate: [
            { validator: validators.required, message: 'Expiration date required'},
            { validator: validators.dateIsValid, message: 'What year are we in?'}],
        amount: [
            { validator: validators.required, message: 'Amount required'},
            { validator: validators.number, message: 'Must be a number'}
        ]
    },
    async (data) => {
      try {
        await createIngredient(API_BASE, data)
        alert('Product saved successfully ✅')
        } catch (e) {
            alert('Failed to save product ❌')
        }
    }
    )  
</script>

<template>
    <form class="parent-view fsf" @submit.prevent="submit">
            <div class="fsf" id="addItem">
                <div class="field">
                    <div class="pee"><p>Name: </p></div>
                    <div class="pee-input"><input v-model="form.name" @blur="validateField('name')"
                    :class="{invalid: submitted && errors.name}"/></div>
                    <div class="pee"><p v-if="submitted && errors.name" class="error">
                        {{ errors.name }}
                    </p></div>
                </div>
                <div class="field">
                    <div class="pee"><p>Original Price: </p></div>
                    <div class="pee-input"><input type="number" min="0" step="0.01" v-model.number="form.originalPrice" @blur="validateField('originalPrice'); validateField('sellingPrice');"
                    :class="{invalid: submitted && errors.originalPrice}"/></div>
                    <div class="pee"><p v-if="submitted && errors.originalPrice" class="error">
                        {{ errors.originalPrice }}
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
                    <div class="pee"><p>Type: </p></div>
                    <div class="pee-input"><select v-model="form.type" @blur="validateField('type')"
                    :class="{invalid: submitted && errors.type}">
                        <option disabled value=""></option>
                        <option v-for="t in TYPE" :key="t" :value="t">
                            {{ t }}
                        </option>
                    </select></div>
                    <div class="pee"><p v-if="submitted && errors.type" class="error">
                        {{ errors.type }}
                    </p></div>
                </div>
                <div class="field">
                    <div class="pee"><p>Expiration Date: </p></div>
                    <div class="pee-input"><input type="date" v-model="form.expirationDate" @blur="validateField('expirationDate')"
                    :class="{invalid: submitted && errors.expirationDate}"/></div>
                    <div class="pee"><p v-if="submitted && errors.expirationDate" class="error">
                        {{ errors.expirationDate }}
                    </p></div>
                </div>
                <div class="field">
                    <div class="pee"><p>Amount: </p></div>
                    <div class="pee-input"><input type="number" min="0" step="1" v-model.number="form.amount" @blur="validateField('amount')"
                    :class="{invalid: submitted && errors.amount}"/></div>
                    <div class="pee"><p v-if="submitted && errors.amount" class="error">
                        {{ errors.amount }}
                    </p></div>
                </div>
            </div>
            <div class="footer-buttons">
                <ButtonsFooter
                :show-save="true"
                :show-modify="false"
                :show-remove="false"/>
            </div>
    </form>
</template>

<style scoped>
    #addItem{
        flex-direction: column;
        flex: 9;
    }
</style>