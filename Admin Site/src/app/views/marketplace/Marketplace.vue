<template>
    <div>
        <welcome-card></welcome-card>
        <page>
            <div class="marketplace">
                <h1>Hello World</h1>
                <p>.</p>
            </div>
        </page>
    </div>
</template>

<script>
    import Page from "../../components/layout/Page.vue";
    import WelcomeCard from "../people/welcome-card.vue";

    export default {
        name: "Marketplace",
        components: {WelcomeCard, Page},
    }
</script>

<style scoped>
    .marketplace {

    }
</style>