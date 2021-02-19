<template>
    <div class="logo-container noselect centred">
        <div class="centred">
            <span class="icon material-icons">face</span>
            <span class="logo-title">CONCIERGE</span>
        </div>
        <span class="logo-subtitle">Providing a helping hand, through speech.</span>
    </div>
</template>

<script>
    export default {
        name: "footer-logo"
    }
</script>

<style scoped>
    .logo-container {
        margin-top: auto;
        padding-bottom: 16px;
        padding-top: 28px;
        flex-direction: column;
    }

    .logo-container .icon {
        margin-right: 4px;
        margin-top: 4px;
    }

    .logo-title {
        letter-spacing: .06em;
        transform: scale(.95, .925);
    }

    .logo-title, .logo-container .icon {
        font-size: 28px;
        font-weight: 900;
        color: var(--blue);
    }

    .logo-subtitle {
        color: rgb(107, 119, 140);
        font-size: 12px;
        text-align: center;
        margin-top: 8px;
    }
</style>