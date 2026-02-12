import { ref, watch } from 'vue';
import { api } from '@/api/apiService';

// MODIFY
export function useModify({API_BASE, form, showAlert, reset, displayName}){
    const modify = async () => {
        try {
        await api.put(`${API_BASE}/${form.id}`, form);
        const updateName = form.name;
        showAlert({
          title: 'Success!',
          message: 'Item successfully modified! ✅',
          type: 'success'
        })
        if (displayName) displayName.value = updateName;
        if (reset) reset();
        
      } catch (e) {
        showAlert({
          title: 'Error',
          message: 'Upload Failed! ❌',
          type: 'error',
          options: ['Close']
        })
      }
    };
  return { modify };
}

// REMOVE
export function useRemove({API_BASE, form, showAlert, reset, onSuccess}){
   
    const remove = async () => {
        if (!form.id) return;

        const confirmDelete = await showAlert({
                title: 'Remove Item',
                message: 'Are you sure you want to permanently remove this item?',
                type: 'warning',
                options: ['Quit', 'Confirm']
            });
        if (confirmDelete !== 'Quit'){
            
            await api.delete(`${API_BASE}/${form.id}`);

            showAlert({
                title: 'Success!',
                message: 'Item succesfully deleted! ✅',
                type: 'success'
            });

            reset();
            if(onSuccess) await onSuccess();
        }
    };
    return { remove };
}

// ADD
export function useAdd ({API_BASE, form, showAlert, reset, existingNames}){
  const add = async () => {
  try {
      await api.post(API_BASE, form);
      const newExistingName = form.name;
      showAlert({
          title: 'Success!',
          message: 'Item saved succesfully! ✅',
          type: 'success'
      });
      if(existingNames) existingNames.value.push(newExistingName);
      if(reset)reset();
    } catch (e) {
      showAlert({
          title: 'Error',
          message: 'Upload Failed! ❌',
          type: 'error',
          options: ['Close']
        })
    }
  }
  return { add };
}

// SEARCH
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

    const resetFilters = () => {
        search.value = '';
        selectedType.value = '';
        selectedIngredient.value = '';
        searchResults.value = [];
    }

    const fetchSearchResults = async () => {

    if (!search.value && !selectedType.value && !selectedIngredient.value){ 
        searchResults.value = [];
        return;}

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
        applyFilter,
        resetFilters
    };
}