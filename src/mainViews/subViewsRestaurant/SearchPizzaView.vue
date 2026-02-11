<script setup>
  import { computed, ref, onMounted } from 'vue';
  import { api } from '@/api/apiService';
  import { pizzaRules } from '@/constants/ruleSets';
  import { PIZZA_TYPE } from '@/constants/types';
  import { useSearch } from '@/router/composable/useSearch';
  import { useForm } from '@/router/composable/useForm';
  import { useAlert } from '@/router/composable/useAlert';
  import { useRoute } from 'vue-router';
  import { useRemove } from '@/router/composable/useRemove';
  import { useModify } from '@/router/composable/useModify';

  import SearchTemplate from '@/commonViews/SearchTemplate.vue';
  import SearchPrompt from '@/commonViews/SearchPrompt.vue';
  import ButtonsFooter from '@/commonViews/ButtonsFooter.vue';
  import FormField from '@/commonViews/FormField.vue';

  const route = useRoute();
  const { showAlert } = useAlert();
  const API_BASE = 'http://localhost:8080/api/pizza';
  const allIngredients = ref([]);

  onMounted(async () => {
    allIngredients.value = await api.get('http://localhost:8080/api/ingredient');

    if (route.query.ingredient){
      selectedIngredient.value = route.query.ingredient;
    }
  });

  const existingNames = ref([]);
  const schema = pizzaRules([]);

  const { search, selectedType, selectedIngredient, searchResults: pizzas,
     applyFilter, fetchSearchResults
   } = useSearch(API_BASE);

  const { form, errors, submit, submitted, reset, displayName,
        selectItem, validateField
   } = useForm({
    initialState: schema.initialState,
    rules: schema.rules,
    existingNamesRef: existingNames,
    API_BASE: API_BASE,
    SEARCH_URL: API_BASE,
    onSubmit: async () => await applyModify()
  });

  const { modify } = useModify({ API_BASE, form, showAlert, reset, displayName})
  const applyModify = async () => {modify();};

  const filteredPizzas = computed(() =>{
    if(!selectedIngredient.value || selectedIngredient.value === "") return pizzas.value;

    const targetId = Number(selectedIngredient.value);

    return pizzas.value.filter(p =>
      p.ingredients?.some(ing => Number(ing.id) === targetId)
    );
  });

  const { remove } = useRemove({ API_BASE, form, showAlert, reset, onSuccess: fetchSearchResults})

  const handleSelect = (pizza) => {
    selectItem(pizza, (p) => ({
      ...p,
      sellingPrice: Number(p.sellingPrice),
      productionPrice: Number(p.productionPrice),
      ingredientIds: p.ingredients.map(i => i.id)
    }));
    displayName.value = pizza.name;
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
                      <select v-model="selectedType" 
                      @change="e => applyFilter('selectedType', e.target.value, ['selectedIngredient'])">
                        <option value="">By Type</option>
                        <option v-for="t in PIZZA_TYPE" :key="t" :value="t">
                          {{ t }}
                        </option>
                      </select>
                      <select v-model="selectedIngredient"
                      @change="e => applyFilter('selectedIngredient', e.target.value, ['selectedType'])">
                          <option value="">By Ingredient</option>
                          <option v-for="ing in allIngredients" :key="ing.id" :value="ing.id">
                              {{ ing.name }}
                          </option>
                      </select>
                  </template>

                  <template #results>
                    <div class="fsf">
                      <ul>
                        <li v-for="p in filteredPizzas" :key="p.id" @click="handleSelect(p)"
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
              <div id="mid-layer">
                <div class="mod-panel">
                  <FormField label="Name:" :error="errors.name" :submitted="submitted" v-slot="{ isInvalid }">
                        <input v-model="form.name" @input="validateField('name')" :class="{ invalid: isInvalid}"/>
                    </FormField>
                    <FormField label="Type:" :error="errors.type" :submitted="submitted" v-slot="{ isInvalid }">
                        <select v-model="form.type" @change="validateField('type')" :class="{ invalid: isInvalid }">
                            <option value=""></option>
                            <option v-for="p in PIZZA_TYPE" :key="p" :value="p">{{ p }}</option>
                        </select>
                    </FormField>
                </div>
                <div class="mod-panel">
                    <FormField label="Production Price:" :error="errors.productionPrice" :submitted="submitted" v-slot="{ isInvalid }">
                        <input type="number" min="0" step="0.01" v-model.number="form.productionPrice" @input="validateField('productionPrice'); validateField('sellingPrice')"
                        :class="{ invalid: isInvalid }"/>
                    </FormField>
                    <FormField label="Selling Price:" :error="errors.sellingPrice" :submitted="submitted" v-slot="{ isInvalid }">
                        <input type="number" min="0" step="0.01"
                            v-model.number="form.sellingPrice" @input="validateField('sellingPrice')" :class="{ invalid: isInvalid}"/>
                    </FormField>
                </div>

                <div id="low-layer" class="fsf">
                  <div id="new-content">
                    <p>Add Ingredient:</p>
                    <select @change="addIngredient">
                      <option value=""></option>
                      <option v-for="i in allIngredients" :key="i.id" :value="i.id"
                      v-show="!form.ingredientIds.includes(i.id)">
                        {{ i.name }}
                      </option>
                    </select>
                  </div>
                  <p>Ingredients:</p>
                  <div id="actual-content" class="fsf">
                    <ul class="fsf" :class="{ invalid: submitted && errors.ingredientIds}">
                      <p v-if="submitted && errors.ingredientIds" class="error">{{ errors.ingredientIds }}</p>
                      <li id="pizza-ingredient" v-for="i in currentIngredients" :key="i.id" @click="removeIngredient(i.id)">
                        {{ i.name }}
                      </li>
                    </ul>
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
        @remove="remove"/>
      </div>
    </div>
</template>

<style scoped>
  #search-pizza{
    flex:9;
  }
  select{
    height: 70%;
    width: 48%;
    background-color: #222;
  }
  .title{
    flex: 0.5;
  }
  #mid-layer{
    display: flex;
    flex-direction: column;
    flex: 5;
  }
  #mid-layer select{
    width: 100%;
    height: 100%
  }
  #mid-layer input{
    width: 100%;
  }
  .mod-panel{
    display: flex;
    flex: 1.5;
    gap: 2%;
  }
  #low-layer{
    flex: 5;
    flex-direction: column;
  }
  li{
    font-size: 1.3rem;
  }
  #new-content{
    flex: 2;
  }
  #new-content select{
    height: 50%;
  }
  #actual-content{
    flex: 5;
    flex-direction: column;
    background-color: #111;
  }
  #actual-content ul{
    gap: 1%;
    padding: 0;
    height: auto;
    margin-top: 1%;
    flex-wrap: wrap;
  }
  #pizza-ingredient{
    height: 3.8rem;
    width: 49.5%;
    display: flex;
    justify-content: center;
    align-items: center;
    border-radius: 5px;
    margin-bottom: 1%;
    background-color: #222;
  }
</style>