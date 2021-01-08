<template>
    <div class="dropdown-item" v-on:click="select">{{ item.text }}</div>
</template>

<script>
    export default {
        name: "dropdown-item",
        props: {
            item: Object,
        },
        methods: {
            select() {
                this.$parent.select(this.item);
                this.item.fn(this.item);
            }
        }
    }
</script>

<style scoped>
    .dropdown-item {
        color: var(--text-color);
        padding: 12px 24px;
        background: var(--page-bg-color);
        flex: 1;
        width: 100%;
        cursor: pointer;
    }

    .dropdown-item:hover {
        color: #fff;
        background: var(--light-blue);
    }
</style>