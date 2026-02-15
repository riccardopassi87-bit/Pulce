<script setup>
import { computed } from 'vue';
import draggable from 'vuedraggable';

const props = defineProps({
    selectedPizzas: Array
});

const emit = defineEmits(['update:selectedPizzas', 'remove-pizza']);

const list = computed({
    get: () => props. selectedPizzas,
    set: (value) => emit('update:selectedPizzas', value)
});

const handleRemove = (id) => {
    emit('remove-pizza', id);
};

const getAllergeneCodes = (ingredients) => {
    if (!ingredients || !Array.isArray(ingredients)) return '';

    const uniqueCodes = [...new Set(
        ingredients
        .map(ing => ing.allergene)
        .filter(code => code && code !== 'N/A')
    )].sort();

    return uniqueCodes.length > 0 ? uniqueCodes.join(', ') : '';
}

</script>

<template>
    <div class="print-container">
        <draggable v-model="list" item-key="id" tag="div"
            class="drag-area" ghost-class="ghost">
            <template #item="{ element: p }">
                <div class="pizza-row" @click="handleRemove(p.id)">
                    <div id="top-layer">
                        <p id="name">{{ p.name }}</p>
                        <p v-if="p.type !== 'Normal'" id="type">{{ p.type }}</p>
                        <div id="spacing"></div>
                        <p id="allergene-codes">{{ getAllergeneCodes(p.ingredients) }}</p>
                        <p id="price">{{p.sellingPrice}} €</p>
                    </div>

                    <div id="low-layer">
                        <ul>
                            <li class="ingredient" v-for="ing in p.ingredients" :key="ing.id">
                                {{ ing.name }}
                            </li>
                        </ul>
                    </div>
                </div>
            </template>
        </draggable>  
    </div>      
</template>

<style scoped>
@font-face {
  font-family: 'OpenSansBold';
  src: url('../../assets/fonts/OpenSans-Bold.ttf') format('truetype');
  font-weight: normal;
  font-style: normal;
}
@font-face {
  font-family: 'OpenSansLightItalic';
  src: url('../../assets/fonts/OpenSans-LightItalic.ttf') format('truetype');
  font-weight: normal;
  font-style: normal;
}
.print-container {
  width: 100%;
  height: 100%;
  background: transparent;
}
#top-layer{
    display: flex;
    height: auto;
    gap: 5px;
    justify-content: space-between;
    align-items: baseline;
}
#low-layer{
    width: 85%;
    height: auto;
}
#low-layer ul{
    width: 100%;
    height: 100%;
    display: flex;
    flex-wrap: wrap;
    gap: 5px;
}
#name{
    font-family: 'OpenSansBold';
    font-size: 15px;
    color: black;
}
#type{
    font-family: 'OpenSansBold';
    font-size: 11px;
    color: #55d400ff;
}
#spacing{
    flex-grow: 1;
    height: 1px;
    position: relative;
    background-image: linear-gradient(to right, black 33%, rgba(255, 255, 255, 0) 0%);
    background-position: bottom;
    background-size: 3px 1px;
    background-repeat: repeat-x;
}
#allergene-codes{
    font-family: 'OpenSansBold';
    font-size: 11px;
    color: black;
}
#price{
    font-family: 'OpenSansBold';
    font-size: 15px;
    color: #ffcc00ff;
}
.ingredient{
    font-family: 'OpenSansLightItalic';
    font-size: 11px;
    color: black;
}
.ingredient:not(:last-child)::after{
    content: ", ";
}
.ghost{
    opacity: 0.5;
    border: 1px solid red;
}
.pizza-row{
    cursor: grab;
}
.pizza-row:active{
    cursor: grabbing;
}
.pizza-row:hover{
    background-color: rgb(153 153 153 / 0.2);
}
</style>