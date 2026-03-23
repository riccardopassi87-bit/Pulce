// Import validation logic
import { validators } from "./validators";
// Import Arrays with types given in drop down selects
// Used here to validate "oneOf" type (avoid injections)
import { PRODUCT_TYPE, PIZZA_TYPE, INGREDIENT_TYPE, ALLERGENE_CODES } from "./types";

// RULE SETS

// Ingredient validations
// Create an array of already existing names for uniqueness check
export const ingredientRules = (existingNamesArray) => ({
    // initial form state
    initialState: {name: '', portionPrice: null, kgPrice: null, type: '', allergene: ''},
    rules: {
        // name is REQUIRE and must be UNIQUE
        name: [{ validator: validators.required, message: 'Name is required' },
               { validator: (val, form, orig) => validators.unique(existingNamesArray)(val, form, orig),
                message: 'Name already taken!'}],
        // portionPrice is REQUIRED and must be a NUMBER
        portionPrice: [
            { validator: validators.required, message: 'Required' },
            { validator: validators.number, message: 'Must be a number' }],
        // kgPrice is REQUIRED and must be a NUMBER
        kgPrice: [
            { validator: validators.required, message: 'Required' },
            { validator: validators.number, message: 'Must be a number' },
            // COMPARES kgPrice to portionPrice
            { validator: validators.priceIsValid('portionPrice'), message: 'Kg price lower than portion price??'}],
        type: [
        // type is REQUIRED and must exist in the INGREDIENT_TYPE array
            { validator: validators.required, message: 'Type required' },
            { validator: validators.oneOf(INGREDIENT_TYPE), message: 'Invalid type' }],
        // allergene is REQUIRED and must exist in the ALLERGENE_CODES array
        allergene: [
            { validator: validators.required, message: 'Allergene required' },
            { validator: validators.oneOf(ALLERGENE_CODES), message: 'Invalid allergene' }]
    }
});

// Products validations
// Create an array of already existing names for uniqueness check
export const productRules = (existingNamesArray) => ({
    // initial form state
    initialState: {name: '', originalPrice: null, sellingPrice: null, type: '', expirationDate: '', amount: null},
    rules: {
        // name is REQUIRE and must be UNIQUE
        name: [{ validator: validators.required, message: 'Name is required'},
               { validator: (val, form, orig) => validators.unique(existingNamesArray)(val, form, orig),
                message: 'Name already taken!'}],
        // originalPrice is REQUIRED and must be a NUMBER
        originalPrice: [
            { validator: validators.required, message: 'Required'},
            { validator: validators.number, message: 'Must be a number'}],
        // sellinglPrice is REQUIRED and must be a NUMBER
        sellingPrice: [
            { validator: validators.required, message: 'Required'},
            { validator: validators.number, message: 'Must be a number'},
            // COMPARES sellingPrice to originalPrice
            { validator: validators.priceIsValid('originalPrice'), message:'We are loosing money here...'}],
        // type is REQUIRED and must exist in the PRODUCT_TYPE array
        type: [
            { validator: validators.required, message: 'Type required'},
            { validator: validators.oneOf(PRODUCT_TYPE), message: 'Invalid type'}],
        // expirationDate is REQUIRED and must be a VALIDA DATE
        expirationDate: [
            { validator: validators.required, message: 'Expiration date required'},
            // { validator: validators.dateIsValid, message: 'What year are we in?'}
            ],
        // amount is REQUIRED and must be a NUMBER
        amount: [
            { validator: validators.required, message: 'Amount required'},
            { validator: validators.number, message: 'Must be a number'}
        ]
    }
});

// Pizzas validations
// Create an array of already existing names for uniqueness check
export const pizzaRules = (existingNamesArray) => ({
    // initial form state
    initialState: {name: '', sellingPrice: null, productionPrice: null, type: '', ingredientIds: []},
    rules: {
        // name is REQUIRE and must be UNIQUE
        name: [{ validator: validators.required, message: 'Name is required'},
               { validator: (val, form, orig) => validators.unique(existingNamesArray)(val, form, orig),
                message: 'Name already taken!'}],
        // sellinglPrice is REQUIRED and must be a NUMBER
        sellingPrice: [
            { validator: validators.required, message: 'Required'},
            { validator: validators.number, message: 'Must be a number'},
            // COMPARES sellingPrice to productionPrice
            { validator: validators.priceIsValid('productionPrice'), message: 'Impossible' }],
        // productionPrice is REQUIRED and must be a NUMBER
        productionPrice: [
            { validator: validators.required, message: 'Required'},
            { validator: validators.number, message: 'Must be a number'}
            ],
        // type is REQUIRED and must exist in the PIZZA_TYPE array
        type: [
            { validator: validators.required, message: 'Type is required'},
            { validator: validators.oneOf(PIZZA_TYPE), message: 'Type is not valid'}],
        // ingredient ids is a list of ingredients contained in a pizza
        // it must include at least 1 ingredient id
        ingredientIds: [
            { validator: validators.minLength(1), message: 'Select at least 1 ingredient'}
        ]
    }
})