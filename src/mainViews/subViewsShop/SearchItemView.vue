<script setup>
    import { ref, watch } from 'vue';
    import { api } from '@/api/apiService';
    import { productRules } from '@/constants/ruleSets';
    import { PRODUCT_TYPE } from '@/constants/types';
    import { useForm } from '@/router/composable/useForm';

    import SearchTemplate from '@/commonViews/SearchTemplate.vue';
    import SearchPrompt from '@/commonViews/SearchPrompt.vue';
    import ButtonsFooter from '@/commonViews/ButtonsFooter.vue';
    import FormField from '@/commonViews/FormField.vue';

    const API_BASE = 'http://localhost:8080/api/item';
    const existingNames = ref([]);
    const schema = productRules([]);

    const { form, errors, submit, reset, remove, search, selectedType,
        searchResults: products, fetchSearchResults, selectItem
    } = useForm ({
        initialState: schema.initialState,
        rules: schema.rules,
        existingNamesRef: existingNames,
        API_BASE: API_BASE,
        SEARCH_URL: `${API_BASE}/search`,
        onSubmit: async (data) => {
            try {
                await api.put(`${API_BASE}/${data.id}`, data);
                alert('Product uploaded ✅');
                reset();
            } catch (e) {alert(e.message);}
        }
    });

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
                                <option disabled selected hidden></option>
                                <option v-for="t in PRODUCT_TYPE" :key="t" :value="t">
                                    {{ t }}
                                </option>
                            </select>
                        </template>
                        <template #results>
                            <div class="fsf">
                                <ul>
                                    <li v-for="p in products" :key="p.id" @click="selectItem(p)"
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
                            <h3>{{ form.name }}</h3>
                        </div>
                        <label>
                            <strong>Name</strong>
                            <input v-model="form.name"/>
                        </label>
                        <label>
                            <strong>Original Price</strong>
                            <input type="number" step="0.01" v-model="form.originalPrice"/>
                        </label>
                        <label>
                            <strong>Selling Price</strong>
                            <input type="number" step="0.01" v-model="form.sellingPrice"/>
                        </label>
                        <label>
                            <strong>Type</strong>
                            <select v-model="form.type">
                                <option disabled selected hidden></option>
                                <option v-for="t in PRODUCT_TYPE" :key="t" :value="t">
                                    {{ t }}
                                </option>
                            </select>
                        </label>
                        <label>
                            <strong>Expiration Date</strong>
                            <input type="date" v-model="form.expirationDate">
                        </label>
                        <label>
                            <strong>Amount</strong>
                            <input type="number" step="1" v-model="form.amount">
                        </label>
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
            @remove="remove(form.id)"/>
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
    label{
      margin-top: 2%;
    }
    strong{
       margin: 0; 
    }
</style>