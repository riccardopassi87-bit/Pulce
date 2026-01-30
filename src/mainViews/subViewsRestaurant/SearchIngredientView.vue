<script setup>
    import SearchPrompt from '@/commonViews/SearchPrompt.vue';
    import SearchTemplate from '@/commonViews/SearchTemplate.vue';
    import ButtonsFooter from '@/commonViews/ButtonsFooter.vue';
    import { useFormValidation } from '@/router/composable/useFormValidation';
    import { useFormManagement } from '@/router/composable/useFormManagement';
    import { ingredientRules } from '@/constants/ruleSets';
    import { INGREDIENT_TYPE, ALLERGENE } from '@/constants/types';
    import { api } from '@/api/apiService';

    const API_BASE = 'http://localhost:8080/api/ingredient';

    const schema = ingredientRules([]);

    const { form, submit, resetForm} = useFormValidation(
        schema.initialState,
        schema.rules,
        async (data) => {
            await handleUpdate(data);
        }
    );

    const { search, selectedType, items: ingredients, selectedItem: selectedIngredient, selectItem, resetSelection} = useFormManagement(
        API_BASE,
        (item) => Object.assign(form, { ...item })
    );

    const handleUpdate = async (data) => {
        try {
            const isPriceChanged = data.portionPrice !== selectedIngredient.value.portionPrice;
            if (isPriceChanged) {
                const impactRes = await fetch(`${API_BASE}/${selectedIngredient.value.id}/impact`);
                const pizzas = await impactRes.json();
                if (pizzas.length > 0 && !confirm(`Affects: ${pizzas.join(', ')}. Continue?`)) return;  
            }

            await api.put(`${API_BASE}/${selectedIngredient.value.id}`, data);
        alert('Updated! ✅');
        resetSelection();
        resetForm();
        } catch (e) {
            alert('Update failed ❌');
        }
    };

    const removeIngredient = async () => {
        if (!selectedIngredient.value || !confirm('Are you sure?')) return;

        try {
            await api.delete(`${API_BASE}/${selectedIngredient.value.id}`);
            alert('Deleted! ✅');
            resetSelection();
            resetForm();
        } catch (e) {
            alert('Connection error ❌');
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
                                    :class="{selected: selectedIngredient?.id === i.id }">
                                        {{ i.name }}
                                    </li>
                                </ul>
                            </div>
                        </template>
                    </SearchPrompt>
                </template>

                <template #result>
                    <div class="fsf search-result" v-if="selectedIngredient">
                        <div class="title">
                            <h3>{{ selectedIngredient.name }}</h3>
                        </div>
                         <label>
                            <strong>Name</strong>
                            <input v-model="form.name" />
                         </label>
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
            :show-modify="!!selectedIngredient"
            :show-remove="!!selectedIngredient"
            @modify="submit"
            @remove="removeIngredient"/>
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