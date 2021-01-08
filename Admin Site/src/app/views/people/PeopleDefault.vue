<template>
    <people>
        <div class="people-default centred">
            <h2 class="people-default-header" ref="header">Select a person from
                your circle using the sidebar on the left.</h2>
        </div>
    </people>
</template>

<script>
    import People from "./People.vue";

    export default {
        name: "people-default",
        components: {People},
        data() {
            return {
                header: null,
            }
        },
        methods: {
            setText() {
                let width = window.innerWidth || document.documentElement.clientWidth ||
                    document.body.clientWidth;
                if (this.$refs.header === null || this.$refs.header === undefined)
                    return;
                if (width <= 1024) {
                    this.$refs.header.innerHTML = "Select a person from your circle using the navigation bar above.";
                } else {
                    this.$refs.header.innerHTML = "Select a person from your circle using the sidebar on the left.";
                }
            }
        },
        mounted() {
            this.setText();
            window.addEventListener('resize', this.setText);
        },
        beforeUnmount() {
            window.removeEventListener('resize', this.setText);
        }
    }
</script>

<style scoped>
    .people-default {
        flex: 1;
    }

    .people-default-header {
        max-width: 30%;
        text-align: center;
        transition: max-width 300ms cubic-bezier(0.2, 0, 0, 1) 0s;
        -webkit-backface-visibility: hidden;
        -moz-backface-visibility: hidden;
        backface-visibility: hidden;
    }

    @media (max-width: 1024px) {
        .people-default {
            position: relative;
        }

        .people-default::before {
            content: "";
            position: absolute;
            left: 0;
            top: 0;
            right: 0;
            height: 4px;
            background: linear-gradient(180deg, rgba(9, 30, 66, 0.13) 0, rgba(9, 30, 66, 0.13) 1px, rgba(9, 30, 66, 0.08) 1px, rgba(9, 30, 66, 0) 4px);
        }

        .people-default-header {
            max-width: 66%;
            padding-left: 12px;
            padding-right: 12px;
        }
    }
</style>