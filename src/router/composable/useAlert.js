import { ref } from "vue";

const activeAlert = ref(null);

export function useAlert() {

    // main alert body for custome alert style and logic
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
            // at successfull alert, automatically close the alert after a set given time
            if (config.type === 'success' && (!config.options || config.options.length === 0)) {
                setTimeout(() => {
                    closeAlert(null);
                }, 1500);
            }
        });
    };

    // alert closing logic
    const closeAlert = (value) => {
        if(activeAlert.value?.resolve){
            activeAlert.value.resolve(value);
        }
        activeAlert.value = null;
    };
    return {activeAlert, showAlert, closeAlert};
}