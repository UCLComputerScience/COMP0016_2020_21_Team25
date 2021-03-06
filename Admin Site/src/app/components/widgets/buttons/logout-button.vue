<template>
    <div class="sign-out" v-on:click="logout">
        Sign out
        <slot></slot>
    </div>
</template>

<script>
export default {
    name: "logout-button",
    methods: {
        logout() {
            this.$store.dispatch("account/logout");
        }
    }
};
</script>

<style scoped>
.sign-out {
    background: var(--blue);
    color: #FFF;
    padding: 12px 24px;
}
</style>