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

// Add
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