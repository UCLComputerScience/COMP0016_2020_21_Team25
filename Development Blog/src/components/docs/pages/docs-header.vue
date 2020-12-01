<template>
    <h1 :id="text" class="docs-header">{{ text }}</h1>
</template>

<script>
    export default {
        name: "docs-header",
        props: {
            text: String
        }
    }
</script>

<style scoped>
    .docs-header {
        display: flex;
        flex-wrap: wrap;
        align-items: center;
        margin-top: 24px;
        margin-bottom: 24px;
        font-size: 36px;
        font-weight: 600;
    }
</style>