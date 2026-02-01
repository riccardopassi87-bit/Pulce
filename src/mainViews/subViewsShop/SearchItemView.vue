<script setup>
    import SearchTemplate from '@/commonViews/SearchTemplate.vue';
    import SearchPrompt from '@/commonViews/SearchPrompt.vue';
    import ButtonsFooter from '@/commonViews/ButtonsFooter.vue';
    import { PRODUCT_TYPE } from '@/constants/types';

    import { productRules } from '@/constants/ruleSets';
    import { api } from '@/api/apiService';

    const API_BASE = 'http://localhost:8080/api/item'
    const schema = productRules([]);

    const { form, submit, resetForm } = useFormValidation(
        schema.initialState,
        schema.rules,
        async (data) => {
            await handleUpdate(data);
        }
    );

    const { search, selectedType, items: products, selectedItem: selectedProduct, selectItem, resetSelection } = useFormManagement(
        API_BASE,
        (item) => Object.assign(form, {...item})
    );

    const handleUpdate = async (data) => {
        try {
            await api.put(`${API_BASE}/${selectedProduct.value.id}`, data);
            alert('Updated! ✅');
            resetSelection();
            resetForm();
        } catch (e) {
            alert('Update failed ❌');
        }
    };

    const removeProduct = async () => {
        if(!selectedProduct.value || !confirm('Are you sure?')) return;

        try {
            await api.delete(`${API_BASE}/${selectedProduct.value.id}`);
        alert('Deleted! ✅');
            resetSelection();
            resetForm();
        } catch (e) {
            alert('Connection error ❌');
        }
    }

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
                                    :class="{selected: selectedProduct?.id === p.id}">
                                        {{ p.name }}
                                    </li>
                                </ul>
                            </div>
                        </template>
                    </SearchPrompt>
                </template>
                
                <template #result>
                    <div class="fsf search-result" v-if="selectedProduct">
                        <div class="title">
                            <h3>{{ selectedProduct.name }}</h3>
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
            :show-modify="!!selectedProduct"
            :show-remove="!!selectedProduct"
            @modify="submit"
            @remove="removeProduct"/>
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