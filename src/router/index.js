import { createRouter, createWebHistory } from 'vue-router';
import ShopView from '@/mainViews/ShopView.vue';
import RestaurantView from '@/mainViews/RestaurantView.vue';

import AddPizzaView from '@/mainViews/subViews/AddPizzaView.vue';
import AddItemView from '@/mainViews/subViews/AddItemView.vue';
import NotificationView from '@/mainViews/subViews/NotificationView.vue';
import PrintView from '@/mainViews/subViews/PrintView.vue';
import SearchView from '@/mainViews/subViews/SearchView.vue';

const routes = [
    { path: '/shop', component: ShopView, children: [
        { path: '/addItem', component: AddItemView},
        { path: '/notification', component: NotificationView},
        { path: '/search', component: SearchView}
    ]},
    { path: '/restaurant', component: RestaurantView, children: [
        { path: '/addItem', component: AddPizzaView},
        { path: '/search', component: SearchView},
        { path: '/print', component: PrintView}
    ]},   
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router;