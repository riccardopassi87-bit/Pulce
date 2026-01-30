import { ref, watch } from 'vue';
import { api } from '@/api/apiService';

export function useFormManagement(API_BASE, onSelectCallBack) {
    const search = ref('')
    const selectedType = ref('')
    const items = ref([])
    const selectedItem = ref(null)

    const fetchItems = async () => {
        if(!search.value && !selectedType.value){
            items.value = []
            selectedItem.value = null
            return
        }

        const params = new URLSearchParams();
        if (search.value) params.append('name', search.value);
        if (selectedType.value) params.append('type', selectedType.value);

        try{
            items.value = await api.get(`${API_BASE}/search?${params.toString()}`);

            if (selectedItem.value && !items.value.some(i => i.id === selectedItem.value.id)) {
                selectedItem.value = null;
            }
        } catch (e) {
            console.error("Search Failed", e);
        }
    };
    watch([search, selectedType], fetchItems, {immediate: true});

    const selectItem = (item) => {
        selectedItem.value = item;
        if (onSelectCallBack) onSelectCallBack(item);
    };

    const resetSelection = () => {
        selectedItem.value = null;
        items.value = [];
        search.value = '';
        selectedType.value = '';
    };

    return {
        search,
        selectedType,
        items,
        selectedItem,
        fetchItems,
        selectItem,
        resetSelection
    };
}