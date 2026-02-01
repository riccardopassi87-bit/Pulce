<script setup>
  import { computed, ref, onMounted,watch } from 'vue';
  import { api } from '@/api/apiService';
  import { pizzaRules } from '@/constants/ruleSets';
  import { PIZZA_TYPE } from '@/constants/types';
  import { useForm } from '@/router/composable/useForm';

  import SearchTemplate from '@/commonViews/SearchTemplate.vue';
  import SearchPrompt from '@/commonViews/SearchPrompt.vue';
  import ButtonsFooter from '@/commonViews/ButtonsFooter.vue';
  import FormField from '@/commonViews/FormField.vue';
  
  const API_BASE = 'http://localhost:8080/api/pizza';
  const allIngredients = ref([]);

  onMounted(async () => {
    allIngredients.value = await api.get('http://localhost:8080/api/ingredient');
  });

  const existingNames = ref([]);
  const schema = pizzaRules([]);

  const { form, errors, submit, submitted, reset, remove,
        search, selectedType, searchResults: pizzas, fetchSearchResults,
        selectItem, validateField
   } = useForm({
    initialState: schema.initialState,
    rules: schema.rules,
    existingNamesRef: existingNames,
    API_BASE: API_BASE,
    SEARCH_URL: `${API_BASE}/search`,
    onSubmit: async (data) => {
      try {
        await api.put(`${API_BASE}/${data.id}`, data);
        alert('Pizza updated succesfully ✅');
        reset();
      } catch (e) { alert(e.message); }
    }
  });

  watch([search, selectedType], fetchSearchResults);

  const handleSelect = (pizza) => {
    selectItem(pizza, (p) => ({
      ...p,
      sellingPrice: Number(p.sellingPrice),
      productionPrice: Number(p.productionPrice),
      ingredientIds: p.ingredients.map(i => i.id)
    }));
  };

  const addIngredient = (event) => {
    const id = Number(event.target.value);
    if(!id || form.ingredientIds.length >= 6) return;

    if (!form.ingredientIds.includes(id)){
      form.ingredientIds.push(id);
      if (submitted.value) validateField('ingredientIds');
    }
      event.target.value = "";
  };

  const removeIngredient = (id) => {
    form.ingredientIds = form.ingredientIds.filter(itemId => itemId !== id);
    if (submitted.value) validateField('ingredientIds');
  };

  const currentIngredients = computed(() =>
    allIngredients.value.filter(i => form.ingredientIds.includes(i.id))
    );
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
                        <option disabled selected hidden></option>
                        <option v-for="t in PIZZA_TYPE" :key="t" :value="t">
                          {{ t }}
                        </option>
                      </select>
                  </template>

                  <template #results>
                    <div class="fsf">
                      <ul>
                        <li v-for="p in pizzas" :key="p.id" @click="handleSelect(p)"
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
                      <option disabled selected hidden></option>
                      <option v-for="t in PIZZA_TYPE" :key="t" :value="t">
                        {{ t }}
                      </option>
                    </select>
                  </label>
                </div>

                <div id="modify-ingredients">
                  <div id="actual-content">
                    <strong>Ingredients</strong>
                    <ul>
                      <li v-for="i in currentIngredients" :key="i.id" @click="removeIngredient(i.id)">
                        {{ i.name }}
                      </li>
                    </ul>
                  </div>
                  <div id="new-content">
                    <strong>Add Ingredient</strong>
                    <select @change="addIngredient">
                      <option disabled selected hidden></option>
                      <option v-for="i in allIngredients" :key="i.id" :value="i.id"
                      v-show="!form.ingredientIds.includes(i.id)">
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
        :show-modify="!!form.id"
        :show-remove="!!form.id"
        @modify="submit"
        @remove="remove(form.id)"/>
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