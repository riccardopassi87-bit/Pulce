<script setup>
defineProps({
    label: String,
    error: String,
    submitted: Boolean
});
</script>

<template>
    <div class="field">
        <div class="pee">
            <p>{{ label }}</p>
        </div>

        <div class="pee-input">
            <slot :isInvalid="submitted && !!error"></slot>
        </div>

        <div class="pee">
            <p v-if="submitted && error" class="error">
                {{ error }}
            </p>
        </div>
    </div>
</template>