<script setup>
    import ButtonsFooter from '@/commonViews/ButtonsFooter.vue';
    import { useFormValidation, validators } from '@/router/composable/useFormValidation';

    const TYPE = ['Alpe', 'Alpenzu', 'Bisquits', 'Pesto', 'Snacks', 'Pasta', 'Drinks']
    
    const { form, errors, submitted, validateField, submit } = useFormValidation({
        name: '',
        originalPrice: null,
        sellingPrice: null,
        type: '',
        expirationDate: '',
        amount: null
    },{
        name: [{ validator: validators.required, message: 'Name is required'}],
        originalPrice: [
            { validator: validators.required, message: 'Required'},
            { validator: validators.number, message: 'Must be a number'}],
        sellingPrice: [
            { validator: validators.required, message: 'Required'},
            { validator: validators.number, message: 'Must be a number'},
            { validator: validators.priceIsValid('originalPrice'), message:'We are loosing money here...'}],
        type: [
            { validator: validators.required, message: 'Type required'},
            { validator: validators.oneOf(TYPE), message: 'Invalid type'}],
        expirationDate: [
            { validator: validators.required, message: 'Expiration date required'},
            { validator: validators.dateIsValid, message: 'What year are we in?'}],
        amount: [
            { validator: validators.required, message: 'Amount required'},
            { validator: validators.number, message: 'Must be a number'}
        ]
    },
    async (data) => {
        alert('hulla la luppa!')
    }
    )  
</script>

<template>
    <form class="parent-view fsf" @submit.prevent="submit">
        <div class="parent-view fsf">
            <div class="fsf" id="addItem">
                <div>
                    <p>Name: </p>
                    <input v-model="form.name" @blur="validateField('name')"
                    :class="{invalid: submitted && errors.name}"
                    :placeholder="errors.name"/>
                </div>
                <div>
                    <p>Original Price: </p>
                    <input type="number" min="0" step="0.01" v-model.number="form.originalPrice" @blur="validateField('originalPrice'); validateField('sellingPrice');"
                    :class="{invalid: submitted && errors.originalPrice}"
                    :placeholder="errors.originalPrice"/>
                </div>
                <div>
                    <p>Selling Price: </p>
                    <input type="number" min="0" step="0.01" v-model.number="form.sellingPrice" @blur="validateField('sellingPrice')"
                    :class="{invalid: submitted && errors.sellingPrice}"
                    :placeholder="errors.sellingPrice"/>
                </div>
                <div>
                    <p>Type: </p>
                    <select v-model="form.type" @blur="validateField('type')"
                    :class="{invalid: submitted && errors.type}">
                        <option disabled value=""></option>
                        <option v-for="t in TYPE" :key="t" :value="t">
                            {{ t }}
                        </option>
                    </select>
                </div>
                <div>
                    <p>Expiration Date: </p>
                    <input type="date" v-model="form.expirationDate" @blur="validateField('expirationDate')"
                    :class="{invalid: submitted && errors.expirationDate}"
                    :placeholder="errors.expirationDate"/>
                </div>
                <div>
                    <p>Amount: </p>
                    <input type="number" min="0" step="1" v-model.number="form.amount" @blur="validateField('amount')"
                    :class="{invalid: submitted && errors.amount}"
                    :placeholder="errors.amount"/>
                </div>
            </div>
            <div class="footer-buttons">
                <ButtonsFooter
                :show-save="true"
                :show-modify="false"
                :show-remove="false"/>
            </div>
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