import { validators } from "@/router/composable/useFormValidation";
import { PRODUCT_TYPE, PIZZA_TYPE, INGREDIENT_TYPE, ALLERGENE } from "./types";

export const ingredientRules = (existingIngredientArray) => ({
    initialState: {name: '', portionPrice: null, kgPrice: null, type: '', allergene: ''},
    rules: {
        name: [{ validator: validators.required, message: 'Name is required' },
               { validator: (val) => validators.unique(existingIngredientArray)(val),
                message: 'Name already taken!'}],
        portionPrice: [
            { validator: validators.required, message: 'Required' },
            { validator: validators.number, message: 'Must be a number' }],
        kgPrice: [
            { validator: validators.required, message: 'Required' },
            { validator: validators.number, message: 'Must be a number' },
            { validator: validators.priceIsValid('portionPrice'), message: 'Kg price lower than portion price??'}],
        type: [
            { validator: validators.required, message: 'Type required' },
            { validator: validators.oneOf(INGREDIENT_TYPE), message: 'Invalid type' }],
        allergene: [
            { validator: validators.required, message: 'Allergene required' },
            { validator: validators.oneOf(ALLERGENE), message: 'Invalid allergene' }]
    }
});

export const productRules = (existingProductArray) => ({
    initialState: {name: '', originalPrice: null, sellingPrice: null, type: '', expirationDate: '', amount: null},
    rules: {
        name: [{ validator: validators.required, message: 'Name is required'},
               { validator: (val) => validators.unique(existingProductArray)(val),
                message: 'Name already taken!'}],
        originalPrice: [
            { validator: validators.required, message: 'Required'},
            { validator: validators.number, message: 'Must be a number'}],
        sellingPrice: [
            { validator: validators.required, message: 'Required'},
            { validator: validators.number, message: 'Must be a number'},
            { validator: validators.priceIsValid('originalPrice'), message:'We are loosing money here...'}],
        type: [
            { validator: validators.required, message: 'Type required'},
            { validator: validators.oneOf(PRODUCT_TYPE), message: 'Invalid type'}],
        expirationDate: [
            { validator: validators.required, message: 'Expiration date required'},
            { validator: validators.dateIsValid, message: 'What year are we in?'}],
        amount: [
            { validator: validators.required, message: 'Amount required'},
            { validator: validators.number, message: 'Must be a number'}
        ]
    }
});

export const pizzaRules = (existingPizzaArray) => ({
    initialState: {name: '', sellingPrice: null, productionPrice: null, type: '', ingredientIds: []},
    rules: {
        name: [{ validator: validators.required, message: 'Name is required'},
               { validator: (val) => validators.unique(existingPizzaArray)(val),
                message: 'Name already taken!'}],
        sellingPrice: [
            { validator: validators.required, message: 'Required'},
            { validator: validators.number, message: 'Must be a number'},
            { validator: validators.priceIsValid('productionPrice'), message: 'Impossible' }],
        productionPrice: [
            { validator: validators.required, message: 'Required'},
            { validator: validators.number, message: 'Must be a number'}
            ],
        type: [
            { validator: validators.required, message: 'Type is required'},
            { validator: validators.oneOf(PIZZA_TYPE), message: 'Type is not valid'}],
        ingredientIds: [
            { validator: validators.minLength(1), message: 'Select at least 1 ingredient'}
        ]
    }
})