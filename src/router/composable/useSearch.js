import { ref, watch } from 'vue';
import { api } from '@/api/apiService';

export function useSearch(SEARCH_URL) {
    // 1. Define refs individually so they are always in scope
    const search = ref('');
    const selectedType = ref('');
    const selectedIngredient = ref('');
    const searchResults = ref([]);

    // Create a local map so applyFilter can find them by string names
    const filterMap = {
        search,
        selectedType,
        selectedIngredient
    };

    const fetchSearchResults = async () => {
        if (!search.value && !selectedType.value && !selectedIngredient.value) {
            searchResults.value = [];
            return;
        }

        const params = new URLSearchParams();
        if (search.value) params.append('name', search.value);
        if (selectedType.value) params.append('type', selectedType.value);

        try {
            searchResults.value = await api.get(`${SEARCH_URL}?${params.toString()}`);
        } catch (e) {
            console.error("Search Failed", e);
            searchResults.value = [];
        }
    };

    const applyFilter = async (key, value, peerKeys = []) => {
        // Find the ref in our map
        const targetRef = filterMap[key];
        if (!targetRef) return;

        targetRef.value = value;

        if (!value) {
            // Check if name search or any peer filter is still active
            const anyPeerActive = peerKeys.some(k => filterMap[k]?.value);
            if (!search.value && !anyPeerActive) {
                searchResults.value = [];
            } else {
                await fetchSearchResults();
            }
        } else {
            // Clear peer filters (The "Exclusive" logic)
            peerKeys.forEach(k => {
                if (filterMap[k]) filterMap[k].value = "";
            });
            await fetchSearchResults();
        }
    };

    watch(search, fetchSearchResults);

    return {
        search,
        selectedType,
        selectedIngredient,
        searchResults,
        fetchSearchResults, // Keep this for your 'remove' function
        applyFilter
    };
}