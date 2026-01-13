<script setup>
    import SearchPrompt from '@/commonViews/SearchPrompt.vue';
    import SearchTemplate from '@/commonViews/SearchTemplate.vue';
    import ButtonsFooter from '@/commonViews/ButtonsFooter.vue';

    const TYPE = ['Veggie', 'Cheese', 'Meat', 'Others']
</script>

<template>
    <div class="parent-view fsf">
        <div id="search-ingredient" class="fsf">
            <SearchTemplate>
                <template #left-search>
                    <SearchPrompt>
                        <template #filter>
                            <select>
                                <option disabled.value=""></option>
                                <option v-for="t in TYPE" :key="t" value="t">
                                    {{ t }}
                                </option>
                            </select>
                        </template>
                    </SearchPrompt>
                </template>
            </SearchTemplate>
        </div>
        <div class="footer-buttons">
            <ButtonsFooter 
            :show-save="true"
            :show-modify="true"
            :show-remove="true"/>
        </div>
    </div>
</template>

<style scoped>
    #search-ingredient{
        flex: 9;
    }
    select{
        width: 70%;
        background-color: #222;
    }
</style>