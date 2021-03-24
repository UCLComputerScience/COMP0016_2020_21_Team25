<template>
    <div ref="button" class="nav-item centred" v-on:click="go">{{ text }}</div>
</template>

<script>
export default {
    name: "nav-item",
    props: {
        text: String,
        href: String
    },
    methods: {
        activate() {
            this.$refs.button.classList.add("active-nav-item");
        },
        deactivate() {
            this.$refs.button.classList.remove("active-nav-item");
        },
        go() {
            this.$router.push({ name: this.href });
        }
    }
};
</script>

<style scoped>
.nav-item {
    color: #FFF;
    text-align: center;
    border-radius: 5px;
    font-weight: 700;
    padding: 2px 8px;
    cursor: pointer;
    line-height: 1.5;
}

.nav-item:hover {
    background: var(--light-blue);
}

.nav-item:not(:last-child) {
    margin-right: 10px;
}

.active-nav-item {
    pointer-events: none;
    background: var(--green);
}
</style>