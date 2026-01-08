<script setup>
    import ButtonsFooter from '@/commonViews/ButtonsFooter.vue';
    import { reactive, ref } from 'vue'

    const form = reactive({
        name: '',
        portionPrice: null,
        kgPrice: null,
        type: '',
        allergene: ''
    })

    const TYPE = ['Veggie', 'Cheese', 'Meat', 'Others']
    const ALLERGENE = ['A - Glutenhaltig', 'B - Krebstiere', 'C - Eier', 'D - Fish', 'E - Erdnüsse',
                       'F - Sojabohnen', 'G - Milch/Laktose', 'H - Schalenfrüchte', 'L - Sellerie',
                       'M - Senf', 'N - Sesamsamen', 'O - Schwefeldioxid/Sulfite', 'P - Lupinen', 'R - Weichtiere',
                       'No Allergene']

    const submitted = ref(false)

    const errors = reactive ({})
    
    const isNumber = (val) => typeof val === 'number' && !isNaN(val)
    const isEmpty = (val) => val === null || val === undefined || val === '' || (typeof val === 'string' && val.trim() === '')

    const validateField = (field) => {
        switch (field) {
            case 'name':
                errors.name = isEmpty(form.name) ? 'Name is required' : ''
                break
            case 'portionPrice':
                errors.portionPrice = isNumber(form.portionPrice) && !isEmpty(form.portionPrice) ? '' : 'Portion Price must be a number'
                break
            case 'kgPrice':
                errors.kgPrice = isNumber(form.kgPrice) && !isEmpty(form.kgPrice) ? '' : 'Kg Price must be a number'
                break
            case 'type':
                errors.type = isEmpty(form.type) || !TYPE.includes(form.type)
                break
            case 'allergene':
                errors.allergene = isEmpty(form.allergene) || !ALLERGENE.includes(form.allergene)
                break
        }
    }

    const validate = () => {
        validateField('name')
        validateField('portionPrice')
        validateField('kgPrice')
        validateField('type')
        validateField('allergene')

        return !Object.values(errors).some(Boolean)
    }

    const resetForm = () => {
        form.name = ''
        form.portionPrice = null
        form.kgPrice = null
        form.type = ''
        form.allergene = ''

        Object.keys(errors).forEach(key =>{
            errors[key] = ''
        })

        submitted.value = false
    }

    const handleSubmit = () => {
        submitted.value = true
        if (!validate()) return
        

        alert('Valid Form!')
        alert(JSON.stringify(form, null, 2))
        resetForm()
    }
</script>

<template>
    <form class="parent-view fsf" @submit.prevent="handleSubmit">
        <div class="fsf" id="addItem">
            <div><p>Name: </p> <input v-model="form.name" @blur="validateField('name')" 
                :class="{invalid: (submitted && errors.name) || errors.name}"
                :placeholder="errors.name"/>
            </div>
            <div><p>Portion Price: </p> <input type="number" min="0" step="0.01" v-model.number="form.portionPrice" @blur="validateField('portionPrice')" 
                :class="{invalid: (submitted && errors.portionPrice) || errors.portionPrice}"
                :placeholder="errors.portionPrice"/>
            </div>
            <div><p>Kg Price: </p> <input type="number" min="0" step="0.01" v-model.number="form.kgPrice" @blur="validateField('kgPrice')" 
                :class="{invalid: (submitted && errors.kgPrice) || errors.kgPrice}"
                :placeholder="errors.kgPrice"/>
            </div>
            <div><p>Type: </p>
                <select v-model="form.type" @blur="validateField('type')" 
                :class="{invalid: (submitted && errors.type) || errors.type }">
                    <option v-for="t in TYPE" :key="t" :value="t">
                        {{ t }}
                    </option>
                </select>
            </div>
            <div><p>Allergene: </p>
                <select v-model="form.allergene" @blur="validateField('allergene')" 
                :class="{invalid: (submitted && errors.allergene) || errors.allergene}">
                    <option v-for="a in ALLERGENE" :key="a" :value="a">
                        {{ a }}
                    </option>
                </select>
            </div>
        </div>
        <div class="footer-buttons">
            <ButtonsFooter 
            :show-save="true"
            :show-modify="false"
            :show-remove="false"
            />
        </div>
    </form>
</template>

<style scoped>
    #addItem{
        flex-direction: column;
        gap: 5%;
        font-size: 1.2rem;
        flex: 9;
    }
    p{
        margin-bottom: 1%;
    }
</style>