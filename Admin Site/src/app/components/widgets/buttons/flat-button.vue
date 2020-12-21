<template>
    <button class="flat-button centred">
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
        font-size: 22px;
        color: #fff;
        font-weight: 700;
        z-index: 2;
    }

    .flat-button, .flat-button:after {
        transition: .25s ease-in-out all;
        border-radius: var(--border-radius);
        cursor: pointer;
    }

    .flat-button:after {
        background: var(--blue);
        position: absolute;
        bottom: 0;
        left: 0;
        right: 0;
        top: 100%;
        content: "";
        z-index: 1;
    }

    .flat-button:hover {
        box-shadow: 0 6px 12px -1px rgba(0, 0, 0, .33);
        transform: translateY(-10px);
    }

    .flat-button:hover:after {
        top: 0;
    }
</style>