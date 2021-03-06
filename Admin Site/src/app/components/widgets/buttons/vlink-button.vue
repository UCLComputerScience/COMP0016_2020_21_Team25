<template>
    <v-link :href="href">
        <flat-button :style="'--button-bg: ' + bg" :text="text"></flat-button>
    </v-link>
</template>

<script>
import FlatButton from "./flat-button.vue";
import VLink from "./v-link.vue";

export default {
    name: "vlink-button",
    components: { VLink, FlatButton },
    props: {
        href: {
            type: String,
            default: ""
        },
        text: {
            type: String,
            default: ""
        },
        bg: {
            type: String,
            default: "var(--text-color)"
        },
    },
};
</script>

<style scoped>

</style>