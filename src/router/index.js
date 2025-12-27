import { createRouter, createWebHistory } from 'vue-router';
import ShopView from '@/mainViews/ShopView.vue';
import RestaurantView from '@/mainViews/RestaurantView.vue';

// Restaurant Routes
import SearchPizzaView from '@/mainViews/subViewsRestaurant/SearchPizzaView.vue';
import AddPizzaView from '@/mainViews/subViewsRestaurant/AddPizzaView.vue';
import PrintView from '@/mainViews/subViewsRestaurant/PrintView.vue';
import AddIngredientView from '@/mainViews/subViewsRestaurant/AddIngredientView.vue';
import SearchIngredientView from '@/mainViews/subViewsRestaurant/SearchIngredientView.vue';

// Shop Routes
import AddItemView from '@/mainViews/subViewsShop/AddItemView.vue';
import NotificationView from '@/mainViews/subViewsShop/NotificationView.vue';
import SearchItemView from '@/mainViews/subViewsShop/SearchItemView.vue';

const routes = [
    { path: '/restaurant', component: RestaurantView, children: [
        { path: 'addItem', name: 'addPizza', component: AddPizzaView},
        { path: 'search', name: 'searchPizza', component: SearchPizzaView},
        { path: 'print', component: PrintView},
        { path: 'srcIngredient', component: SearchIngredientView },
        { path: 'addIngredient', component: AddIngredientView }
    ]}, 
    { path: '/shop', component: ShopView, children: [
        { path: 'addItem', name: 'addProduct', component: AddItemView},
        { path: 'notification', component: NotificationView},
        { path: 'search', name: 'searchProduct', component: SearchItemView}
    ]},  
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router;