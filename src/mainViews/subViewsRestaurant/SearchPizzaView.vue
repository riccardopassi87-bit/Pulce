<script setup>
  import SearchTemplate from '@/commonViews/SearchTemplate.vue';
  import SearchPrompt from '@/commonViews/SearchPrompt.vue';
  import ButtonsFooter from '@/commonViews/ButtonsFooter.vue';

  import { ref, watch, computed } from 'vue'

  const form = ref({
    name: '',
    sellingPrice: null,
    productionPrice: null,
    type: '',
    ingredientIds: []
  })

  const search = ref('')
  const selectedType = ref('')
  const pizzas = ref([])
  const selectedPizza = ref(null)
  const allIngredients = ref([])
  const selectedIngredients = ref([])

  const fetchAllIngredients = async () => {
    const res = await fetch('http://localhost:8080/api/ingredient')
    allIngredients.value = await res.json()
  }
  fetchAllIngredients()

  const selectPizza = (pizza) => {
    selectedPizza.value = pizza
    selectedIngredients.value = [...pizza.ingredients]

    form.value = {
      name: pizza.name,
      sellingPrice: pizza.sellingPrice.toFixed(2),
      productionPrice: pizza.productionPrice.toFixed(2),
      type: pizza.type,
      ingredientIds: pizza.ingredients.map(i => i.id)
    }
  }

  const removeIngredient = (ingredientId) => {
    selectedIngredients.value = selectedIngredients.value.filter(i => i.id !== ingredientId)

    form.value.ingredientIds = form.value.ingredientIds.filter(id => id !== ingredientId)
  }

  const availableIngredients = computed(() => allIngredients.value.filter(
    i => !form.value.ingredientIds.includes(i.id)
  ))

  const addIngredient = (ingredient) => {
    selectedIngredients.value.push(ingredient)
    form.value.ingredientIds.push(ingredient.id)
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
      console.log('MODIFY CLICKED', form.value)
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
              <div class="title">
                  <h3>{{ selectedPizza.name }}</h3>
                </div>
              <div id="mid-layer">
                <div id="left-mod-panel">
                  <label>
                    <strong>Name</strong>
                    <input v-model="form.name">
                  </label>
                  <label>
                    <strong>Selling Price</strong>
                    <input type="number" v-model="form.sellingPrice">
                  </label>
                  <label>
                    <strong>Production Price</strong>
                    <input type="number" v-model="form.productionPrice">
                  </label>
                  <label>
                    <strong>Type</strong>
                    <select v-model="form.type">
                      <option v-for="t in TYPE" :key="t" :value="t">
                        {{ t }}
                      </option>
                    </select>
                  </label>
                </div>

                <div id="modify-ingredients">
                  <div id="actual-content">
                    <strong>Ingredients</strong>
                    <ul>
                      <li v-for="i in selectedIngredients" :key="i.id" @click="removeIngredient(i.id)">
                        {{ i.name }}
                      </li>
                    </ul>
                  </div>
                  <div id="new-content">
                    <strong>Add Ingredient</strong>
                    <select @change="addIngredient(availableIngredients[$event.target.selectedIndex])">
                      <option v-for="i in availableIngredients" :key="i.id">
                        {{ i.name }}
                      </option>
                    </select>
                  </div>
                </div>
              </div>
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
  #mid-layer{
    display: flex;
    flex-direction: row;
    flex: 9.2;
  }
  li{
    font-size: 1.3rem;
  }
  #left-mod-panel{
    flex: 1;
    display: flex;
    flex-direction: column;
  }
  #left-mod-panel select{
    width: 97%;
  }
  #modify-ingredients{
    flex: 1;
    border-left: 1px solid #777;
    display: flex;
    flex-direction: column;
  }
  #actual-content{
    flex: 3;
    padding: 3%;
    padding-right: 0;
  }
  #actual-content > ul{
    margin-top: 3%;
    background-color: #333;
    border-radius: 5px;
  }
  #actual-content > ul > li{
    font-size: 1rem;
    color: rgb(187, 150, 80);
  }
  #new-content{
    display: flex;
    flex-direction: column;
    justify-content: end;
    flex: 1;
    padding: 3%;
    padding-right: 0;
  }
  #new-content select{
    flex: 0.7;
    width: 100%;
  }
  #search-result input{
    width: 95%;
  }
  label{
    margin-bottom: 4%;
  }
</style>