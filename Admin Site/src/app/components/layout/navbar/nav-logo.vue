<template>
    <div class="nav-logo noselect centred">
        <span class="logo-icon material-icons">face</span>
        <h1 class="logo-header">Concierge</h1>
    </div>
</template>

<script>
export default {
    name: "nav-logo"
};
</script>

<style scoped>
.nav-logo {
    border-radius: 5px;
    padding: 8px;
    height: 55%;
    cursor: default;
    position: absolute;
    left: 16px;
}

.logo-icon, .logo-header {
    font-weight: 800;
    font-size: 24px;
    color: #FFF;
}

.logo-header {
    display: none;
    text-transform: uppercase;
    letter-spacing: .25rem;
}

@media (min-width: 720px) {
    .logo-icon {
        margin-top: 2px;
        margin-right: 4px;
    }

    .logo-icon, .logo-header {
        font-size: 21px;
    }

    .logo-header {
        display: block;
    }
}
</style>