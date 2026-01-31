import { reactive, ref } from "vue";

export function useBaseForm(ruleConfig) {
    const { initialState, rules } = ruleConfig

    const form = reactive({ ...initialState });
    const errors = reactive(
        Object.keys(rules).reduce((acc, key) => ({ ...acc, [key]: '' }), {})
    );
    const submitted = ref(false);

    const validateField = (field) => {
        errors[field] = '';
        if (!rules[field]) return true;
        for (const { validator, message } of rules[field]) {
            if(!validator(form[field], form)){
                errors[field] = message;
                return false;
            }
        }
        return true;
    };

    const validateAll = () => {
        submitted.value = true;
        let isValid = true;
        for (const field in rules) {
            if(!validateField(field)) isValid = false;
        }
        return isValid;
    };

    return { form, errors, submitted, validateField, validateAll };
}