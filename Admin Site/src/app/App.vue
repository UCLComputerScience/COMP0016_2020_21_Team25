<template>
    <router-view v-slot="{ Component }">
        <component :is="Component"/>
    </router-view>
</template>

<script>
export default {
    name: "App",
    created() {
        this.$store.dispatch("service/setServices");
        this.$store.dispatch("media/setImages");
    },
};
</script>

<style scoped>
</style>
