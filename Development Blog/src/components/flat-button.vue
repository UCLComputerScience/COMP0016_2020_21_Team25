<template>
    <a v-if="command !== null" class="flat-button" v-on:click="command">
        {{ text }}
    </a>
    <v-link v-else class="flat-button" :href="href" :text="text"></v-link>
</template>

<script>
    import VLink from "./v-link.vue";

    export default {
        name: "flat-button",
        components: {VLink},
        props: {
            text: {type: String},
            command: {
                type: Function,
                default: null
            },
            href: {
                type: String,
                default: ''
            },
        }
    }
</script>

<style scoped>
    .flat-button {
        text-decoration: none;
        color: var(--main);
        border: 1px solid var(--main);
        background: transparent;
        padding: 10px 18px;
        transition: all 0.3s ease;
        font-weight: 600;
        text-align: center;
        justify-content: center;
        align-items: center;
        font-size: 17px;
        box-shadow: 0 1px 3px 0 rgba(21, 28, 52, .16);
        display: flex;
        cursor: pointer;
    }

    .flat-button:active {
        background: var(--main);
        color: #f9f9f9;
    }

    @media (pointer: fine) {
        .flat-button:hover {
            background: var(--main);
            color: #f9f9f9;
        }
    }

    @media only screen and (pointer: coarse) {
        .flat-button {
            background: var(--main);
            color: #f9f9f9;
        }
    }
</style>