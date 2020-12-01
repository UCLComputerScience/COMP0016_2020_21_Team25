<template>
    <page>
        <h1>Building Concierge, our development process</h1>
        <p class="subtitle">The following posts outline each stage in our
            development process of Concierge and how we set about building it.</p>

        <separator></separator>

        <section>
            <p id="latest-post" class="latest-label">latest post</p>
            <brainstorming latest="true"></brainstorming>
        </section>

        <separator></separator>

        <section>
            <gathering-requirements></gathering-requirements>
        </section>

        <separator></separator>

        <section>
            <brainstorming></brainstorming>
        </section>


        <separator></separator>
    </page>
</template>

<script>
    import Page from "../Page.vue";
    import separator from "../components/layout/separator.vue";
    import VSeparator from "../components/layout/v-separator.vue";
    import FlatButton from "../components/flat-button.vue";
    // Blog Posts
    import GatheringRequirements from "../components/blog-posts/gathering-requirements.vue"
    import brainstorming from "../components/blog-posts/brainstorming.vue";

    export default {
        name: "blog",
        components: {
            Page, separator, VSeparator, FlatButton, GatheringRequirements,
            brainstorming
        }
    }
</script>

<style scoped>

    .latest-label {
        text-transform: uppercase;
        float: right;
        color: var(--main);
        margin: 0;
    }
</style>