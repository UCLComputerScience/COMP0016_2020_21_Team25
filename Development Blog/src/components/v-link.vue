<template>
    <router-link :to="href">{{ text }}
        <slot></slot>
    </router-link>
</template>

<script>
    export default {
        name: "v-link",
        props: {
            href: {
                type: String,
                default: ""
            },
            text: {
                type: String,
                default: ""
            }
        },
    }
</script>

<style scoped>

</style>