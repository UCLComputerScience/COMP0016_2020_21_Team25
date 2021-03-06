<template>
    <ul class="admin-circle centred noselect">
        <user
            v-for="member in members"
            :key="member"
            :ref="setRef"
            :fn="fn"
            :userId="member"
        ></user>
        <slot></slot>
    </ul>
</template>

<script>
import User from "./user.vue";

export default {
    name: "admin-circle",
    components: { User },
    props: {
        fn: {
            type: Function,
            default: (user, el) => {
            },
        },
    },
    computed: {
        members() {
            return this.$store.getters["member/memberIds"];
        },
    },
    data() {
        return {
            userRefs: [],
        };
    },
    beforeCreate() {
        this.$store.dispatch("member/fetchMembers", this.$route.params["username"]);
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
        },
    },
};
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