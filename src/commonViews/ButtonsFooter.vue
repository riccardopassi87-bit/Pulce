<script setup>
        defineProps({
                showSave: Boolean,
                showModify: Boolean,
                showRemove: Boolean
        })
</script>

<template>
        <div class="fsf" id="footer">
                <button type="submit" v-if="showSave" id="save">Save</button>
                <button v-if="showRemove" id="remove">Remove</button>
                <button v-if="showModify" id="modify">Modify</button>
        </div>
</template>

<style scoped>
        #footer{
                display: flex;
                justify-content: space-between;
                padding: 1% 3%;
        }
        button{
                height: 100%;
                width: 30%;
                border-radius: 5px;
        }
</style>