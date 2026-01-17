<script setup>
    import ButtonsFooter from '@/commonViews/ButtonsFooter.vue';
    import { useFormValidation, validators } from '@/router/composable/useFormValidation';
    import { createIngredient } from '@/api/ingredientApi';

    const API_BASE = 'http://localhost:8080/api/ingredient'
    const TYPE = ['Veggie', 'Cheese', 'Meat', 'Others']
    const ALLERGENE = ['A - Glutenhaltig', 'B - Krebstiere', 'C - Eier', 'D - Fish', 'E - Erdnüsse',
                       'F - Sojabohnen', 'G - Milch/Laktose', 'H - Schalenfrüchte', 'L - Sellerie',
                       'M - Senf', 'N - Sesamsamen', 'O - Schwefeldioxid/Sulfite', 'P - Lupinen', 'R - Weichtiere',
                       'No Allergene']
                       
    const {form, errors, submitted, validateField, submit } = useFormValidation({
        name: '',
        portionPrice: null,
        kgPrice: null,
        type: '',
        allergene: ''
    },
    {
        name: [{ validator: validators.required, message: 'Name is required' }],
        portionPrice: [
            { validator: validators.required, message: 'Required' },
            { validator: validators.number, message: 'Must be a number' }],
        kgPrice: [
            { validator: validators.required, message: 'Required' },
            { validator: validators.number, message: 'Must be a number' },
            { validator: validators.priceIsValid('portionPrice'), message: 'Kg price lower than portion price??'}],
        type: [
            { validator: validators.required, message: 'Type required' },
            { validator: validators.oneOf(TYPE), message: 'Invalid type' }],
        allergene: [
            { validator: validators.required, message: 'Allergene required' },
            { validator: validators.oneOf(ALLERGENE), message: 'Invalid allergene' }]
    },
    async (data) => {
      try {
        await createIngredient(API_BASE, data)
        alert('Ingredient saved successfully ✅')
        } catch (e) {
            alert('Failed to save ingredient ❌')
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
                <div class="pee"><p>Portion Price: </p></div>
                <div class="pee-input"><input type="number" min="0" step="0.01" v-model.number="form.portionPrice" @blur="validateField('portionPrice'); validateField('kgPrice');" 
                :class="{invalid: submitted && errors.portionPrice}"/></div>
                <div class="pee"><p v-if="submitted && errors.portionPrice" class="error">
                    {{ errors.portionPrice }}
                </p></div>
            </div>
            <div class="field">
                <div class="pee"><p>Kg Price: </p></div>
                <div class="pee-input"><input type="number" min="0" step="0.01" v-model.number="form.kgPrice" @blur="validateField('kgPrice')" 
                :class="{invalid: submitted && errors.kgPrice}"/></div>
                <div class="pee"><p v-if="submitted && errors.kgPrice" class="error">
                    {{ errors.kgPrice }}
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
                <div class="pee"><p>Allergene: </p></div>
                <div class="pee-input"><select v-model="form.allergene" @blur="validateField('allergene')" 
                :class="{invalid: submitted && errors.allergene}">
                    <option disabled value=""></option>
                    <option v-for="a in ALLERGENE" :key="a" :value="a">
                        {{ a }}
                    </option>
                </select></div>
                <div class="pee"><p v-if="submitted && errors.allergene" class="error">
                    {{ errors.allergene }}
                </p></div>
            </div>
        </div>
        <div class="footer-buttons">
            <ButtonsFooter 
            :show-save="true"
            :show-modify="false"
            :show-remove="false"
            />
        </div>
    </form>
</template>

<style scoped>
    #addItem{
        flex-direction: column;
        flex: 9;
    }
</style>