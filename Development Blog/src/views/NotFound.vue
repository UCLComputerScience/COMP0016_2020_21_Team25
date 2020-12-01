<template>
    <div class="panel">
            <h1>404</h1>
            <p>Sorry, we can't find that page! Don't worry though, everything is STILL AWESOME!</p>
            <flat-button href="/" text="Home"></flat-button>
    </div>
</template>

<script>
    import Page from "../Page.vue";
    import FlatButton from "../components/flat-button.vue";

    export default {
        name: "404",
        components: {FlatButton, Page}
    }
</script>

<style scoped>
    html, body, #app {
        display: flex;
    }
    #app {
        justify-content: center !important;
        align-items: center !important;
    }
    .panel {
        display: flex;
        flex-direction: column;
    }

    h1 {
        float: right;
        padding: 32px;
        font-size: 16vw;
        text-align: right;
        margin: 0;
    }

    p {
        float: right;
        padding: 32px;
        text-align: right;
        margin: 0;
    }

    .flat-button {
        margin-inline-start: auto;
        margin-right: 32px;
        padding-right: 128px !important;
        padding-left: 128px !important;
    }

    @media (max-width: 1024px) {
        h1, p {
            float: none;
            text-align: center;
            padding: 16px;
        }
        h1 {
            padding-bottom: 0;
        }
        .flat-button {
            margin-inline-start: unset;
            margin-right: 0;
            padding: 10px 18px !important;
        }
    }
</style>