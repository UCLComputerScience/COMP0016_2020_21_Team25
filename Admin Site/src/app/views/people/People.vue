<template>
    <div>
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

    export default {
        name: "People",
        components: {LogoutButton, Page},
    }
</script>

<style scoped>
    .people {

    }
</style>