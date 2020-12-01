<template>
    <h2 class="docs-subheader" :id="headerID">{{ text }}</h2>
</template>

<script>
    export default {
        name: "docs-subheader",
        props: {
            text: String,
        },
        computed: {
            headerID() {
                return this.text.replace(/\s+/g, '-').replace('\?', '').toLowerCase();
            }
        }
    }
</script>

<style scoped>
    .docs-subheader {
        font-size: 26px;
        padding-bottom: 5px;
        border-bottom: 1px solid #eaecef;
    }
</style>