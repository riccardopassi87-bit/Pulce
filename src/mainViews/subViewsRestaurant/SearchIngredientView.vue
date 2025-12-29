<script setup>
    import SearchPrompt from '@/commonViews/SearchPrompt.vue';
    import SearchTemplate from '@/commonViews/SearchTemplate.vue';
</script>

<template>
    <SearchTemplate>
        <template #left-search>
            <SearchPrompt>
                <template #filter>
                    <p>Veggies</p>
                    <p>Cheese</p>
                    <p>Meat</p>
                    <p>Others</p>
                </template>
            </SearchPrompt>
        </template>
    </SearchTemplate>
</template>