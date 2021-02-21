<template>
    <a :aria-label="label" :href="href" rel="noopener" target="_blank">
        <flat-button :style="'--button-bg: ' + bg" :text="text"
                     :type="type"></flat-button>
    </a>
</template>

<script>
import FlatButton from "./flat-button.vue";
import VLink from "./v-link.vue";

export default {
    name: "link-button",
    components: {VLink, FlatButton},
    props: {
        href: {
            type: String,
            default: ""
        },
        text: {
            type: String,
            default: ""
        },
        type: String,
        bg: {
            type: String,
            default: "var(--text-color)"
        },
        label: {
            type: String,
            default: ""
        }
    },
}
</script>

<style scoped>

</style>