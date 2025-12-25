<script setup>
    import { commonRouter } from '../router/composable/commonRouter';
    const { goNext } = commonRouter();
</script>

<template>
    <button type="button" id="notification" @click="goNext('notification')">Notification Settings</button>
</template>
