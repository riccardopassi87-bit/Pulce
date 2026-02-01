<script setup>
    import { ingredientRules } from '@/constants/ruleSets';
    import { ref } from 'vue';
    import { api } from '@/api/apiService';
    import { ALLERGENE, INGREDIENT_TYPE } from '@/constants/types';
    import { useForm } from '@/router/composable/useForm';
    
    import ButtonsFooter from '@/commonViews/ButtonsFooter.vue';
    import FormField from '@/commonViews/FormField.vue';
    
    const API_BASE = 'http://localhost:8080/api/ingredient'
    const existingNames = ref([])

    const schema = ingredientRules(existingNames)

    const {form, errors, submit, submitted, validateField, reset
    } = useForm({
    initialState: schema.initialState,
    rules: schema.rules,
    existingNamesRef: existingNames,
    API_BASE: API_BASE,
    onSubmit: async (data) => {
        try {
            await api.post(API_BASE, data);
            alert('Ingredient saved succesfully ✅');
            existingNames.value.push(data.name);
            reset();
        } catch (e) { alert('Error saving'); }
    }
});
                          
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
                    <option disabled selected hidden></option>
                    <option v-for="t in INGREDIENT_TYPE" :key="t" :value="t">
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
                    <option disabled selected hidden></option>
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