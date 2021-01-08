<template>
    <div class="modal-dialog centred noselect"
         ref="container" v-show="render">
        <div class="content centred">
            <span class="close-icon material-icons" v-on:click="close">close</span>
            <slot></slot>
        </div>
    </div>
</template>

<script>
    export default {
        name: "modal-dialog",
        computed: {
            render() {
                return this.visible;
            },
        },
        data() {
            return {
                visible: false,
            }
        },
        methods: {
            enableScroll() {
                const body = document.body;
                const scrollY = body.style.top;
                body.style.position = "";
                body.style.top = "";
                window.scrollTo(0, parseInt(scrollY || "0") * -1);
            },
            disableScroll() {
                const scrollY = document.documentElement.style.getPropertyValue(
                    "--scroll-y");
                const body = document.body;
                body.style.position = "fixed";
                body.style.top = `-${scrollY}`;
            },
            show() {
                this.visible = true;
                this.disableScroll();
            },
            confirm() {
                this.close();
            },
            cancel() {
                this.close();
            },
            close() {
                this.visible = false;
                this.enableScroll();
            },
        },
        mounted() {
            window.addEventListener("scroll", () => {
                document.documentElement.style.setProperty(
                    "--scroll-y",
                    `${window.scrollY}px`
                );
            });
        },
        beforeUnmount() {
            this.enableScroll();
        },
    };
</script>

<style scoped>
    .modal-dialog {
        background: rgba(0, 0, 0, 0.67);
        position: fixed;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        z-index: 29;
        padding: 24px;
        overflow-y: auto;
    }

    .modal-dialog .content {
        position: relative;
        border-radius: var(--border-radius);
        background: #fff;
        flex-direction: column;
        padding: 36px 24px;
        z-index: 30;
    }

    .modal-dialog .content .close-icon {
        color: rgba(0, 0, 0, .44);
        font-weight: 800;
        position: absolute;
        right: 16px;
        top: 16px;
        cursor: pointer;
    }

    .modal-dialog .content .close-icon:hover {
        color: var(--red);
    }

    .modal-dialog .content p {
        color: rgba(0, 0, 0, .67);
        max-width: 66.7%;
        text-align: center;
        margin-top: 0;
        margin-bottom: 24px;
        font-weight: 400;
        font-size: 18px;
    }

    .modal-dialog .content .button-group .flat-button:first-child {
        margin-right: 8px;
    }

    .modal-dialog .content .button-group .flat-button:last-child {
        margin-left: 8px;
    }

    .profile-pic-chooser .button-group > * {
        flex: 1;
    }
</style>
