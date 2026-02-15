<script setup>
  import { ref, nextTick, onMounted, computed } from 'vue';
  import { api } from '@/api/apiService';
  import { pizzaRules } from '@/constants/ruleSets';
  import { PIZZA_TYPE } from '@/constants/types';
  import { useSearch } from '@/router/composable/useService';
  import { useForm } from '@/router/composable/useForm';

  import SearchTemplate from '@/commonViews/SearchTemplate.vue';
  import SearchPrompt from '@/commonViews/SearchPrompt.vue';
  import FormPrint from './FormPrint.vue';
  import html2pdf from 'html2pdf.js';
  
  const API_BASE = 'http://localhost:8080/api/pizza';
  const allIngredients = ref([]);
  const printList = ref([]);
  
  const isMainTitle = ref(false);
  const toggleMainTitle = async () => {
    
    if (isMainTitle.value) {
        isMainTitle.value = false;
        isFull.value = false;
        return;
    }
    isMainTitle.value = true;
    await nextTick();


    if(paperContent.value){
        const hasOverflowed = paperContent.value.scrollHeight > paperContent.value.clientHeight;
        
        if(hasOverflowed) {
            isMainTitle.value = false;

            isFull.value = true;
            setTimeout(() => { isFull.value = false;}, 500);
        }
    }
  };

  onMounted(async () => {
    allIngredients.value = await api.get('http://localhost:8080/api/ingredient');
  });

  const schema = pizzaRules([]);

  const removeFromPrint = (id) => {
    printList.value = printList.value.filter(p => p.id !== id);
    isFull.value = false;
  }
  const paperContent = ref(null);
  const isFull = ref(false);

  const { search, selectedType, selectedIngredient, searchResults: pizzas,
    applyFilter
  } = useSearch(API_BASE);

  const { form } = useForm({
    initialState: schema.initialState,
    rules: schema.rules,
    API_BASE: API_BASE,
    SEARCH_URL: API_BASE,
  });

  const filteredPizzas = computed(() => {
    if(!selectedIngredient.value || selectedIngredient.value === "") return pizzas.value;

    const targetId = Number(selectedIngredient.value);

    return pizzas.value.filter(p =>
      p.ingredients?.some(ing => Number(ing.id) === targetId)
    );
  });

  const handleSelect = async (pizza) => {
    const exists = printList.value.some(p => p.id === pizza.id);

    if(!exists) {
        printList.value.push({
            ...pizza,
            sellingPrice: Number(pizza.sellingPrice).toFixed(2)
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

  const handlePrint = () =>{
    const element = document.getElementById('paper');

    const options = {
        margin: 0,
        filename: 'pizza-menu.pdf',
        image: { type: 'jpeg', quality: 1.0 },
        html2canvas: {
            scale: 4,
            useCORS: true,
            backgroundColor: '#ffffff',
            width: element.offsetWidth,
            height: element.offsetHeight,
            scrollY: 0,
            scrollX: 0
        },
        jsPDF: {
            unit: 'px',
            format: [element.offsetWidth, element.offsetHeight],
            orientation: 'portrait',
        }
    };
    html2pdf().set(options).from(element).toPdf().get('pdf').then((pdf) => {
        const totalPages = pdf.internal.getNumberOfPages();
        for (let i = totalPages; i > 1; i--) {
            pdf.deletePage(i);
        }
    }).save();
  };
</script>

<template>
    <div class="parent-view fsf">
    <div id="search-pizza" class="fsf">
      <SearchTemplate>
          <template #left-search>
              <SearchPrompt>
                <template #section-header>
                        <button id="first-page" @click="toggleMainTitle"
                        :class="{ 'first-page-remove' : isMainTitle }">{{ isMainTitle ? 'Remove first page header' : 'Add first page header' }}</button>
                </template>
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
                        :class="{'selected': form.id === p.id,
                                'already-in-print': printList.some(item => item.id === p.id)
                        }">
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
                            <p v-if="isMainTitle" id="main-title"> Speisen </p>
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
            <button @click="handlePrint">
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
    @font-face {
    font-family: 'Beyond';
    src: url('../../assets/fonts/Beyond\ Wonderland.ttf') format('truetype');
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
    #first-page{
        flex: 1;
        width: 60%;
        font-size: 0.7rem;
        border: 1px solid rgb(151, 216, 151);
        transition: all 0.2s ease;
    }
    #first-page.first-page-remove{
        border: 1px solid rgb(182, 43, 43);
    }
    #first-page:hover{
        font-size: 0.75rem;
    }
    #search-pizza{
        flex: 9;
    }
    select{
        height: 70%;
        flex: 1;
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
        background-color: white;
        display: flex;
        flex-direction: column;
        width: 400px;
        height: 566px;
        padding: 20px;
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
        font-size: 24px;
        font-family: 'InkFree';
        margin-bottom: 12px;
        color: black;
    }
    li{
        font-size: 1.3rem;
    }
    .already-in-print{
        color: #444;
    }
    #main-title{
        font-family: 'beyond';
        text-align: center;
        font-size: 68px;
        
        margin-bottom: 10px;
        color: black;
    }
</style>

