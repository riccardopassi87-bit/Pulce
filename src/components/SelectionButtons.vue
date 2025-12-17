<script setup>
import { useRouter } from 'vue-router';

const router = useRouter()

function goNext(){
    router.push('/configure');
}
</script>

<template>
    <div class="container-fluid min-vh-100">
        <div class="row min-vh-100">
            <div class="col-12 col-md-6 d-flex align-items-center justify-content-center">
                <button class="btn btn-primary w-100 h-50 fs-2" @click="goNext">
                    Shop Manager
                </button>
            </div>

            <div class="col-12 col-md-6 d-flex align-items-center justify-content-center">
                <button class="btn btn-primary w-100 h-50 fs-2" @click="goNext">
                    Menu Generator
                </button>
            </div>
        </div>
    </div>
</template>