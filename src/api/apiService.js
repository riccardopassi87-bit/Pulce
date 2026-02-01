const headers = { 'Content-Type': 'application/json' }

export const api = {
    async get(url) {
        const res = await fetch(url);
        if (!res.ok) throw new Error(`Fetch failed: ${res.status}`);
        return res.json();
    },

    async post(url, data) {
        const res = await fetch(url, {
            method: 'POST',
            headers,
            body: JSON.stringify(data)
        });
        if(!res.ok) throw new Error(`Save failed: ${res.status}`);
        return res.json();
    },

    async put(url, data) {
        const res = await fetch(url, {
            method: 'PUT',
            headers,
            body: JSON.stringify(data)
        });
        if(!res.ok) throw new Error(`Update failed: ${res.status}`);
        return res.json();
    },
    
    async delete(url) {
        const res = await fetch(url, { method: 'DELETE'});
        if(!res.ok) {
            const errorData = await res.json().catch(() => ({}));
            throw new Error(errorData.message || `Delete failed: ${res.status}`);
        }
        return res.status === 204 ? true : res.json();
    }
};

export async function nameLoader(refToUpdate, url) {
    try {
        const data = await api.get(url);
        refToUpdate.value = data.map(item => item.name);
    } catch (e) {
        console.error("NameLoader Error:", e.message);
    }
}