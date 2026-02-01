export const validators = {

    required: (val) =>
    val !== null &&
    val !== undefined &&
    val !== '' &&
    !(typeof val === 'string' && val.trim() === ''),
    
    number: (val) => !isNaN(parseFloat(val)) && isFinite(val),
    
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