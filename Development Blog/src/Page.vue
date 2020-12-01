<template>
    <navbar></navbar>
    <div class="panel">
        <slot></slot>
    </div>
    <app-footer></app-footer>
    <scroll-to-top></scroll-to-top>
</template>

<script>
    import navbar from "./components/navbar.vue";
    import AppFooter from "./components/app-footer.vue";
    import FlatButton from "./components/flat-button.vue";
    import ScrollToTop from "./components/layout/scroll-to-top.vue";

    export default {
        name: 'Page',
        components: {ScrollToTop, navbar, AppFooter, FlatButton},
    }
</script>

<style>

</style>
