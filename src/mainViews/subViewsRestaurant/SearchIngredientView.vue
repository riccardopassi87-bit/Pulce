<script setup>
    import SearchPrompt from '@/commonViews/SearchPrompt.vue';
    import SearchTemplate from '@/commonViews/SearchTemplate.vue';
    import ButtonsFooter from '@/commonViews/ButtonsFooter.vue';

    import { ref, watch } from 'vue'

    const form = ref({
        name: '',
        type: '',
        portionPrice: null,
        kgPrice: null,
        allergene: ''
    })

    const search = ref('')
    const selectedType = ref('')
    const ingredients = ref([])
    const selectedIngredient = ref(null)

    const selectIngredient = (ingredient) => {
        selectedIngredient.value = ingredient

        form.value = {
        name: ingredient.name,
        type: ingredient.type,
        portionPrice: ingredient.portionPrice.toFixed(2),
        kgPrice: ingredient.kgPrice.toFixed(2),
        allergene: ingredient.allergene
        }
    }

    const fetchIngredients = async () => {
        if (!search.value && !selectedType.value) {
            ingredients.value = []
            selectedIngredient.value = null
            return
        }

        const params = new URLSearchParams()
        if (search.value) params.append('name', search.value)
        if (selectedType.value) params.append('type', selectedType.value)

        const res = await fetch(
            `http://localhost:8080/api/ingredient/search?${params.toString()}`
        )
        ingredients.value = await res.json()

        if(selectedIngredient.value && !ingredients.value.some(i => i.id === selectedIngredient.value.id)
    ) {
        selectedIngredient.value = null
        }
    }
    
    watch([search, selectedType], fetchIngredients, {immediate: true})

    const TYPE = ['Veggie', 'Cheese', 'Meat', 'Base', 'Others']
    const ALLERGENE = ['A - Glutenhaltig', 'B - Krebstiere', 'C - Eier', 'D - Fish', 'E - Erdnüsse',
                       'F - Sojabohnen', 'G - Milch/Laktose', 'H - Schalenfrüchte', 'L - Sellerie',
                       'M - Senf', 'N - Sesamsamen', 'O - Schwefeldioxid/Sulfite', 'P - Lupinen', 'R - Weichtiere',
                       'No Allergene']

    const modifyIngredient = async () => {
        if (!selectedIngredient.value) return

        const isPriceChanged = form.value.portionPrice !== selectedIngredient.value.portionPrice

        if(isPriceChanged) {
            try {
                const impactRes = await fetch(
                    `http://localhost:8080/api/ingredient/${selectedIngredient.value.id}/impact`,
                    )

                    if(impactRes.ok){
                        const pizzaNames = await impactRes.json()

                        if (pizzaNames.length > 0) {
                            const confirmMessage = `Changing the price will affect these pizzas:\n\n` + 
                                        pizzaNames.join(", ") + 
                                        `\n\nDo you want to continue?`

                            if(!confirm(confirmMessage)) return
                        }
                    }
            } catch (err) {
                console.error("Could not check impact, err")
            }
        }
        
        try {
            const res = await fetch(
                `http://localhost:8080/api/ingredient/${selectedIngredient.value.id}`,
            
                {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(form.value)
                }
            )
            if (!res.ok) throw new Error()

            await fetchIngredients()
            ingredients.value = []
            selectedIngredient.value = null
            alert('Ingredient updated ✅')
        } catch (e) {
            alert('Update failed ❌')
        }
    }

    const removeIngredient = async () => {
    if(!selectedIngredient.value) return

    const confirmDelete = confirm(`Are you sure you want to remove "${selectedIngredient.value.name}"?`)
    if (!confirmDelete) return

    try {
      const res = await fetch(
        `http://localhost:8080/api/ingredient/${selectedIngredient.value.id}`,
        { method: 'DELETE'}
      )
      if (!res.ok) {
        const errorData = await res.json()

        alert(`Delete failed ❌\n\n${errorData.message || 'Unknown error occurred'}`);
        return;
      }

      await fetchIngredients()
      selectedIngredient.value = null

      alert('Ingredient deleted ✅')
  } catch (e) {
    console.error(e);
    alert('Could not connect to the server ❌')
  }
}
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
                                <option v-for="t in TYPE" :key="t" :value="t">
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
            @modify="modifyIngredient"
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