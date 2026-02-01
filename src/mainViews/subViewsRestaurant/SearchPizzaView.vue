<script setup>
  import SearchTemplate from '@/commonViews/SearchTemplate.vue';
  import SearchPrompt from '@/commonViews/SearchPrompt.vue';
  import ButtonsFooter from '@/commonViews/ButtonsFooter.vue';
  import { computed, ref, onMounted } from 'vue';

  import { pizzaRules } from '@/constants/ruleSets';
  import { api } from '@/api/apiService';
  import { PIZZA_TYPE } from '@/constants/types';

  const API_BASE = 'http://localhost:8080/api/pizza';
  
  const allIngredients = ref([]);
  onMounted(async () => {
    allIngredients.value = await api.get('http://localhost:8080/api/ingredient');
  });

  const schema = pizzaRules([]);
  const { form, errors, submit, resetForm } = useFormValidation(
    schema.initialState,
    schema.rules,
    async (data) => await handleUpdate(data)
  );
 
  const { search, selectedType, items: pizzas, selectedItem: selectedPizza, selectItem, resetSelection} = 
    useFormManagement(API_BASE, (pizza) => {
      Object.assign(form, {
        ...pizza,
        sellingPrice: Number(pizza.sellingPrice),
        productionPrice: Number(pizza.productionPrice),
        ingredientIds: pizza.ingredients.map(i => i.id)
      });
    });
  
  const addIngredient = (ingredientId) => {
    const id = Number(ingredientId)

    if(form.ingredientIds.length >= 7){
      alert("NOOOOOOOOO!!!!!!")
      return;}

    if(!form.ingredientIds.includes(id)){
      form.ingredientIds.push(id);
      event.target.value = "";
    }
  };

  const removeIngredient = (id) => {
    form.ingredientIds = form.ingredientIds.filter(itemId => itemId !== id);
  };

  const currentIngredients = computed(() =>
  allIngredients.value.filter(i => form.ingredientIds.includes(i.id))
  );

  const handleUpdate = async (data) => {
    try{
      await api.put(`${API_BASE}/${selectedPizza.value.id}`, data);
      alert('Pizza updated successfully! ✅');
        resetSelection();
        resetForm();
    } catch (e) { alert(e.message); }
  };

  const removePizza = async () => {
    if (!selectedPizza.value || !confirm('Delete pizza?')) return;

    try{
      await api.delete(`${API_BASE}/${selectedPizza.value.id}`);
        alert('Pizza deleted! ✅');
        resetSelection();
        resetForm();
    } catch (e) { alert(e.message); }
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
                        <option disabled selected hidden></option>
                        <option v-for="t in PIZZA_TYPE" :key="t" :value="t">
                          {{ t }}
                        </option>
                      </select>
                  </template>

                  <template #results>
                    <div class="fsf">
                      <ul>
                        <li v-for="p in pizzas" :key="p.id" @click="selectItem(p)"
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
                    <select @change="addIngredient($event.target.value)">
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
        :show-modify="!!selectedPizza"
        :show-remove="!!selectedPizza"
        @modify="submit"
        @remove="removePizza"/>
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