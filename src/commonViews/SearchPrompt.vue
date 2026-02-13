<template>
    <div class="fsf" id="search-container">

        <div id="header">
            <slot name="section-header"></slot>
        </div>

        <div id="input-filter">
            <slot name="input"></slot>
        </div>

        <div id="own-filter">
            <slot name="filter"></slot>
        </div>
        
        <div id="result-display">
            <slot name="results"></slot>
        </div>
    </div>
</template>

<style scoped>

    #search-container{
        border-radius: 5px;
        flex-direction: column;
        align-items: center;
        padding: 1%;
        font-size: 1.2rem;
        background-color: #333;
        flex: 1;
        min-height: 0;
    }
    #header{
        flex: 0.7;
        display: flex;
        justify-content: center;
        align-items: center;
        width: 100%;
    }
    #own-filter{
        flex: 1;
        width: 100%;
        font-size: 1rem;
        display: flex;
        justify-content: space-evenly;
        align-items: center;
        gap: 2%;
    }
    #input-filter{
        width: 100%;
        flex: 0.7;
        display: flex;
    }
    #result-display{
        width: 100%;
        flex: 3;
        min-height: 0;
        border-radius: 5px;
        background-color: #111;
        padding: 1%;
        overflow-y: auto;
    }
</style>