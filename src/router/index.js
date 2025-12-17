import { createRouter, createWebHistory } from 'vue-router';
import SelectionView from '../views/SelectionView.vue';
import ConfigureView from '../views/ConfigureView.vue';

const routes = [
    { path: '/', component: SelectionView },
    { path: '/configure', component: ConfigureView }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router;