<template>
    <div class="fsf" id="search-container">
        <input placeholder="search by name"/>

        <p>- Filter -</p>
        
        <div id="filter">
            <button>Veggies</button>
            <button>Cheese</button>
            <button>Meat</button>
            <button>Other</button>
        </div>

        <div id="result-display">
        
        </div>
    
    </div>
</template>

<style scoped>

    #search-container{
        height: 87%;
        border-radius: 5px;
        flex-direction: column;
        align-items: center;
        padding: 1%;
        font-size: 1.2rem;
        background-color: #333;
    }
    p{
        flex: 1;
        display: flex;
        align-items: end;
    }
    #filter{
        flex: 1;
        width: 98%;
        display: flex;
        justify-content: space-evenly;
    }
    #filter button{
        all: unset;
        height: 100%;
        font-size: 1rem;
    }
    #filter button:hover{
        all: unset;
        font-size: 1rem;
        cursor: pointer;
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