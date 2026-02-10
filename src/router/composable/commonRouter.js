import { computed } from 'vue';
import { useRouter, useRoute } from 'vue-router';


// ROUTE CHECK - to divert between children routes depending on the parent
export function commonRouter(){
    const router = useRouter()
    const route = useRoute()

    function goNext(path) {

        if (path.startsWith('/')) {
            router.push(path)
            return
        }

        const parentPath = (route.matched[0]?.path || '').replace(/\/$/, '')
        router.push(`${parentPath}/${path}`)
    }

 // ROUTE CHECK - to select button text depending on parent route

    const isRestaurant = computed(() => {
        return route.matched[0]?.path === '/restaurant' || ['addPizza', 'searchPizza'].includes(route.name)
    })
    const addButtonText = computed(() => {
        return isRestaurant.value ? 'Add Pizza' : 'Add Product';
    })
    const searchButtonText = computed (() => {
        return isRestaurant.value ? 'Search Pizza' : 'Search Product';
    })

// ACTIVE ROUTE CHECK - will change the button style of the actual position

    const isRouteActive = (routeName) => {
        return route.name?.includes(routeName) ||
        route.path.includes(`/${routeName}`) ||
        route.path.includes(`/${routeName}/`)
    }
    const getButtonClass = (routeName) => [
        isRestaurant.value ? '/restaurant' : '/shop',
        isRouteActive(routeName) ? 'button-active' : 'button'
    ]
    const isParentActive = (parentPath) => {
        return route.matched[0]?.path === parentPath
    }

    return {
        goNext,
        addButtonText,
        searchButtonText,
        getButtonClass,
        isParentActive
    }
}
