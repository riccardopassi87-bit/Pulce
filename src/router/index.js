import { createRouter, createWebHistory } from 'vue-router';
import ShopView from '@/mainViews/ShopView.vue';
import RestaurantView from '@/mainViews/RestaurantView.vue';

import AddPizzaView from '@/mainViews/subViews/AddPizzaView.vue';
import AddItemView from '@/mainViews/subViews/AddItemView.vue';
import NotificationView from '@/mainViews/subViews/NotificationView.vue';
import PrintView from '@/mainViews/subViews/PrintView.vue';
import SearchItemView from '@/mainViews/subViews/SearchItemView.vue';
import SearchPizzaView from '@/mainViews/subViews/SearchPizzaView.vue';


const routes = [
    { path: '/shop', component: ShopView, children: [
        { path: 'addItem', name: 'addProduct', component: AddItemView},
        { path: 'notification', component: NotificationView},
        { path: 'search', name: 'searchProduct', component: SearchItemView}
    ]},
    { path: '/restaurant', component: RestaurantView, children: [
        { path: 'addItem', name: 'addPizza', component: AddPizzaView},
        { path: 'search', name: 'searchPizza', component: SearchPizzaView},
        { path: 'print', component: PrintView}
    ]},   
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router;