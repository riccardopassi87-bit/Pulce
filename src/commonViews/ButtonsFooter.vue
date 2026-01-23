<script setup>

const emit = defineEmits(['save', 'modify', 'remove'])

        defineProps({
                showSave: Boolean,
                showModify: Boolean,
                showRemove: Boolean
        })
</script>

<template>
        <div class="fsf" id="footer">
                <button type="submit" v-if="showSave" id="save">Save</button>
                <button v-if="showRemove" @click="emit('remove')">Remove</button>
                <button v-if="showModify" @click="emit('modify')">Modify</button>
        </div>
</template>

<style scoped>
        #footer{
                display: flex;
                justify-content: space-between;
                align-items: end;
        }
        button{
                height: 70%;
                width: 30%;
                border-radius: 5px;
        }
</style>