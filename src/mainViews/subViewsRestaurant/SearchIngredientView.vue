<script setup>
    import { ref, watch } from 'vue';
    import { api } from '@/api/apiService';
    import { ingredientRules } from '@/constants/ruleSets';
    import { INGREDIENT_TYPE, ALLERGENE } from '@/constants/types';
    import { useForm } from '@/router/composable/useForm';

    import SearchPrompt from '@/commonViews/SearchPrompt.vue';
    import SearchTemplate from '@/commonViews/SearchTemplate.vue';
    import ButtonsFooter from '@/commonViews/ButtonsFooter.vue';
    import FormField from '@/commonViews/FormField.vue';
    
    const API_BASE = 'http://localhost:8080/api/ingredient';
    const existingNames = ref([]);
    const schema = ingredientRules();

    const { form, errors, submit, submitted, reset, search, selectedType,
        searchResults: ingredients, fetchSearchResults, selectItem, validateField
     } = useForm({
        initialState: schema.initialState,
        rules: schema.rules,
        existingNamesRef: existingNames,
        API_BASE: API_BASE,
        SEARCH_URL: `${API_BASE}/search`,
        onSubmit: async (data) => {

            const originalPrice = ingredients.value.find(i => i.id === data.id);
            const changedPrice = originalPrice && (originalPrice.portionPrice !== data.portionPrice ||
                originalPrice.kgPrice !== data.kgPrice
            );

            if (changedPrice) {
                try {
                    const affectedPizzas = await api.get(`${API_BASE}/${data.id}/impact`);

                    if(affectedPizzas.length > 0) {
                        const pizzaList = affectedPizzas.map(name => `• ${name}`).join('\n');
                        const proceed = confirm(
                            `Changing the price will affect the following pizzas:\n\n${pizzaList}\n\nDo you want to proceed?`
                        );
                        if (!proceed) return;
                    }
                } catch (e) {
                    console.error("Impact check failed", e);
                }
            }

            try {
                await api.put(`${API_BASE}/${data.id}`, data);
                alert('Ingredient uploaded ✅');
                reset();
            } catch (e) {alert(e.message);}
        }
    });

    watch([search, selectedType], fetchSearchResults);
    
    const handleRemove = async () => {
    if (!form.id || !confirm("Are you sure?")) return;

    try {
        await api.delete(`${API_BASE}/${form.id}`);

        alert('Ingredient deleted! ✅');
        handleResetAll();
        fetchSearchResults();
    } catch (e) {
        alert(e.message); 
    }
};

</script>

<template>
    <div class="parent-view fsf">
        <div id="search-ingredient" class="fsf">
            <SearchTemplate>
                <template #left-search>
                    <SearchPrompt>
                        <template #input>
                            <input class="own-input" v-model="search" placeholder="search by name"/>
                        </template>
                        <template #filter>
                            <select v-model="selectedType">
                                <option disabled selected hidden></option>
                                <option v-for="t in INGREDIENT_TYPE" :key="t" :value="t">
                                    {{ t }}
                                </option>
                            </select>
                        </template>
                        <template #results>
                            <div class="fsf">
                                <ul>
                                    <li v-for="i in ingredients" :key="i.id" @click="selectItem(i)"
                                    :class="{selected: form.id === i.id }">
                                        {{ i.name }}
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
                        <FormField label="Name:" :error="errors.name" :submitted="submitted" v-slot="{ isInvalid }">
                        <input v-model="form.name" @input="validateField('name')" :class="{ invalid: isInvalid}"/>
                    </FormField>
                         <!-- <label>
                            <strong>Name</strong>
                            <input v-model="form.name"/>
                         </label> -->
                         <label>
                            <strong>Type</strong>
                            <select v-model="form.type">
                                <option disabled selected hidden></option>
                                <option v-for="t in INGREDIENT_TYPE" :key="t" :value="t">
                                    {{ t }}
                                </option>
                            </select>
                         </label>
                         <label>
                            <strong>Portion Price</strong>
                            <input type="number" step="0.01" v-model.number="form.portionPrice" />
                        </label>

                        <label>
                            <strong>Kg Price</strong>
                            <input type="number" step="0.01" v-model.number="form.kgPrice" />
                        </label>

                        <label>
                            <strong>Allergene</strong>
                            <select v-model="form.allergene">
                                <option disabled selected hidden></option>
                                <option v-for="a in ALLERGENE" :key="a" :value="a">
                                    {{ a }}
                                </option>
                            </select>
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
            @remove="handleRemove"/>
        </div>
    </div>
</template>

<style scoped>
    #search-ingredient{
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
</style>