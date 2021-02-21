<template>
    <span :style="'--outline-color: ' + outlineColor" class="outlined-text">
        {{ text }}</span>
</template>

<script>
export default {
    name: "outlined-text",
    props: {
        outlineColor: {type: String, default: "var(--text-color)"},
        text: {type: String}
    }
}
</script>

<style scoped>
.outlined-text {
    --outline-color: var(--text-color);
    text-shadow: -1px -1px 0 var(--outline-color),
    1px -1px 0 var(--outline-color),
    -1px 1px 0 var(--outline-color),
    1px 1px 0 var(--outline-color);
}
</style>