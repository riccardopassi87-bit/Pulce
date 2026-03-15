<script setup>
    import { commonRouter } from '../router/composable/commonRouter';
    import { apiSettings } from '@/api/apiService';

    const togglePerformance = () =>{
        apiSettings.performanceMode = !apiSettings.performanceMode;
    }

    const { goNext, getButtonClass } = commonRouter();
</script>

<template>
    <button type="button" id="notification" @click="goNext('notification')" :class="getButtonClass('notification')">Expiration Reminders</button>
    
     
    <!-- <button type="button" @click="togglePerformance">Improve Performance</button> -->
    
</template>
