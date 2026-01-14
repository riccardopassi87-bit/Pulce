<script setup>
    import SearchTemplate from '@/commonViews/SearchTemplate.vue';
    import SearchPrompt from '@/commonViews/SearchPrompt.vue';
    import ButtonsFooter from '@/commonViews/ButtonsFooter.vue';

    const TYPE = ['All', 'Normal', 'Vegetarian', 'Vegan']
</script>

<template>
    <div class="parent-view fsf">
        <div id="search-item" class="fsf">
            <SearchTemplate>
                <template #left-search>
                    <SearchPrompt>
                        <template #filter>
                            <select>
                                <option disabled.value=""></option>
                                <option v-for="t in TYPE" :key="t" :value="t">
                                    {{ t }}
                                </option>
                            </select>
                        </template>
                    </SearchPrompt>
                </template>    
            </SearchTemplate>
        </div>
        <div class="footer-buttons">
            <button>
                <p>&#9888; PRINT</p>
                <p>-</p>
                <p>Keeping in mind that wasting paper is not good</p>
                <p>-</p>
                <p>PRINT &#9888;</p>
            </button>
        </div>
    </div>
</template>

<style scoped>
    #search-item{
        flex: 9;
    }
    select{
        height: 70%;
        width: 70%;
        background-color: #222;
    }
    button{
        height: 70%;
        border-radius: 5px;
        display: flex;
        justify-content: space-evenly;
        align-items: center;
    }
    .footer-buttons{
        display: flex;
        align-items: end;
    }
</style>

