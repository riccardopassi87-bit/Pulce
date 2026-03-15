
// EsterEgg, FALSE to disable!!!!
import { reactive } from "vue";
import sound from "@/assets/easterEgg.wav";

export const apiSettings = reactive({
    performanceMode: false
})

const EASTER_EGG_ACTIVE = true;
const sleep = (ms) => new Promise(resolve => setTimeout(resolve, ms));
const playSound = () => {
    const audio = new Audio(sound);
    audio.volume = 0.5;
    audio.play();
}
// END EasterEgg - Calls in APIs, Expands from -App.vue- via -ButtonShop-

// API generic calls
const headers = { 'Content-Type': 'application/json' }

export const api = {
    // API GET
    async get(url) {

        // Easter egg - IGNORE ---------------------------------------- //
        if(apiSettings.performanceMode) {playSound(); await sleep(3500);}
        // ------------------------------------------------------------ //

        const res = await fetch(url);
        if (!res.ok) throw new Error(`Fetch failed: ${res.status}`);
        return res.json();
    },
    // API POST
    async post(url, data) {
        
        // Easter egg - IGNORE ---------------------------------------- //
        if(apiSettings.performanceMode) {playSound(); await sleep(3500);}
        // ------------------------------------------------------------ //

        const res = await fetch(url, {
            method: 'POST',
            headers,
            body: JSON.stringify(data)
        });
        if(!res.ok) throw new Error(`Save failed: ${res.status}`);
        return res.json();
    },
    // API PUT
    async put(url, data) {
        
        // Easter egg - IGNORE ---------------------------------------- //
        if(apiSettings.performanceMode) {playSound(); await sleep(3500);}
        // ------------------------------------------------------------ //

        const res = await fetch(url, {
            method: 'PUT',
            headers,
            body: JSON.stringify(data)
        });
        if(!res.ok) throw new Error(`Update failed: ${res.status}`);
        return res.json();
    },
    // API DELETE
    async delete(url) {
        
        // Easter egg - IGNORE ---------------------------------------- //
        if(apiSettings.performanceMode) {playSound(); await sleep(3500);}
        // ------------------------------------------------------------ //

        const res = await fetch(url, { method: 'DELETE'});
        if(!res.ok) {
            let errorMessage = `Delete failed: ${res.status}`;
            try{
                const errorData = await res.json();
                errorMessage = errorData.message || errorMessage;
            } catch (e){ }
             throw new Error(errorMessage);  
        }
        const text = await res.text();
        return text ? JSON.parse(text) : true;
    }
};
// Name loader checks if the given article/item NAME is already present in the DB
export async function nameLoader(refToUpdate, url) {
    try {
        const data = await api.get(url);
        refToUpdate.value = data.map(item => item.name);
    } catch (e) {
        console.error("NameLoader Error:", e.message);
    }
};