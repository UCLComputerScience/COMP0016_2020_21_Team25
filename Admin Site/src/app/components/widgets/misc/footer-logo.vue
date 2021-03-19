<template>
    <div class="logo-container noselect centred">
        <div class="centred">
            <span class="icon material-icons">face</span>
            <span class="logo-title">CONCIERGE</span>
        </div>
        <span class="logo-subtitle">Providing a helping hand, through speech.</span>
        <collaborators></collaborators>
    </div>
</template>

<script>
import Collaborators from "../../layout/navbar/mobile-nav/collaborators/collaborators.vue";

export default {
    name: "footer-logo",
    components: { Collaborators }
};
</script>

<style>
.logo-container {
    margin-top: auto;
    padding-top: 28px;
    flex-direction: column;
    cursor: default;
    z-index: 1;
}

.logo-container .icon {
    margin-right: 4px;
    margin-top: 4px;
}

.logo-container .logo-title {
    letter-spacing: .06em;
    transform: scale(.95, .925);
}

.logo-container .logo-title, .logo-container .icon {
    font-size: 28px;
    font-weight: 900;
    color: var(--blue);
}

.logo-container .logo-subtitle {
    color: rgb(107, 119, 140);
    font-size: 12px;
    text-align: center;
    margin-top: 8px;
}

.logo-container span {
    text-shadow: -1px -1px 0 var(--page-bg-color), 1px -1px 0 var(--page-bg-color), -1px 1px 0 var(--page-bg-color), 1px 1px 0 var(--page-bg-color);
}

.logo-container .collaborators {
    margin-top: 8px;
}

.logo-container .collaborators .tagline {
    display: none;
}

.logo-container .collaborators .logos {
    flex-direction: row;
}

.logo-container .collab-logo:not(:last-child) {
    margin-bottom: 0;
    margin-right: 8px;
}
</style>