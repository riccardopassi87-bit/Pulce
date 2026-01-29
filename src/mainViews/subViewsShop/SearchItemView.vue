<script setup>
    import SearchTemplate from '@/commonViews/SearchTemplate.vue';
    import SearchPrompt from '@/commonViews/SearchPrompt.vue';
    import ButtonsFooter from '@/commonViews/ButtonsFooter.vue';

    import { ref, watch } from 'vue'

    const form = ref({
        name: '',
        originalPrice: null,
        sellingPrice: null,
        type: '',
        expirationDate: '',
        amount: null
    })

    const search = ref('')
    const selectedType = ref('')
    const products = ref([])
    const selectedProduct = ref(null)

    const selectProduct = (product) => {
        selectedProduct.value = product

        form.value = {
            name: product.name,
            originalPrice: product.originalPrice.toFixed(2),
            sellingPrice: product.sellingPrice.toFixed(2),
            type: product.type,
            expirationDate: product.expirationDate,
            amount: product.amount
        }
    }

    const fetchProducts = async () => {
        if(!search.value && !selectedType.value) {
            products.value = []
            selectedProduct.value = null
            return
        }

        const params = new URLSearchParams()
        if (search.value) params.append('name', search.value)
        if (selectedType.value) params.append('type', selectedType.value)

        const res = await fetch(
            `http://localhost:8080/api/item/search?${params.toString()}`
        )
        products.value = await res.json()

        if(selectedProduct.value && !products.value.some(i => i.id === selectedProduct.value.id)
    ) {
        selectedProduct.value = null}
    }

    watch([search, selectedType], fetchProducts, { immediate: true })

    const TYPE = ['Alpe', 'Alpenzu', 'Bisquits', 'Snacks', 'Drinks', 'Pesto', 'Pasta', 'Antipasti', 'Olives', 'Wine', 'Others' ]

    const modifyProduct = async () => {
        if (!selectedProduct.value) return

        try{
            const res = await fetch(
                `http://localhost:8080/api/item/${selectedProduct.value.id}`,
                {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(form.value)
                }
            )
            if (!res.ok) throw new Error()

            await fetchProducts()
            products.value = []
            selectedProduct.value = null
            selectedType.value = ''
            search.value = ''
            alert('Product updated ✅')
        } catch (e) {
            alert('Update failed ❌')
        }
    }
    
    const removeProduct = async () => {
    if(!selectedProduct.value) return

    const confirmDelete = confirm(`Are you sure you want to remove "${selectedProduct.value.name}"?`)
    if (!confirmDelete) return

    try {
      const res = await fetch(
        `http://localhost:8080/api/item/${selectedProduct.value.id}`,
        { method: 'DELETE'}
      )
      if (!res.ok) throw new Error()

       await fetchProducts()
       products.value = []
       selectProduct.value = null
       selectedType.value = ''
       search.value = ''

       alert('Product deleted ✅')
      } catch (e) {
        alert('Delete failed ❌')
      }
  }
</script>

<template>
    <div class="parent-view fsf">
        <div id="search-item" class="fsf">
            <SearchTemplate>
                <template #left-search>
                    <SearchPrompt>
                        <template #input>
                            <input class="own-input" v-model="search" placeholder="search by name"/>
                        </template>
                        <template #filter>
                            <select v-model="selectedType">
                                <option disabled selected hidden></option>
                                <option v-for="t in TYPE" :key="t" :value="t">
                                    {{ t }}
                                </option>
                            </select>
                        </template>
                        <template #results>
                            <div class="fsf">
                                <ul>
                                    <li v-for="p in products" :key="p.id" @click="selectProduct(p)"
                                    :class="{selected: selectedProduct?.id === p.id}">
                                        {{ p.name }}
                                    </li>
                                </ul>
                            </div>
                        </template>
                    </SearchPrompt>
                </template>
                
                <template #result>
                    <div class="fsf search-result" v-if="selectedProduct">
                        <div class="title">
                            <h3>{{ selectedProduct.name }}</h3>
                        </div>
                        <label>
                            <strong>Name</strong>
                            <input v-model="form.name"/>
                        </label>
                        <label>
                            <strong>Original Price</strong>
                            <input type="number" step="0.01" v-model="form.originalPrice"/>
                        </label>
                        <label>
                            <strong>Selling Price</strong>
                            <input type="number" step="0.01" v-model="form.sellingPrice"/>
                        </label>
                        <label>
                            <strong>Type</strong>
                            <select v-model="form.type">
                                <option disabled selected hidden></option>
                                <option v-for="t in TYPE" :key="t" :value="t">
                                    {{ t }}
                                </option>
                            </select>
                        </label>
                        <label>
                            <strong>Expiration Date</strong>
                            <input type="date" v-model="form.expirationDate">
                        </label>
                        <label>
                            <strong>Amount</strong>
                            <input type="number" step="1" v-model="form.amount">
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
            :show-modify="!!selectedProduct"
            :show-remove="!!selectedProduct"
            @modify="modifyProduct"
            @remove="removeProduct"/>
        </div>
    </div>
</template>

<style scoped>
    #search-item{
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
    label{
      margin-top: 2%;
    }
    strong{
       margin: 0; 
    }
</style>