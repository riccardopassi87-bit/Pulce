<template>
    <div class="fsf" id="search">
        <div id="search-prompt" class="common">
            <slot name="prompt"></slot>
        </div>
        <div id="search-result" class="common">
            <slot name="result"></slot>
        </div>
    </div>
</template>

<style scoped>
    #search{
        gap: 3%;
    }
    .common{
        height: 100%;
        background-color: #333;
        border-radius: 5px;
        flex: 1;
        padding: 0.5%;
    }
</style>