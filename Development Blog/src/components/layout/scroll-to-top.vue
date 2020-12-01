<template>
    <flat-button :command="toTop" id="to-top" text="&#9650;"></flat-button>
</template>

<script>
    import FlatButton from "../flat-button.vue";
    import $ from "jquery";

    export default {
        name: "scroll-to-top",
        components: {FlatButton},
        data() {
            return {
                button: null,
                container: null,
                isDOM: false,
            }
        },
        props: {
            div: String,
        },
        methods: {
            toggle() {
                let scrollDistance;
                if (this.isDOM) {
                    scrollDistance = document.body.scrollTop || document.documentElement.scrollTop;
                } else {
                    scrollDistance = this.container.scrollTop;
                }
                if (scrollDistance > 75) {
                    this.button.classList.add('scroll-visible');
                } else {
                    this.button.classList.remove('scroll-visible');
                }
            },
            toTop() {
                this.container.animate({scrollTop: 0}, 500);
                if (!this.isDOM)
                    this.container.scrollTo({top: 0, behavior: 'smooth'});
                this.container.scrollTop = 0;
                this.button.classList.remove("hasactive");
                this.button.classList.remove('scroll-visible');
            }
        },
        mounted() {
            this.button = document.getElementById("to-top");
            let div = $(this.div)[0];
            if (typeof div !== 'undefined') {
                this.container = div;
                this.container.onscroll = this.toggle;
            } else {
                this.container = $('html, body');
                this.isDOM = true;
                this.toggle.bind(this);

                function toggler(func) {
                    func();
                }

                window.onscroll = () => {
                    toggler(this.toggle)
                };
            }
            this.toggle();
        }
    }
</script>

<style scoped>
    #to-top {
        border-radius: 50%;
        width: 42px;
        height: 42px;
        padding: 0;
        box-shadow: 0 0 8px rgba(0, 0, 0, 0.25);
        display: none;
        position: fixed;
        bottom: 20px;
        right: 20px;
        z-index: 99;
        border: 2px solid var(--main);
    }

    .scroll-visible {
        display: flex !important;
    }
</style>