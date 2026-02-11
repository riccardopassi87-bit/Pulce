import { api } from '@/api/apiService';

export function useModify({API_BASE, form, showAlert, reset, displayName}){
    const modify = async () => {
        try {
        await api.put(`${API_BASE}/${form.id}`, form);
        showAlert({
          title: 'Success!',
          message: 'Item successfully modified ✅',
          type: 'success'
        })
        reset();
        displayName.value = form.name;
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