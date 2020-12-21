<template>
    <router-link :to="href" v-if="href !== ''">{{ text }}
        <slot></slot>
    </router-link>

    <slot v-else></slot>
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