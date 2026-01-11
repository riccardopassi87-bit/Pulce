<script setup>
    import ButtonsFooter from '@/commonViews/ButtonsFooter.vue';
    import { useFormValidation, validators } from '@/router/composable/useFormValidation';

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
            { validator: validators.number, message: 'Must be a number' }],
        type: [
            { validator: validators.required, message: 'Type required' },
            { validator: validators.oneOf(TYPE), message: 'Invalid type' }],
        allergene: [
            { validator: validators.required, message: 'Allergene required' },
            { validator: validators.oneOf(ALLERGENE), message: 'Invalid allergene' }]
    },
    async (data) =>{
        alert('hewvouvwowufv')
    }
    )
</script>

<template>
    <form class="parent-view fsf" @submit.prevent="submit">
        <div class="fsf" id="addItem">
            <div><p>Name: </p> <input v-model="form.name" @blur="validateField('name')" 
                :class="{invalid: submitted && errors.name}"
                :placeholder="errors.name"/>
            </div>
            <div><p>Portion Price: </p> <input type="number" min="0" step="0.01" v-model.number="form.portionPrice" @blur="validateField('portionPrice')" 
                :class="{invalid: submitted && errors.portionPrice}"
                :placeholder="errors.portionPrice"/>
            </div>
            <div><p>Kg Price: </p> <input type="number" min="0" step="0.01" v-model.number="form.kgPrice" @blur="validateField('kgPrice')" 
                :class="{invalid: submitted && errors.kgPrice}"
                :placeholder="errors.kgPrice"/>
            </div>
            <div><p>Type: </p>
                <select v-model="form.type" @blur="validateField('type')" 
                :class="{invalid: submitted && errors.type}">
                    <option disabled value=""></option>
                    <option v-for="t in TYPE" :key="t" :value="t">
                        {{ t }}
                    </option>
                </select>
            </div>
            <div><p>Allergene: </p>
                <select v-model="form.allergene" @blur="validateField('allergene')" 
                :class="{invalid: submitted && errors.allergene}">
                    <option disabled value=""></option>
                    <option v-for="a in ALLERGENE" :key="a" :value="a">
                        {{ a }}
                    </option>
                </select>
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
        gap: 5%;
        font-size: 1.2rem;
        flex: 9;
    }
    p{
        margin-bottom: 1%;
    }
</style>