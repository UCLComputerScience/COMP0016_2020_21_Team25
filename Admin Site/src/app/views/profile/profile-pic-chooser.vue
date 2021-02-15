<template>
    <div class="profile-pic-chooser centred noselect" ref="container" v-show="render">
        <div class="content centred">
            <span class="close-icon material-icons" v-on:click="close">close</span>
            <p class="extract">Select a new profile picture below.</p>
            <div class="pic-grid centred">
                <div class="row centred" v-for="row in chunks">
                    <div :ref="setRef" class="image centred" v-for="image in row">
                        <img :alt="image.name" :src="image.image"
                             v-on:click="select(image.name)">
                    </div>
                </div>
            </div>
            <div class="button-group centred">
                <flat-button class="confirm" text="Confirm" v-on:click="confirm">
                </flat-button>
                <flat-button class="cancel" style="--button-bg: var(--red)"
                             text="Cancel" v-on:click="cancel"></flat-button>
            </div>
        </div>
    </div>
</template>

<script>
    import backend from "../../../assets/scripts/backend/backend";
    import FlatButton from "../../components/widgets/buttons/flat-button.vue";

    export default {
        name: "profile-pic-chooser",
        components: {FlatButton},
        props: {
            data: Object,
            fn: Function,
        },
        computed: {
            images() {
                return backend.fetchAllProfilePictures();
            },
            chunks() {
                const chunks = []
                for (let i = 0, j = this.images.length; i < j; i += this.columns)
                    chunks.push(this.images.slice(i, i + this.columns));
                return chunks;
            },
            render() {
                return this.display;
            }
        },
        data() {
            return {
                picRefs: [],
                columns: 3,
                display: false,
            }
        },
        beforeUpdate() {
            this.picRefs = [];
        },
        methods: {
            setRef(el) {
                this.picRefs.push(el);
            },
            enableScroll() {
                const body = document.body;
                const scrollY = body.style.top;
                body.style.position = '';
                body.style.top = '';
                window.scrollTo(0, parseInt(scrollY || '0') * -1);
            },
            disableScroll() {
                const scrollY = document.documentElement.style.getPropertyValue('--scroll-y');
                const body = document.body;
                body.style.position = 'fixed';
                body.style.top = `-${scrollY}`;
            },
            show() {
                this.display = true;
                this.disableScroll();
            },
            confirm() {
                this.fn();
                this.data.original = this.data.selected;
                this.close();
            },
            cancel() {
                this.data.selected = this.data.original;
                this.close();
            },
            close() {
                this.disablePrevious();
                this.display = false;
                this.enableScroll();
            },
            select(newPic) {
                this.data.selected = newPic;
                this.disablePrevious();
                const el = document.querySelector(`.profile-pic-chooser [alt='${newPic}']`)
                el.parentElement.classList.add("selected");
            },
            disablePrevious() {
                const prev = document.querySelector(".profile-pic-chooser .selected");
                if (prev !== null)
                    prev.classList.remove("selected");
            }
        },
        mounted() {
            window.addEventListener('scroll', () => {
                document.documentElement.style.setProperty('--scroll-y', `${window.scrollY}px`);
            });
        },
        beforeUnmount() {
            this.enableScroll();
        }
    }
</script>

<style scoped>
    .profile-pic-chooser {
        background: rgba(0, 0, 0, .67);
        position: fixed;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        z-index: 29;
        padding: 24px;
    }

    .profile-pic-chooser .content {
        position: relative;
        border-radius: var(--border-radius);
        background: #fff;
        flex-direction: column;
        padding: 24px;
        z-index: 30;
        max-height: 95%;
        overflow-y: auto;
        justify-content: flex-start;
        width: max-content;
    }

    .profile-pic-chooser .close-icon {
        color: rgba(0, 0, 0, .44);
        font-weight: 800;
        position: absolute;
        right: 16px;
        top: 16px;
        cursor: pointer;
    }

    .profile-pic-chooser .close-icon:hover {
        color: var(--red);
    }

    .profile-pic-chooser .content p {
        color: rgba(0, 0, 0, .67);
        max-width: 75%;
        text-align: center;
        margin-top: 0;
        margin-bottom: 24px;
        font-weight: 600;
        font-size: 18px;
    }

    .profile-pic-chooser .pic-grid {
        flex-direction: column;
        margin-bottom: 24px;
        width: 80%;
    }

    .profile-pic-chooser .confirm {
        margin-right: 8px;
    }

    .profile-pic-chooser .cancel {
        margin-left: 8px;
    }

    .profile-pic-chooser .button-group > * {
        flex: 1;
    }
    
    .profile-pic-chooser .row:not(:last-child) {
        margin-bottom: 16px;
    }

    .profile-pic-chooser .row .image:not(:last-child) {
        margin-right: 16px;
    }

    .profile-pic-chooser .image {
      border: 4px solid transparent;
      cursor: pointer;
    }

    .profile-pic-chooser .image:hover {
        border-color: var(--light-green);
        filter: brightness(70%);
    }

    .profile-pic-chooser .row .image.selected {
        border-color: var(--light-blue);
        pointer-events: none;
    }
</style>