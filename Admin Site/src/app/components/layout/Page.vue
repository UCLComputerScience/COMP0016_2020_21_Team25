<template>
    <div class="page full-page centred">
        <navbar></navbar>
        <div class="page-content">
            <slot></slot>
        </div>
        <page-footer></page-footer>
    </div>
</template>

<script>
    import Navbar from "./navbar/Navbar.vue";
    import PageFooter from "./PageFooter.vue";

    export default {
        name: 'Page',
        components: {PageFooter, Navbar},
    }
</script>

<style scoped>
    .page {
        background: var(--page-bg-color);
        flex-direction: column;
    }

    .page-content {
        padding-top: var(--nav-height);
    }
</style>
