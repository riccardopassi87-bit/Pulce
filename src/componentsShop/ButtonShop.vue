<script setup>
    import { commonRouter } from '../router/composable/commonRouter';
    import { apiSettings } from '@/api/apiService';

    const togglePerformance = () =>{
        apiSettings.performanceMode = !apiSettings.performanceMode;
    }

    const { goNext, getButtonClass } = commonRouter();
</script>

<template>
    <button type="button" id="notification" @click="goNext('notification')" :class="getButtonClass('notification')">Notification & Reminders</button>
    
    <!--EasterEgg 
    <button type="button" @click="togglePerformance">Improve Performance</button>
    -->
</template>
