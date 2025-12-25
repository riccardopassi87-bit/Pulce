import { useRouter, useRoute } from 'vue-router';

export function commonRouter(){
    const router = useRouter()
    const route = useRoute()

    function goNext(path) {

        if (path.startsWith('/')) {
            router.push(path)
            return
        }

        const parentPath = route.matched[0]?.path
        router.push(`${parentPath}/${path}`)
    }
    return { goNext }
}
