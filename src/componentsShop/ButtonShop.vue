<script setup>
    import { commonRouter } from '../router/composable/commonRouter';
    const { goNext, getButtonClass } = commonRouter();
</script>

<template>
    <button type="button" id="notification" @click="goNext('notification')" :class="getButtonClass('notification')">Notification & Reminders</button>
</template>
