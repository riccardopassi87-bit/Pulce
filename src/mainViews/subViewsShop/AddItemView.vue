<script setup>
    import { productRules } from '@/constants/ruleSets';
    import { ref } from 'vue';
    import { api } from '@/api/apiService';
    import { PRODUCT_TYPE } from '@/constants/types';
    import { useForm } from '@/router/composable/useForm';

    import ButtonsFooter from '@/commonViews/ButtonsFooter.vue';
    import FormField from '@/commonViews/FormField.vue';

    const API_BASE = 'http://localhost:8080/api/item'
    const existingNames = ref([])

    const schema = productRules(existingNames)
    
    const { form, errors, submit, submitted, validateField, reset 
    } = useForm({
    initialState: schema.initialState,
    rules: schema.rules,
    existingNamesRef: existingNames,
    API_BASE: API_BASE,
    onSubmit: async (data) => {
        try{
            await api.post(API_BASE, data);
            alert('Product saved succesfully ✅');
            existingNames.value.push(data.name);
            reset();
        }catch (e) { alert('Error saving');}
    }
});
</script>

<template>
    <form class="parent-view fsf" @submit.prevent="submit">
            <div class="fsf" id="addItem">
                <FormField label="Name:" :error="errors.name" :submitted="submitted" v-slot="{ isInvalid }">
                    <input v-model="form.name" @input="validateField('name')" :class="{ invalid: isInvalid }"/>
                </FormField>
                <FormField label="Original Price:" :error="errors.originalPrice" :submitted="submitted" v-slot="{ isInvalid }">
                    <input type="number" min="0" step="0.1" v-model.number="form.originalPrice"
                        @input="validateField('originalPrice'); validateField('sellingPrice')" :class="{ invalid: isInvalid }"/>
                </FormField>
                <FormField label="Selling Price:" :error="errors.sellingPrice" :submitted="submitted" v-slot="{ isInvalid }">
                    <input type="number" min="0" step="0.1" v-model.number="form.sellingPrice"
                        @input="validateField('sellingPrice')" :class="{ invalid: isInvalid }"/>
                </FormField>
                <FormField label="Type:" :error="errors.type" :submitted="submitted" v-slot="{ isInvalid }">
                    <select v-model="form.type" @change="validateField('type')" :class="{ invalid: isInvalid }">
                        <option value=""></option>
                        <option v-for="t in PRODUCT_TYPE" :key="t" :value="t">{{ t }}</option>
                    </select>
                </FormField>
                <FormField label="Expiration Date:" :error="errors.expirationDate" :submitted="submitted" v-slot="{ isInvalid }">
                    <input type="date" v-model="form.expirationDate" @input="validateField('expirationDate')" :class="{ invalid: isInvalid }"/>
                </FormField>
                <FormField label="Amount:" :error="errors.amount" :submitted="submitted" v-slot="{ isInvalid }">
                    <input type="number" min="1" step="1" v-model.number="form.amount" @input="validateField('amount')" :class="{ invalid: isInvalid }"/>
                </FormField>
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