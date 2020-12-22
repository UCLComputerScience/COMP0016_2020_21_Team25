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
                this.$store.dispatch("logout");
            }
        }
    }
</script>

<style scoped>
    .sign-out {
        background: var(--blue);
        color: #fff;
        padding: 12px 24px;
    }
</style>