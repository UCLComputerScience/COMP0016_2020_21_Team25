<template>
    <div>
        <welcome-card></welcome-card>
        <page>
            <div class="people">
                <h1>Hello World</h1>
                <p>.</p>
                <logout-button></logout-button>
            </div>
        </page>
    </div>
</template>

<script>
    import Page from "../../components/layout/Page.vue";
    import LogoutButton from "../../components/widgets/buttons/logout-button.vue";
    import WelcomeCard from "./welcome-card.vue";

    export default {
        name: "People",
        components: {WelcomeCard, LogoutButton, Page},
    }
</script>

<style scoped>
    .people {

    }
</style>