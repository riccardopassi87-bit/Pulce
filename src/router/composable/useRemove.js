export async function standardDelete(API_BASE, form){
    const normaleDelete = await showAlert({
            title: 'Remove Item',
            message: 'Are you sure you want to permanently remove this item?',
            type: 'warning',
            options: ['Quit', 'Confirm']
        });
    if (normaleDelete !== 'Quit'){
        showAlert({
            title: 'Success!',
            message: 'Item succesfully deleted! ✅',
            type: 'success'
        })
        await api.delete(`${API_BASE}/${form.id}`);
        reset();
        await fetchSearchResults();
    }
}