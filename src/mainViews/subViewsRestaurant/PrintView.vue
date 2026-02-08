<script setup>
  import { ref, onMounted, watch } from 'vue';
  import { pizzaRules } from '@/constants/ruleSets';
  import { PIZZA_TYPE } from '@/constants/types';
  import { useForm } from '@/router/composable/useForm';

  import SearchTemplate from '@/commonViews/SearchTemplate.vue';
  import SearchPrompt from '@/commonViews/SearchPrompt.vue';
  import FormPrint from './FormPrint.vue';
  
  const API_BASE = 'http://localhost:8080/api/pizza';
  const printList = ref([]);
  const schema = pizzaRules([]);
  const removeFromPrint = (id) => {
    printList.value = printList.value.filter(p => p.id !== id);
  }

  const { form, search, selectedType,
        searchResults: pizzas, fetchSearchResults, selectItem
   } = useForm({
    initialState: schema.initialState,
    rules: schema.rules,
    API_BASE: API_BASE,
    SEARCH_URL: API_BASE,
  });

  watch([search, selectedType], fetchSearchResults);

  const handleSelect = (pizza) => {
    const exists = printList.value.some(p => p.id === pizza.id);

    if(!exists) {
        printList.value.push({
            ...pizza,
            sellingPrice: Number(pizza.sellingPrice)
        });
    }
  };
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
                        <option value=""></option>
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
                    <div class="fsf search-result">
                        <div id="paper">
                            <p v-if="isMainTitle" id="main-title"></p>
                            <p id="title">Pizza</p>

                            <FormPrint 
                                v-model:selectedPizzas="printList"
                                @remove-pizza="removeFromPrint"/>

                        </div>
                    </div>
                </template>
            </SearchTemplate>
        </div>
        <div class="footer-buttons">
            <button>
                <p>&#9888; PRINT</p>
                <p>-</p>
                <p>Keeping in mind that wasting paper is not good</p>
                <p>-</p>
                <p>PRINT &#9888;</p>
            </button>
        </div>
    </div>
</template>

<style scoped>
    @font-face {
    font-family: 'InkFree';
    src: url('../../assets/fonts/Inkfree.ttf') format('truetype');
    font-weight: normal;
    font-style: normal;
    }

    #search-pizza{
        flex: 9;
    }
    select{
        height: 70%;
        width: 70%;
        background-color: #222;
    }
    button{
        height: 70%;
        border-radius: 5px;
        display: flex;
        justify-content: space-evenly;
        align-items: center;
    }
    .footer-buttons{
        display: flex;
        align-items: end;
    }
    .search-result{
        padding: 0;
        justify-content: center;
        align-items: center;
    }
    #paper{
        background-color: antiquewhite;
        display: flex;
        flex-direction: column;
        width: auto;
        height: 96%;
        aspect-ratio: 1 / 1.4142;
        padding: 3%;
    }
    #title{
        font-size: 1.5rem;
        font-family: 'InkFree';
        margin-bottom: 3%;
        color: black;
    }
</style>

