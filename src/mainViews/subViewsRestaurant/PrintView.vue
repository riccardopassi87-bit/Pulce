<script setup>
    import SearchTemplate from '@/commonViews/SearchTemplate.vue';
</script>

<template>
    <SearchTemplate>
        <template #left-search>
            <div class="fsf" id="left-sort">
                <p> - Filter by - </p>
                <div id="sort-filter">
                    <p>All</p>
                    <p>Normal</p>
                    <p>Vegetarian</p>
                    <p>Vegan</p>
                </div>
                <div class="fsf" id="sort-select">

                </div>
            </div>
        </template>
    </SearchTemplate>
</template>

<style scoped>
    #left-sort{
        flex-direction: column;
        padding: 1%;
    }
    #left-sort > p{
        flex: 1;
        font-size: 1.5rem;
        text-align: center;
        margin-top: 2%;
    }
    #sort-filter{
        flex: 1;
        display: flex;
        justify-content: space-evenly;
    }
    #sort-select{
        flex: 8;
        border-radius: 5px;
        background-color: #222;
    }
</style>