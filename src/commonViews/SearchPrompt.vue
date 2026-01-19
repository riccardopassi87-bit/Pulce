<template>
    <div class="fsf" id="search-container">

        <div id="input-filter">
            <slot name="input"></slot>
        </div>


        <p>- Filter by -</p>
        
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
    p{
        flex: 0.5;
        margin-top: 2%;
        display: flex;
        align-items: end;
    }
    #own-filter{
        flex: 1;
        width: 100%;
        font-size: 1rem;
        display: flex;
        justify-content: space-evenly;
        align-items: center;
    }
    #input-filter{
        width: 98%;
        flex: 1;
    }
    #result-display{
        width: 100%;
        flex: 5;
        min-height: 0;
        border-radius: 5px;
        background-color: #111;
        padding: 1%;
        overflow-y: auto;
    }
</style>