<template>
    <div class="docs">
        <docs-navbar>
            <div id="mobile-sidebar-container">
                <separator class="mobile-separator"></separator>
            </div>
        </docs-navbar>

        <div class="docs-page">
            <docs-sidebar></docs-sidebar>
            <div id="docs-content" class="docs-panel">
                <div class="docs-content">
                    <router-view></router-view>
                </div>
            </div>
        </div>
        <scroll-to-top div="#docs-content"></scroll-to-top>
    </div>
</template>

<script>
    import ScrollToTop from "../components/layout/scroll-to-top.vue";
    import DocsSidebar from "../components/docs/docs-sidebar.vue";
    import DocsNavbar from "../components/docs/docs-navbar.vue";
    import Separator from "./../components/layout/separator.vue";

    export default {
        name: "documentation",
        components: {DocsNavbar, DocsSidebar, ScrollToTop, Separator}
    }
</script>

<style scoped>
    body {
        overflow-y: hidden !important;
    }

    .docs {
        height: 100%;
        width: 100%;
        margin: 0;
        overflow: hidden;
    }

    .docs-page {
        display: flex;
        width: 100vw;
        height: calc(100vh - 76px);
        padding: 0;
        margin: 76px 0 0;
    }

    .docs-panel {
        flex: 1;
        padding-bottom: 32px;
        overflow-y: auto;
        height: calc(100% - 32px);
        margin: 0;
    }

    .docs-content {
        margin: 0 auto;
        padding: 38px;
        height: auto;
        max-width: 800px;
    }

    .mobile-sidebar {
        display: none;
        background: white;
        margin: 0;
        width: 100%;
    }

    .mobile-separator {
        margin-top: 48px !important;
        margin-bottom: 16px !important;
    }

    #mobile-sidebar-container {
        flex: 1;
    }

    @media (max-width: 1024px) {
        .mobile-sidebar-visible {
            display: block;
            padding-top: 16px;
        }
    }
</style>