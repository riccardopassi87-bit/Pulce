<script setup>
  import SearchTemplate from '@/commonViews/SearchTemplate.vue';
  import SearchPrompt from '@/commonViews/SearchPrompt.vue';
  import ButtonsFooter from '@/commonViews/ButtonsFooter.vue';

  import { ref, watch } from 'vue'

  const form = ref({
    name: '',
    sellingPrice: null,
    originalPrice: null,
    type: '',
    ingredientIds: []
  })

  const search = ref('')
  const selectedType = ref('')
  const pizzas = ref([])
  const selectedPizza = ref(null)

  const selectPizza = (pizza) => {
    selectedPizza.value = pizza

    form.value = {
      name: pizza.name,
      sellingPrice: pizza.sellingPrice,
      originalPrice: pizza.originalPrice,
      type: pizza.type,
      ingredientIds: []
    }
  }

  const fetchPizzas = async () => {
    if(!search.value && !selectedType.value) {
      pizzas.value = []
      selectedPizza.value = null
      return
    }

    const params = new URLSearchParams()
    if(search.value) params.append('name', search.value)
    if(selectedType.value) params.append('type', selectedType.value)

    const res = await fetch(
      `http://localhost:8080/api/pizza/search?${params.toString()}`
    )
    pizzas.value = await res.json()
    if (selectedPizza.value && !pizzas.value.some(i => i.id === selectedPizza.value.id))
    {
      selectedPizza.value = null
    }
  }

  watch([search, selectedType], fetchPizzas, { immediate: true})

  const TYPE = ['Normal', 'Vegetarian', 'Vegan']

  const modifyPizza = async () => {
    if (!selectedPizza.value) return

    try{
      const res = await fetch(
        `http://localhost:8080/api/pizza/${selectedPizza.value.id}`,
        {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(form.value)
        }
      )
      if (!res.ok) throw new Error()

      await fetchPizzas()
      pizzas.value = []
      selectedPizza.value = null
            alert('Pizza updated ✅')
        } catch (e) {
            alert('Update failed ❌')
        }
  }
</script>

<template>
  <div class="parent-view fsf">
    <div id="search-pizza" class="fsf">
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
                        <li v-for="p in pizzas" :key="p.id" @click="selectPizza(p)"
                        :class="{selected: selectedPizza?.id === p.id}">
                          {{ p.name }}
                        </li>
                      </ul>
                    </div>
                  </template>
              </SearchPrompt>
          </template>

          <template #result>
            <div class="fsf search-result" v-if="selectedPizza">
              <ul>
                <li v-for="i in selectedPizza.ingredients" :key="i">
                  {{ i.name }}
                </li>
              </ul>
            </div>
            <div v-else class="fsf search-result"></div>
          </template>
      </SearchTemplate>
      </div>
      <div class="footer-buttons">
        <ButtonsFooter
        :show-save="false"
        :show-modify="!!selectedPizza"
        :show-remove="!!selectedPizza"
        @modify="modifyPizza"/>
      </div>
    </div>
</template>

<style scoped>
  #search-pizza{
    flex:9;
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