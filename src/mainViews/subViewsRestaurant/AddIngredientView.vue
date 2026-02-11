<script setup>
    import { ingredientRules } from '@/constants/ruleSets';
    import { ref } from 'vue';
    import { ALLERGENE_MAP, INGREDIENT_TYPE } from '@/constants/types';
    import { useForm } from '@/router/composable/useForm';
    import { useAlert } from '@/router/composable/useAlert';
    import { useAdd } from '@/router/composable/useUse';
    
    import ButtonsFooter from '@/commonViews/ButtonsFooter.vue';
    import FormField from '@/commonViews/FormField.vue';
    
    const { showAlert } = useAlert();
    const API_BASE = 'http://localhost:8080/api/ingredient'
    const existingNames = ref([])

    const schema = ingredientRules(existingNames)

    const {form, errors, submit, submitted, validateField, reset
    } = useForm({
        initialState: schema.initialState,
        rules: schema.rules,
        existingNamesRef: existingNames,
        API_BASE: API_BASE,
        onSubmit: async () => await applyAdd()
    });

    const { add } = useAdd({ API_BASE, form, showAlert, reset, existingNames});
    const applyAdd = async () => {await add();};                      
</script>

<template>
    <form class="parent-view fsf" @submit.prevent="submit">
        <div class="fsf" id="addItem">
            <FormField label="Name:" :error="errors.name" :submitted="submitted" v-slot="{ isInvalid }">
                <input v-model="form.name" @input="validateField('name')" :class="{ invalid: isInvalid }"/>
            </FormField>
            <FormField label="Portion Price:" :error="errors.portionPrice" :submitted="submitted" v-slot="{ isInvalid }">
                <input type="number" min="0" step="0.1" v-model.number="form.portionPrice"
                    @input="validateField('portionPrice'); validateField('kgPrice')" :class="{ invalid: isInvalid }"/>
            </FormField>
            <FormField label="Kg Price:" :error="errors.kgPrice" :submitted="submitted" v-slot="{ isInvalid }">
                <input type="number" min="0" step="0.1" v-model.number="form.kgPrice"
                    @input="validateField('kgPrice')" :class="{ invalid: isInvalid }"/>
            </FormField>
            <FormField label="Type:" :error="errors.type" :submitted="submitted" v-slot="{ isInvalid }">
                <select v-model="form.type" @change="validateField('type')" :class="{ invalid: isInvalid }">
                    <option value=""></option>
                    <option v-for="t in INGREDIENT_TYPE" :key="t" :value="t">{{ t }}</option>
                </select>
            </FormField>
            <FormField label="Allergene:" :error="errors.allergene" :submitted="submitted" v-slot="{ isInvalid }">
                <select v-model="form.allergene" @change="validateField('allergene')" :class="{ invalid: isInvalid }">
                    <option value=""></option>
                    <option v-for="a in ALLERGENE_MAP" :key="a.code" :value="a.code">{{ a.label }}</option>
                </select>
            </FormField>
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