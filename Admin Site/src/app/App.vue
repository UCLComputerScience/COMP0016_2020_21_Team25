<template>
    <router-view v-slot="{ Component }">
        <component :is="Component"/>
    </router-view>
</template>

<script>
export default {
    name: "App",
    watch: {
        $route(to, from) {
            if (to.hash) {
                this.$nextTick(() => {
                    this.scrollToId(to.hash.slice(1));
                });
            }
        }
    },
    methods: {
        scrollToId(id) {
            try {
                const element = document.getElementById(id);
                window.scrollTo({
                    top: element.offsetTop - 72,
                    behaviour: "smooth",
                });
            } catch {
                location.hash = id;
            }
        }
    },
    created() {
        this.$store.dispatch("service/setServices");
        const profileImages = this.$store.getters["media/profileImages"];
        const serviceIcons = this.$store.getters["media/serviceIcons"];
        /**
         * Only fetch from backend if images aren't already cached.
         */
        if (Object.keys(profileImages).length === 0) {
            this.$store.dispatch("media/setProfileImages");
        }
        if (Object.keys(serviceIcons).length === 0) {
            this.$store.dispatch("media/setServiceIcons");
        }
    },
};
</script>

<style scoped>
</style>
