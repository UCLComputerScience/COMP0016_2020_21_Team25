<template>
    <div class="people-container">
        <welcome-card></welcome-card>
        <page>
            <sidebar></sidebar>
            <div v-if="this.$route.params.person !== undefined"
                 class="people-content centred">
                <navbar></navbar>
                <div class="people centred">
                    <router-view></router-view>
                    <footer-logo></footer-logo>
                </div>
            </div>
            <slot v-else></slot>
        </page>
    </div>
</template>

<script>
import Page from "../../components/layout/Page.vue";
import FooterLogo from "../../components/widgets/misc/footer-logo.vue";
import WelcomeCard from "../welcome/welcome-card.vue";
import Navbar from "./navbar/navbar.vue";
import Sidebar from "./sidebar/sidebar.vue";

export default {
    name: "People",
    components: { FooterLogo, Navbar, Sidebar, WelcomeCard, Page },
    beforeCreate() {
        this.$store.dispatch("admin/fetchAdmin", this.$route.params.username);
    }
};
</script>

<style>
.people-container .page-content {
    display: flex;
    width: 100%;
    height: 100vh;
}

.people-content {
    height: calc(100vh - var(--nav-height));
    flex-direction: column;
    justify-content: flex-start;
    position: relative;
    flex: 1;
    background: var(--page-bg-color);
}

.people {
    padding: 24px;
    width: 100%;
    overflow-y: auto;
    flex-direction: column;
    justify-content: flex-start;
}

@media (max-width: 1024px) {
    .people-container .page {
        justify-content: flex-start;
    }

    .people-container .page-content {
        flex-direction: column;
    }
}
</style>