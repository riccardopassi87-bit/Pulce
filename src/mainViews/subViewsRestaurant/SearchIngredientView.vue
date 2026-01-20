<script setup>
    import SearchPrompt from '@/commonViews/SearchPrompt.vue';
    import SearchTemplate from '@/commonViews/SearchTemplate.vue';
    import ButtonsFooter from '@/commonViews/ButtonsFooter.vue';

    import { ref, watch } from 'vue'

    const search = ref('')
    const selectedType = ref('')
    const ingredients = ref([])
    const selectedIngredient = ref(null)
    const selectIngredient = (ingredient) => {
        selectedIngredient.value = ingredient
    }

    const fetchIngredients = async () => {
        if (!search.value && !selectedType.value) {
            ingredients.value = []
            return
        }

        const params = new URLSearchParams()
        if (search.value) params.append('name', search.value)
        if (selectedType.value) params.append('type', selectedType.value)

        const res = await fetch(
            `http://localhost:8080/api/ingredient/search?${params.toString()}`
        )
        ingredients.value = await res.json()
    }
    
    watch([search, selectedType], fetchIngredients, {immediate: true})

    const TYPE = ['Veggie', 'Cheese', 'Meat', 'Base', 'Others']
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
                                <option disabled.value=""></option>
                                <option v-for="t in TYPE" :key="t" :value="t">
                                    {{ t }}
                                </option>
                            </select>
                        </template>
                        <template #results>
                            <div class="fsf">
                                <ul>
                                    <li v-for="i in ingredients" :key="i.id" @click="selectIngredient(i)"
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
                        <h3>{{ selectedIngredient.name }}</h3>

                        <p><strong>Type:</strong> {{ selectedIngredient.type }}</p>
                        <p><strong>Portion Price:</strong> {{ selectedIngredient.portionPrice.toFixed(2) }} €</p>
                        <p><strong>Kg Price:</strong> {{ selectedIngredient.kgPrice.toFixed(2) }} €</p>
                        <p><strong>Allergene:</strong> {{ selectedIngredient.allergene }}</p>
                    </div>

                    <div v-else class="fsf">
                        <p>Select an ingredient</p>
                    </div>
                </template>
            </SearchTemplate>
        </div>
        <div class="footer-buttons">
            <ButtonsFooter 
            :show-save="true"
            :show-modify="true"
            :show-remove="true"/>
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
</style>