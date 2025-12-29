<script setup>
    import SearchTemplate from '@/commonViews/SearchTemplate.vue';
    import SearchPrompt from '@/commonViews/SearchPrompt.vue';
</script>

<template>
    <SearchTemplate>
        <template #left-search>
            <SearchPrompt>
                <template #filter>
                    <p>Alpe</p>
                    <p>Alpenzu</p>
                    <p>Bisquits</p>
                    <p>Ragus</p>
                    <p>Snacks</p>
                    <p>Pasta</p>
                    <p>Drinks</p>
                </template>
            </SearchPrompt>
        </template>    
    </SearchTemplate>
</template>