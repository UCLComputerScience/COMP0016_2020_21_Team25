<template>
    <li ref="button" class="people-nav-item centred" v-on:click="go">{{ text }}</li>
</template>

<script>
export default {
    name: "nav-item",
    props: {
        text: String,
        href: String,
    },
    watch: {
        $route(to, from) {
            this.toggle(to);
        }
    },
    methods: {
        toggle(route) {
            if (route.name.includes(this.href)) {
                this.activate();
            } else {
                this.deactivate();
            }
        },
        go() {
            this.$router.push({ name: this.href });
        },
        activate() {
            this.$refs.button.classList.add("active-people-item");
        },
        deactivate() {
            this.$refs.button.classList.remove("active-people-item");
        }
    },
    mounted() {
        this.toggle(this.$route);
    }
};
</script>

<style scoped>
.people-nav-item {
    border-radius: 5px;
    padding: 6px;
    cursor: pointer;
    color: #344563;
    text-align: center;
    font-weight: 400;
    flex: 1;
    font-size: 14px;
}

@media (pointer: fine) {
    .people-nav-item:hover {
        background: #E1EDFF;
        color: var(--blue);
    }
}

.people-nav-item:not(:last-child) {
    margin-right: 16px;
}

.active-people-item {
    pointer-events: none;
    color: var(--light-blue);
}

@media only screen and (max-width: 1024px) and (pointer: coarse) {
    .active-people-item {
        color: var(--blue);
    }
}
</style>