<template>
    <div>
        <welcome-card></welcome-card>
        <page>
            <router-view></router-view>
            <footer-logo></footer-logo>
        </page>
    </div>
</template>

<script>
import Page from "../../components/layout/Page.vue";
import FooterLogo from "../../components/widgets/misc/footer-logo.vue";
import WelcomeCard from "../welcome/welcome-card.vue";

export default {
    name: "Marketplace",
    components: { FooterLogo, Page, WelcomeCard },
    beforeCreate() {
        this.$store.dispatch("admin/fetchAdmin", this.$route.params.username);
    }
};
</script>

<style scoped>
</style>