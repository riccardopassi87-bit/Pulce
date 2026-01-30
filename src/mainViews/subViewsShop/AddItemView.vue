<script setup>
    import ButtonsFooter from '@/commonViews/ButtonsFooter.vue';
    import { useFormValidation } from '@/router/composable/useFormValidation';
    import { apiService } from '@/api/apiService';
    import { onMounted, ref } from 'vue';
    import { PRODUCT_TYPE } from '@/constants/types';
    import { productRules } from '@/constants/ruleSets';

    const API_BASE = 'http://localhost:8080/api/item'
    const existingProduct = ref([])

    onMounted(async () => {
        try {
            const res = await fetch(API_BASE)
            const data = await res.json()
            existingProduct.value = data.map(item => item.name)
        } catch (e) {
            console.error("Could not load names for validation")
        }
    })

    const schema = productRules(existingProduct)
    
    const { form, errors, submitted, validateField, submit } = useFormValidation(
    schema.initialState,
    schema.rules,
    async (data) => {
      try {
        await apiService(API_BASE, data)
        alert('Product saved successfully ✅')
        existingProduct.value.push(data.name);
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
                        <option disabled selected hidden></option>
                        <option v-for="t in PRODUCT_TYPE" :key="t" :value="t">
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