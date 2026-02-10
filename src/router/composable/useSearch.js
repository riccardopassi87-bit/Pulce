import { ref, watch } from 'vue';
import { api } from '@/api/apiService';

export function useSearch(SEARCH_URL) {

    const search = ref('');
    const selectedType = ref('');
    const selectedIngredient = ref('');
    const searchResults = ref([]);

    const filterMap = {
        search,
        selectedType,
        selectedIngredient
    };

    const fetchSearchResults = async () => {

    if (!search.value && !selectedType.value && !selectedIngredient.value) return;

    const params = new URLSearchParams();
    if (search.value) params.append('name', search.value);
    if (selectedType.value) params.append('type', selectedType.value);

    try {
        const url = `${SEARCH_URL}${params.toString() ? '?' + params.toString() : ''}`;
        searchResults.value = await api.get(url);
    } catch (e) {
        console.error("Search Failed", e);
        searchResults.value = [];
    }
    };

    const applyFilter = async (key, value, peerKeys = []) => {
        const targetRef = filterMap[key];
        if (!targetRef) return;

        targetRef.value = value;

        if (!value) {
            const anyPeerActive = peerKeys.some(k => filterMap[k]?.value);
            if (!search.value && !anyPeerActive) {
                searchResults.value = [];
            } else {
                await fetchSearchResults();
            }
        } else {
            peerKeys.forEach(k => {
                if (filterMap[k]) filterMap[k].value = "";
            });
            await fetchSearchResults();
        }
    };

    watch([search, selectedType, selectedIngredient], fetchSearchResults);

    return {
        search,
        selectedType,
        selectedIngredient,
        searchResults,
        fetchSearchResults,
        applyFilter
    };
}