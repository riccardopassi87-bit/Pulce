<script setup>
    import SearchTemplate from '@/commonViews/SearchTemplate.vue';
    import CommonResults from '@/commonViews/CommonResults.vue';
</script>

<template>
        <SearchTemplate>
            <template #prompt>
                <div class="fsf" id="pizza-search">
                    <input placeholder="search by name"/>
                        
                    <input placeholder="search by type"/>
                </div>
            </template>
            <template #result>
                <CommonResults>
                    
                </CommonResults>
            </template>
        </SearchTemplate>
</template>

<style scoped>
    #pizza-search{
        display: flex;
        flex-direction: column;
        gap: 40%;
    }
    input{
        width: 98%;
        height: 10%;
        background-color: #222;
    }
</style>