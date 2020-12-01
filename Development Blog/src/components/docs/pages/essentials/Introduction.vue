<template>
    <docs-header text="Introduction"></docs-header>
    <div class="group">
        <div class="column">
            <docs-subheader text="What is Concierge?"></docs-subheader>
            <p>Concierge is a virtual assistant for simplifying the
                everyday lives of elderly users. Unlike other assistants,
                Concierge allows for the full integration of services from
                organisations, making them accessible via voice - we
                are focused on connecting users
                and organisations through speech. Concierge is fully
                capable of performing localised speech analysis and
                synthesis to ensure user privacy.</p>
        </div>
    </div>
    <div class="group">
        <div class="column">
            <docs-subheader text="Getting Started"></docs-subheader>
            <placeholder-text></placeholder-text>
            <placeholder-text></placeholder-text>
        </div>
    </div>
</template>

<script>
    import DocsHeader from "../docs-header.vue";
    import DocsSubheader from "../docs-subheader.vue";
    import PlaceholderText from "../../../layout/placeholder-text.vue";

    export default {
        name: "introduction",
        components: {DocsHeader, PlaceholderText, DocsSubheader}
    }
</script>

<style scoped>

</style>