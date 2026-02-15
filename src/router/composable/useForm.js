import { onMounted, reactive, ref, toRaw } from "vue";
import { nameLoader } from "@/api/apiService";

export function useForm({ initialState, rules, API_BASE, onSubmit, existingNamesRef }) {

    // Form - Validation Satet
    const form = reactive({...initialState});
    const submitted = ref(false);
    const existingNames = existingNamesRef || ref([]);
    const displayName = ref('');
    const errors = reactive(
        Object.keys(rules).reduce((acc, key) => ({...acc, [key]: ''}), {})
    );

    // ExistingName Validation
    onMounted(() => {
        if (API_BASE) {
            nameLoader(existingNames, API_BASE);
        }
    });

    // Validation Logic
    const validateField = (field) => {
        errors[field] = '';
        if (!rules[field]) return true;
        for(const { validator, message } of rules[field]) {
            if(!validator(form[field], form, originalName.value)) {
                errors[field] = message;
                return false;
            }
        }
        return true;
    };

    const validateForm = () => {
        let valid = true;
        for (const field in rules) {
            if (!validateField(field)) valid = false;
        }
        return valid;
    }

    // Submission
    const submit = async () => {
        submitted.value = true;
        if(!validateForm()) return;
        if(onSubmit) await onSubmit(toRaw(form));
    };

    // Reset
    const reset = () => {
        Object.assign(form, initialState);
        Object.keys(errors).forEach(k => errors[k] = '');
        submitted.value = false;
        displayName.value = '';
        originalName.value = '';
    };


    // Item Name

    const originalName = ref('');
    const handleSelect = (item, customMapping) => {
        const data = customMapping? customMapping(item) : toRaw(item);
        Object.assign(form, data);

        originalName.value = item.name || '';

        displayName.value = item.name || '';
        submitted.value = false;
        Object.keys(errors).forEach( k => errors[k] = '');
    }

    return{ form, errors, submitted, existingNames, displayName, originalName,
            validateField, submit, reset, handleSelect
    };
}