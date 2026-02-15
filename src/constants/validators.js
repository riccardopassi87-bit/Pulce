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

    unique: (listRef) => (val, form, originalName) => {
        const values = (listRef && typeof listRef === 'object' && 'value' in listRef) 
            ? listRef.value 
            : listRef;

            console.log({
  typing: val,
  comparingTo: originalName,
  isMatch: val === originalName,
  listSize: values.length
});

        if (!val || !values) return true;

        const searchVal = val.trim().toLowerCase();
        if (originalName && searchVal === originalName.toLowerCase().trim()) {
            return true;
        }

        return !values.some(existingName => existingName.toLowerCase() === searchVal);
    },

}