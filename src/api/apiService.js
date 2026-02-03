
// EsterEgg, comment to disable!!!!
import { reactive } from "vue";
import sound from "@/assets/easterEgg.wav";

export const apiSettings = reactive({
    performanceMode: true
})

const EASTER_EGG_ACTIVE = true;
const sleep = (ms) => new Promise(resolve => setTimeout(resolve, ms));
const playSound = () => {
    const audio = new Audio(sound);
    audio.volume = 0.5;
    audio.play();
}

// END EasterEgg - Calls in APIs, Expands from -App.vue- via -ButtonShop-

const headers = { 'Content-Type': 'application/json' }

export const api = {
    async get(url) {

        if(apiSettings.performanceMode) {playSound(); await sleep(3500);}

        const res = await fetch(url);
        if (!res.ok) throw new Error(`Fetch failed: ${res.status}`);
        return res.json();
    },

    async post(url, data) {
        
        if(apiSettings.performanceMode) await sleep(3500);

        const res = await fetch(url, {
            method: 'POST',
            headers,
            body: JSON.stringify(data)
        });
        if(!res.ok) throw new Error(`Save failed: ${res.status}`);
        return res.json();
    },

    async put(url, data) {
        
        if(apiSettings.performanceMode) await sleep(3500);

        const res = await fetch(url, {
            method: 'PUT',
            headers,
            body: JSON.stringify(data)
        });
        if(!res.ok) throw new Error(`Update failed: ${res.status}`);
        return res.json();
    },
    
    async delete(url) {
        
        if(apiSettings.performanceMode) await sleep(3500);

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