import { api } from '@/api/apiService';

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