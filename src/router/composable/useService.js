import { ref, watch } from 'vue';
import { api } from '@/api/apiService';

// SERVICE logic

// MODIFY structure
export function useModify({API_BASE, form, showAlert, reset, displayName}){
    const modify = async () => {
        try {
        // put the form informations in the db
        await api.put(`${API_BASE}/${form.id}`, form);
        const updateName = form.name;
        // shows alert if successfull
        showAlert({
          title: 'Success!',
          message: 'Item successfully modified! ✅',
          type: 'success'
        })
        // updates the displayed name in the search form only after
        // successfull update
        if (displayName) displayName.value = updateName;
        if (reset) reset();
        
      } catch (e) {
        // alert if failed update
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

// REMOVE structure
export function useRemove({API_BASE, form, showAlert, reset, onSuccess}){
   
    const remove = async () => {
        // remove item based on id
        if (!form.id) return;

        const confirmDelete = await showAlert({
            // alert structure for deletion
                title: 'Remove Item',
                message: 'Are you sure you want to permanently remove this item?',
                type: 'warning',
                options: ['Quit', 'Confirm']
            });
        if (confirmDelete !== 'Quit'){
            
            await api.delete(`${API_BASE}/${form.id}`);

            // Deletion alert if delition successfull
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

// ADD structure
export function useAdd ({API_BASE, form, showAlert, reset, existingNames}){
  const add = async () => {
  try {
    // submits given form for post
      await api.post(API_BASE, form);
      const newExistingName = form.name;
      showAlert({
        //alert structure if successfull
          title: 'Success!',
          message: 'Item saved succesfully! ✅',
          type: 'success'
      });
      if(existingNames) existingNames.value.push(newExistingName);
      if(reset)reset();
    } catch (e) {
      showAlert({
        //alert structure if failed
          title: 'Error',
          message: 'Upload Failed! ❌',
          type: 'error',
          options: ['Close']
        })
    }
  }
  return { add };
}

// SEARCH structure
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

    // fetch elements from search
    const fetchSearchResults = async () => {

    if (!search.value && !selectedType.value && !selectedIngredient.value){ 
        searchResults.value = [];
        return;}

    // search by typed NAME and by given TYPE from dropdown
    const params = new URLSearchParams();
    if (search.value) params.append('name', search.value);
    if (selectedType.value) params.append('type', selectedType.value);

    // GET api logic
    try {
        const url = `${SEARCH_URL}${params.toString() ? '?' + params.toString() : ''}`;
        searchResults.value = await api.get(url);
    } catch (e) {
        console.error("Search Failed", e);
        searchResults.value = [];
    }
    };

    // filtering logic - in use in the pizza search and print selection
    // to be able to filter pizzas by ingredients
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