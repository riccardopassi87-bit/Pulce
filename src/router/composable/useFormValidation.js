import { reactive, ref } from "vue";

export const validators = {

    required: (val) =>
    val !== null &&
    val !== undefined &&
    val !== '' &&
    !(typeof val === 'string' && val.trim() === ''),
    
    number: (val) => typeof val === 'number' && !isNaN(val),
    
    oneOf: (list) => (val) => list.includes(val),

    dateIsValid: (val) => {
        if (!val) return false
        const today = new Date()
        today.setHours(0, 0, 0, 0)

        return new Date(val) >= today
    },

    priceIsValid: (otherField) => (val, form) =>
    val != null && form[otherField] != null && val >= form[otherField],

    minLength: (min) => (val) =>
    Array.isArray(val) && val.length >= min,

    unique: (listRef) => (val) => {
        const values = (listRef && typeof listRef === 'object' && 'value' in listRef) 
            ? listRef.value 
            : listRef;

        if (!val || !values) return true;
        return !values.includes(val);
    },

}

export function useFormValidation(initialState, rules, onSubmit) {
    // form state
    const form = reactive({...initialState})
    const submitted = ref(false)

    // error state
    const errors = reactive(
        Object.keys(rules).reduce((acc, key) => {
            acc[key] = ''
            return acc
        }, {})
    )

    // validate single field
    const validateField = (field) => {
        errors[field] = ''
        for(const { validator, message } of rules[field]) {
            if (!validator(form[field], form)) {
                errors[field] = message
                return false
            }
        }
        return true
    }

    // validate complete form
    const validateForm = () => {
        let valid = true
        for (const field of Object.keys(rules)) {
            if (!validateField(field)) valid = false
        }
        return valid
    }

    // reset errors
    const resetErrors = () => {
            Object.keys(errors).forEach(k => (errors[k] = ''))
        }

    // reset form
    const resetForm = () => {
        Object.assign(form, initialState)
        resetErrors()
        submitted.value = false
    }

    // handle submit
    const submit = async () => {
        submitted.value = true
        if (!validateForm()) return

        await onSubmit(form)
        resetForm()
    }
    return { form, errors, submitted,
        validateField, validateForm, resetErrors, resetForm, submit }
}