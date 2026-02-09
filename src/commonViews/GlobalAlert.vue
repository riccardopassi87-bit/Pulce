<script setup>
import { ref, watch } from 'vue';
import { useAlert } from '@/router/composable/useAlert';

const { activeAlert, closeAlert} = useAlert();
const safetyInput = ref('');

watch(activeAlert, () =>{ safetyInput.value = ''; });

const handleAction = (value) => {
    closeAlert(value);
};
</script>

<template>
    <Transition name="fade">
        <div v-if="activeAlert" class="modal-overlay">
            <div class="modal-card" :class="activeAlert.type">
                <h3>{{ activeAlert.title }}</h3>
                <p>{{ activeAlert.message }}</p>

                <ul v-if="activeAlert.items.length" class="alert-list">
                    <li v-for="item in activeAlert.items" :key="item">
                        • {{ item }}
                    </li>
                </ul>

                <div v-if="activeAlert.type === 'safety'" class="safety-zone">
                    <input v-model="safetyInput"
                    :placeholder="`Type ${activeAlert.safetyCode} to confirm`"
                    class="safety-input"/>
                </div>

                <div class="modal-actions">
                    <button v-for="btn in activeAlert.options" :key="btn"
                    :disabled="activeAlert.type === 'safety' && btn === 'Delete' && safetyInput !== activeAlert.safetyCode"
                    @click="handleAction(btn)">{{ btn }}</button>
                </div>
            </div>
        </div>
    </Transition>

</template>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0,0,0,0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 9999;
}
.modal-card {
  display: flex;
  flex-direction: column;
  background: #333;
  height: 50%;
  max-height: 200px;
  padding: 20px;
  max-width: 400px;
  width: 50%;
  box-shadow: 0 10px 25px rgba(0,0,0,0.2);
}
h3{
    flex: 1;
    width: 100%;
    text-align: center;
    color: rgb(187, 150, 80);
}
p{
    flex:0.5;
    width: 100%;
    font-size: larger;
    text-align: center;
}
.alert-list { list-style: none;
    flex: 2;
    max-height: 200px;
    overflow-y: auto; }

.modal-actions {
    flex: 1;
    display: flex;
    gap: 10px;
    justify-content: flex-end;
    margin-top: 20px; }

.modal-actions button{
    height: 100%;
    background-color: #222;
}
.modal-actions button:hover{
    background-color: rgb(39, 10, 10);
}
.safety-input { width: 100%; padding: 8px; border: 1px solid red; margin-top: 10px; }
.fade-enter-active, .fade-leave-active { transition: opacity 0.3s; }
.fade-enter-from, .fade-leave-to { opacity: 0; }
</style>