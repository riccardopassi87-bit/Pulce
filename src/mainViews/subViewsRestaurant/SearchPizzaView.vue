<script setup>
    import SearchTemplate from '@/commonViews/SearchTemplate.vue';
    import SearchPrompt from '@/commonViews/SearchPrompt.vue';
</script>

<template>
    <SearchTemplate>
        <template #left-search>
            <SearchPrompt>
                <template #filter>
                    <p>Normal</p>
                    <p>Vegetarian</p>
                    <p>Vegan</p>
                </template>
            </SearchPrompt>
        </template>
    </SearchTemplate>
</template>