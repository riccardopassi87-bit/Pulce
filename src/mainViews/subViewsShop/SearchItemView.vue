<script setup>
    import { ref, watch } from 'vue';
    import { api } from '@/api/apiService';
    import { productRules } from '@/constants/ruleSets';
    import { PRODUCT_TYPE } from '@/constants/types';
    import { useForm } from '@/router/composable/useForm';
    import { useAlert } from '@/router/composable/useAlert';
    import { useRemove } from '@/router/composable/useRemove';

    import SearchTemplate from '@/commonViews/SearchTemplate.vue';
    import SearchPrompt from '@/commonViews/SearchPrompt.vue';
    import ButtonsFooter from '@/commonViews/ButtonsFooter.vue';
    import FormField from '@/commonViews/FormField.vue';

    const { showAlert } = useAlert();
    const API_BASE = 'http://localhost:8080/api/item';
    const existingNames = ref([]);
    const schema = productRules([]);

    const { form, errors, submit, submitted, reset, search, selectedType, displayName, handleSelect,
        searchResults: products, fetchSearchResults, validateField
    } = useForm ({
        initialState: schema.initialState,
        rules: schema.rules,
        existingNamesRef: existingNames,
        API_BASE: API_BASE,
        SEARCH_URL: API_BASE,
        onSubmit: async (data) => {
            try {
                await api.put(`${API_BASE}/${data.id}`, data);
                showAlert({
                    title: 'Success!',
                    message: 'Product successfully modified! ✅',
                    type: 'success'
                })
                displayName.value = data.name;
                reset();
            } catch (e) {
                showAlert({
                    title: 'Error',
                    message: 'Upload failed! ❌',
                    type: 'error',
                    options: ['Close']
                })
            }
        }
    });

    const { remove } = useRemove({ API_BASE, form, showAlert, reset, onSuccess: fetchSearchResults})

    watch([search, selectedType], fetchSearchResults);

</script>

<template>
    <div class="parent-view fsf">
        <div id="search-item" class="fsf">
            <SearchTemplate>
                <template #left-search>
                    <SearchPrompt>
                        <template #input>
                            <input class="own-input" v-model="search" placeholder="search by name"/>
                        </template>
                        <template #filter>
                            <select v-model="selectedType">
                                <option value=""></option>
                                <option v-for="t in PRODUCT_TYPE" :key="t" :value="t">
                                    {{ t }}
                                </option>
                            </select>
                        </template>
                        <template #results>
                            <div class="fsf">
                                <ul>
                                    <li v-for="p in products" :key="p.id" @click="handleSelect(p)"
                                    :class="{selected: form.id === p.id}">
                                        {{ p.name }}
                                    </li>
                                </ul>
                            </div>
                        </template>
                    </SearchPrompt>
                </template>
                
                <template #result>
                    <div class="fsf search-result" v-if="form.id">
                        <div class="title">
                            <h3>{{ displayName }}</h3>
                        </div>
                        <div class="modify-container">
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
                    </div>    
                    <div v-else class="fsf search-result">
                    </div>
                </template>
            </SearchTemplate>
        </div>
        <div class="footer-buttons">
            <ButtonsFooter
            :show-save="false"
            :show-modify="!!form.id"
            :show-remove="!!form.id"
            @modify="submit"
            @remove="remove"/>
        </div>
    </div>
</template>

<style scoped>
    #search-item{
        flex: 9;
    }
    select{
        height: 70%;
        width: 70%;
        background-color: #222;
    }
    li{
        font-size: 1.3rem;
    }
    #search-result input{
        width: 68%;
    }
    #search-result select{
        height: 100%;
    }
</style>