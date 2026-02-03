import { onMounted, reactive, ref, toRaw } from "vue";
import { api, nameLoader } from "@/api/apiService";

export function useForm({ initialState, rules, API_BASE, SEARCH_URL, onSubmit, existingNamesRef }) {

    // Form - Validation Satet
    const form = reactive({...initialState});
    const submitted = ref(false);
    const existingNames = existingNamesRef || ref([]);
    const errors = reactive(
        Object.keys(rules).reduce((acc, key) => ({...acc, [key]: ''}), {})
    );

    // Search - List Management State
    const search = ref('');
    const selectedType = ref('');
    const searchResults = ref([]);

    // ExistingName Validation
    onMounted(() => {
        if (API_BASE) nameLoader(existingNames, API_BASE)
    });

    // Validation Logic
    const validateField = (field) => {
        errors[field] = '';
        if (!rules[field]) return true;
        for(const { validator, message } of rules[field]) {
            if(!validator(form[field], form)) {
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

    // Search Logic
    const fetchSearchResults = async () => {
        if(!search.value && !selectedType.value) {
            searchResults.value = [];
            return;
        }
        if(!SEARCH_URL) return;

        const params = new URLSearchParams();
        if(search.value) params.append('name', search.value);
        if(selectedType.value) params.append('type', selectedType.value);

        try{
            searchResults.value = await api.get(`${SEARCH_URL}?${params.toString()}`);
        } catch (e) {
            console.error("Search failed", e);
        }
    };

    // Submission
    const submit = async () => {
        submitted.value = true;
        if(!validateForm()) return;
        if(onSubmit) await onSubmit(toRaw(form));
    };

    const reset = () => {
        Object.assign(form, initialState);
        Object.keys(errors).forEach(k => errors[k] = '');
        submitted.value = false;
        search. value = '';
        selectedType.value = '';
        searchResults.value = [];
        displayName.value = '';
    };

    // Modify
    const selectItem = (item, customMapping) => {
        if (customMapping) {
            Object.assign(form, customMapping(item));
        } else {
            Object.assign(form, toRaw(item));
        }
        submitted.value = false;
        
        Object.keys(errors).forEach(k => errors[k] = '');
    };

    // Delete
    const remove = async (id) => {
        if (!id || !confirm('Are you sure you want to remove this item?')) return;
        try {
            await api.delete(`${API_BASE}/${id}`);
            alert('Deleted successfully ✅');
            reset();
            await fetchSearchResults();
        } catch (e) {
            alert(e.message);
        }
    };

    // Item Name
    const displayName = ref('')

    const handleSelect = (item, customMapping) => {
        selectItem(item, customMapping);
        displayName.value = item.name;
    }

    return{ form, errors, submitted, existingNames, search, selectedType, searchResults, displayName,
            validateField, submit, reset, fetchSearchResults, selectItem, remove, handleSelect
    };
}