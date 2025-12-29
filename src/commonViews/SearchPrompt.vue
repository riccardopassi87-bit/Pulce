<template>
    <div class="fsf" id="search-container">
        <input placeholder="search by name"/>

        <p>- Filter by -</p>
        
        <div id="own-filter">
            <slot name="filter"></slot>
        </div>
        
        <div id="result-display">
        
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
    }
    p{
        flex: 0.5;
        margin-top: 2%;
        display: flex;
        align-items: end;
    }
    #own-filter{
        flex: 1;
        width: 98%;
        font-size: 1rem;
        display: flex;
        justify-content: space-evenly;
        align-items: center;
    }
    #search-container input{
        width: 98%;
        flex: 1;
        background-color: #222;
    }
    #result-display{
        width: 100%;
        flex: 5;
        border-radius: 5px;
        background-color: #222;
    }
</style>