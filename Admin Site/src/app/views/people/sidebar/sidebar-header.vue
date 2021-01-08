<template>
    <div class="sidebar-header centred">
        <p class="sidebar-tagline tagline">Manage users</p>
        <h2 class="sidebar-title">Your Circle</h2>
        <span class="sidebar-header-icon material-icons">group</span>
    </div>
</template>

<script>
    export default {
        name: "sidebar-header"
    }
</script>

<style scoped>
    .sidebar-header {
        flex-direction: column;
        position: absolute;
        top: 16px;
        padding-left: 24px;
        padding-right: 24px;
        width: 100%;
        overflow: hidden;
    }

    .sidebar-title, .sidebar-tagline {
        margin: 0;
        text-align: left;
        width: 100%;
        opacity: 1;
        min-width: 100%;
        white-space: nowrap;
    }

    .sidebar-header-icon {
        font-size: 36px;
        font-weight: 400;
        display: none;
        position: absolute;
        top: 0;
    }

    .minimised-sidebar .sidebar-header {
        top: 8px;
    }

    .minimised-sidebar .sidebar-tagline, .minimised-sidebar .sidebar-title {
        opacity: 0;
    }

    .minimised-sidebar .sidebar-header-icon {
        display: flex;
    }

    @media (max-width: 1024px) {
        .sidebar-header {
            padding: 16px;
            position: relative;
        }

        .sidebar-title, .sidebar-tagline {
            min-width: unset;
            width: unset;
        }
    }
</style>