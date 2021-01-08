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
    import WelcomeCard from "../welcome/welcome-card.vue";
    import FooterLogo from "../../components/widgets/misc/footer-logo.vue";

    export default {
        name: "Marketplace",
        components: {FooterLogo, Page, WelcomeCard}
    }
</script>

<style scoped>
</style>