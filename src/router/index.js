import { createRouter, createWebHistory } from 'vue-router';
import StartingView from '@/views/StartingView.vue';
import ShopView from '@/views/ShopView.vue';

const routes = [
    { path: '/', component: StartingView },
    { path: '/configure', component: ShopView }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router;