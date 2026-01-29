import { useFormValidation } from "./useFormValidation";
import { apiService } from "@/api/apiService";

export function useFormSubmit(config) {
    const { initialState, rules, endPoint, successMessage, onRefresh } = config;

    return useFormValidation(
        initialState,
        rules,
        async (data) => {
            try {
                await apiService(endPoint, data);
                alert(`${successMessage} ✅`)
                if (onRefresh) onRefresh();
            } catch(e) {
                alert(`Failed to save ❌`);
                console.error(e);
            }
        }
    )
}