<script setup>
    import ControlPanel from '@/components/ControlPanel.vue';
    import Visualization from '@/components/Visualization.vue';
    import BtnElement from '@/components/BtnElement.vue';
</script>

<template>
    <div class="container mt-4">
        <div class="row">
            <div class="col-12 col-md-4 mb-3">
                <ControlPanel />
            </div>

            <div class="col-12 col-md-8">
                <Visualization />
                <BtnElement />
            </div>
        </div>
    </div>
</template>