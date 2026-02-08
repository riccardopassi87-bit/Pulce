<script setup>
  import { ref, watch, nextTick } from 'vue';
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
    isFull.value = false;
  }
  const paperContent = ref(null);
  const isFull = ref(false);

  const { form, search, selectedType,
        searchResults: pizzas, fetchSearchResults
   } = useForm({
    initialState: schema.initialState,
    rules: schema.rules,
    API_BASE: API_BASE,
    SEARCH_URL: API_BASE,
  });

  watch([search, selectedType], fetchSearchResults);

  const handleSelect = async (pizza) => {
    const exists = printList.value.some(p => p.id === pizza.id);

    if(!exists) {
        printList.value.push({
            ...pizza,
            sellingPrice: Number(pizza.sellingPrice)
        });

        await nextTick();

        if (paperContent.value) {
            isFull.value = paperContent.value.scrollHeight > paperContent.value.clientHeight;

            if(isFull.value) {
                printList.value.pop();

                isFull.value = true;

                setTimeout(() => {
                    isFull.value = false;
                }, 500);
            } else {
                isFull.value = false;
            }
        }
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
                        <div id="paper" ref="paperContent" :class="{ overflowing: isFull }">
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
    @keyframes shake {
    0% { transform: translateX(0); }
    10% { transform: translateX(-4px); }
    20% { transform: translateX(4px); }
    30% { transform: translateX(-4px); }
    40% { transform: translateX(4px); }
    50% { transform: translateX(-4px); }
    60% { transform: translateX(4px); }
    70% { transform: translateX(-4px); }
    80% { transform: translateX(4px); }
    90% { transform: translateX(-4px); }
    100% { transform: translateX(0); }
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
        transition: border 0.3s ease, box-shadow 0.3s ease;
        border: 2px solid transparent;
        overflow: hidden;
    }
    #paper.overflowing{
        border: 2px solid red;
        box-shadow: 0 0 15px rgba(255, 68, 68, 0.4);
        animation: shake 0.4s ease-in-out;
    }
    #title{
        font-size: 1.5rem;
        font-family: 'InkFree';
        margin-bottom: 3%;
        color: black;
    }
</style>

