import { ref } from "vue";

const activeAlert = ref(null);

export function useAlert() {
    /** 
    *@param {Object} config - { title, message, items, options, type, safetyCode }
    */
    const showAlert = (config) => {
        return new Promise((resolve) => {
            activeAlert.value = {
                title: config.title || 'Notification',
                message: config.message || '',
                items: config.items || [],
                options: config.options || [],
                type: config.type || 'info',
                safetyCode: config.safetyCode || null,
                resolve
            };

            if (config.type === 'success' && (!config.options || config.options.length === 0)) {
                setTimeout(() => {
                    closeAlert(null);
                }, 1500);
            }
        });
    };

    const closeAlert = (value) => {
        if(activeAlert.value?.resolve){
            activeAlert.value.resolve(value);
        }
        activeAlert.value = null;
    };
    return {activeAlert, showAlert, closeAlert};
}