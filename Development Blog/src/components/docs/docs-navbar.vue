<template>
    <navbar class="docs-navbar">
        <slot></slot>
    </navbar>
</template>

<script>
    import Navbar from "../navbar.vue";
    import DocsSidebar from "./docs-sidebar.vue";

    export default {
        name: "docs-navbar",
        components: {DocsSidebar, Navbar}
    }
</script>

<style scoped>
    .docs-navbar {
        position: fixed;
        top: 0;
        left: 0;
        right: 0;
        height: 75px;
        box-sizing: border-box;
        border-bottom: 1px solid #eaecef;
        background: white;
    }

    @media (max-width: 1024px) {
        .docs-navbar {
            box-shadow: 0 0 8px 0 rgba(0, 0, 0, 0.15);
            -webkit-box-shadow: 0 0 8px 0 rgba(0, 0, 0, 0.15);
            -moz-box-shadow: 0 0 8px 0 rgba(0, 0, 0, 0.15);
            margin: 0;
        }
    }
</style>