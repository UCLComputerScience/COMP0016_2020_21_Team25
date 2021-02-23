<template>
    <button ref="button" class="flat-button noselect centred">
        <span>{{ text }}</span>
        <slot></slot>
    </button>
</template>

<script>
export default {
    name: "flat-button",
    props: {
        type: String,
        text: String,
    },
    methods: {
        enable() {
            this.$refs.button.classList.remove("disabled");
        },

        disable() {
            this.$refs.button.classList.add("disabled");
        }
    }
}
</script>

<style scoped>
.flat-button {
    --button-bg: var(--text-color);
    position: relative;
    background: var(--button-bg);
    outline: 0;
    border: 0;
}

.flat-button span {
    padding: 12px 24px;
    font-size: 18px;
    color: #FFF;
    font-weight: 700;
    z-index: 2;
    text-transform: uppercase;
}

.flat-button {
    border-radius: var(--border-radius);
    cursor: pointer;
}

.flat-button:hover {
    filter: brightness(65%);
}

.disabled {
    pointer-events: none;
    filter: brightness(40%);
}
</style>