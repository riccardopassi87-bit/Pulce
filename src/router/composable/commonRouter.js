import { useRouter } from 'vue-router';

export function commonRouter(){
    const router = useRouter()

    function goNext(routePath) {
        router.push(routePath)
    }

    return { goNext }
}
