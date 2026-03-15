export const validators = {

    // Required - cannot be null, undefined, empty string
    required: (val) =>
    val !== null &&
    val !== undefined &&
    val !== '' &&
    !(typeof val === 'string' && val.trim() === ''),
    
    // Number - numerical value check
    number: (val) => !isNaN(parseFloat(val)) && isFinite(val),
    
    // oneOf has to be included in the given array type
    oneOf: (list) => (val) => list.includes(val),

    // date has to be a valid format and be grater than today's date
    dateIsValid: (val) => {
        if (!val) return false
        const today = new Date()
        today.setHours(0, 0, 0, 0)

        return new Date(val) >= today
    },

    // price is valid compares the given value with another field's value
    // the other field's value is given in the RULE SET form
    // the minimun different has to be at least a factor of --
    priceIsValid: (otherField) => (val, form) =>
    val != null && form[otherField] != null && val > form[otherField]*1.5,

    // minimum length defines the minimum length of an array
    // in this case is used to validate the minimum amount of ingredients in one pizza
    // the minimum amount can be set as parameter in the RULE SET
    minLength: (min) => (val) =>
    Array.isArray(val) && val.length >= min,

    // Unique check if the given value is already present in the db
    unique: (listRef) => (val, form, originalName) => {
        const values = (listRef && typeof listRef === 'object' && 'value' in listRef) 
            ? listRef.value 
            : listRef;

        if (!val || !values) return true;

        const searchVal = val.trim().toLowerCase();
        if (originalName && searchVal === originalName.toLowerCase().trim()) {
            return true;
        }

        return !values.some(existingName => existingName.toLowerCase() === searchVal);
    },

}