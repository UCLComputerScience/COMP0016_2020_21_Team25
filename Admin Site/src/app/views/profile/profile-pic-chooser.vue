<template>
    <div v-show="render" ref="container" class="profile-pic-chooser centred noselect">
        <div class="content centred">
            <span class="close-icon material-icons" v-on:click="cancel">close</span>
            <p class="extract">Select a new profile picture below and click confirm to set your choice.</p>
            <div class="pic-grid centred">
                <div class="image centred" :ref="setRef" v-for="image in images" :key="image">
                    <img :id="image.id" :alt="image.name" :src="image.image" v-on:click="select"/>
                </div>
            </div>
            <div class="button-group centred">
                <flat-button class="confirm" text="Confirm" v-on:click="confirm"></flat-button>
                <flat-button class="cancel" style="--button-bg: var(--red)" text="Cancel"
                             v-on:click="cancel"></flat-button>
            </div>
        </div>
    </div>
</template>

<script>
import {getProfileImage} from "../../../assets/scripts/util";
import FlatButton from "../../components/widgets/buttons/flat-button.vue";

export default {
    name: "profile-pic-chooser",
    components: { FlatButton },
    props: {
        data: Object,
        fn: Function,
    },
    computed: {
        images() {
            const images = [];
            const ids = this.$store.getters["media/profileIds"];
            for (const [id, name] of Object.entries(ids)) {
                const image = getProfileImage(id);
                images.push({ id, name, image });
            }
            return images;
        },
        render() {
            return this.display;
        },
    },
    data() {
        return {
            picRefs: [],
            display: false,
        };
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
            body.style.position = "";
            body.style.top = "";
            window.scrollTo(0, parseInt(scrollY || "0") * -1);
        },
        disableScroll() {
            const scrollY = document.documentElement.style.getPropertyValue("--scroll-y");
            const body = document.body;
            body.style.position = "fixed";
            body.style.top = `-${scrollY}`;
        },
        show() {
            this.display = true;
            this.disableScroll();
        },
        async confirm() {
            await this.fn();
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
        select(e) {
            const element = e.target;
            this.data.selected = element.id;
            this.disablePrevious();
            element.parentElement.classList.add("selected");
        },
        disablePrevious() {
            const prev = document.querySelector(".profile-pic-chooser .selected");
            if (prev !== null) prev.classList.remove("selected");
        },
    },
    mounted() {
        window.addEventListener("scroll", () => {
            document.documentElement.style.setProperty("--scroll-y", `${window.scrollY}px`);
        });
    },
    beforeUnmount() {
        this.enableScroll();
    },
};
</script>

<style scoped>
.profile-pic-chooser {
    background: rgba(0, 0, 0, 0.67);
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
    background: #FFF;
    flex-direction: column;
    padding: 24px;
    z-index: 30;
    max-height: 95%;
    overflow-y: auto;
    justify-content: flex-start;
    width: 90vw;
}

.profile-pic-chooser .close-icon {
    color: rgba(0, 0, 0, 0.44);
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
    color: rgba(0, 0, 0, 0.67);
    max-width: 75%;
    text-align: center;
    margin-top: 0;
    margin-bottom: 24px;
    font-weight: 600;
    font-size: 18px;
}

.profile-pic-chooser .pic-grid {
    margin-bottom: 16px;
    width: 80%;
    display: grid;
    grid-auto-rows: 1fr;
    grid-template-columns: repeat(auto-fill, minmax(10rem, 10fr));
    grid-gap: 1rem;
    place-items: center;
}

.profile-pic-chooser .button-group {
    flex-direction: column;
    width: 80%;
}

.profile-pic-chooser .button-group .confirm {
    margin-bottom: 12px;
}

.profile-pic-chooser .button-group > * {
    flex: 1;
    width: 100%;
}

.profile-pic-chooser .image {
    border: 4px solid transparent;
    cursor: pointer;
    width: 100%;
}

.profile-pic-chooser .image:hover {
    border-color: var(--light-green);
    filter: brightness(70%);
}

.profile-pic-chooser .image.selected {
    border-color: var(--light-blue);
    pointer-events: none;
}

@media (min-width: 620px) {
    .profile-pic-chooser .content {
        width: 80vw;
    }

    .profile-pic-chooser .button-group {
        flex-direction: row;
    }

    .profile-pic-chooser .button-group .confirm {
        margin-bottom: 0;
        margin-right: 8px;
    }

    .profile-pic-chooser .button-group .cancel {
        margin-left: 8px;
    }
}

@media (min-width: 820px) {
    .profile-pic-chooser .content {
        width: 66vw;
    }
}
</style>