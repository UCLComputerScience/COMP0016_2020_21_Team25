<template>
    <ul class="admin-circle centred noselect">
        <user :data="user" :fn="fn" :ref="setRef" v-for="user in users"></user>
        <slot></slot>
    </ul>
</template>

<script>
    import User from "./user.vue";

    export default {
        name: "admin-circle",
        components: {User},
        props: {
            fn: {
                type: Function, default: (user, el) => {
                }
            }
        },
        computed: {
            users() {
                return this.$store.getters["member/members"];
            }
        },
        data() {
            return {
                userRefs: []
            }
        },
        beforeUpdate() {
            this.userRefs = [];
        },
        methods: {
            setRef(el) {
                this.userRefs.push(el);
            },
            elements() {
                return this.userRefs;
            }
        }
    }
</script>

<style scoped>
    .admin-circle {
        padding: 32px;
        width: 100%;
        justify-content: flex-start;
        list-style: none;
        align-items: stretch;
    }

    .admin-circle > *:not(:last-child) {
        margin-right: 16px;
    }
</style>