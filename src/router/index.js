import { createRouter, createWebHistory } from 'vue-router';
import ShopView from '@/views/ShopView.vue';
import RestaurantView from '@/views/RestaurantView.vue';

const routes = [
    { path: '/shop', component: ShopView },
    { path: '/restaurant', component: RestaurantView }

]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router;