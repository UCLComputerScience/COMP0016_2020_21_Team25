<template>
    <div class="not-found centred" ref="container">
        <div class="content centred">
            <h1>Oops!</h1>
            <h3 ref="subtitle">404 - Page Not Found</h3>
            <p ref="text">
                The page you're looking for may have had it's name changed, be temporarily
                unavailable, been removed or may not exist at all.</p>
            <vlink-button href="/" text="Back to safety"></vlink-button>
        </div>
        <footer-logo></footer-logo>
    </div>
</template>

<script>
    import FlatButton from "../components/widgets/buttons/flat-button.vue";
    import VlinkButton from "../components/widgets/buttons/vlink-button.vue";
    import FooterLogo from "../components/widgets/misc/footer-logo.vue";

    export default {
        name: "NotFound",
        components: {FooterLogo, VlinkButton, FlatButton},
        methods: {
            resize() {
                let maxWidth = "75%";
                if (this.$refs.container.offsetWidth > 900) {
                    maxWidth = (this.$refs.subtitle.offsetWidth * 1.25).toString() + "px";
                }
                this.$refs.text.style.maxWidth = maxWidth;
            }
        },
        mounted() {
            this.resize();
            window.addEventListener('resize', this.resize);
        },
        beforeUnmount() {
            window.removeEventListener('resize', this.setColumns);
        }
    }
</script>

<style scoped>
    .not-found {
        flex-direction: column;
        min-height: 100vh;
        max-height: 100vh;
    }

    .content {
        flex-direction: column;
        margin-top: auto;
        margin-bottom: auto;
        padding-top: 16px;
    }

    h1 {
        font-weight: 900;
        font-size: 20vw;
        margin-top: 0;
        margin-bottom: 16px;
        color: var(--blue);
    }

    h3 {
        margin-top: 0;
        margin-bottom: 0;
        font-weight: 800;
        font-size: calc(1rem + 2vw);
        text-transform: uppercase;
    }

    p {
        text-align: center;
    }
</style>