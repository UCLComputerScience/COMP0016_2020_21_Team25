<template>
    <router-link v-if="href !== ''" :to="href">{{ text }}
        <slot></slot>
    </router-link>

    <slot v-else></slot>
</template>

<script>
export default {
    name: "v-link",
    props: {
        href: {
            type: Object,
            default: ""
        },
        text: {
            type: String,
            default: ""
        }
    },
};
</script>

<style scoped>

</style>