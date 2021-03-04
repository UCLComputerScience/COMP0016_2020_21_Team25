<template>
    <div v-if="show" v-once
         ref="container" class="welcome-container noselect centred">
        <div ref="card" class="welcome-card centred">
            <h1>{{ title }}, {{ name }}</h1>
            <profile-picture :border-width="3"></profile-picture>
            <p>{{ subtitle }}, <b>{{ name }}</b>. Your portal is ready for
                you when you are.</p>
            <flat-button text="Continue to portal" v-on:click="close"></flat-button>
        </div>
    </div>
</template>

<script>
import FlatButton from "../../components/widgets/buttons/flat-button.vue";
import ProfilePicture from "../../components/widgets/misc/profile-picture.vue";

export default {
    name: "welcome-card",
    components: {ProfilePicture, FlatButton},
    computed: {
        newlyRegistered() {
            return this.$store.getters["account/newlyRegistered"];
        },
        title() {
            if (this.newlyRegistered) {
                return "Welcome to Concierge";
            }
            return "Welcome back";
        },
        subtitle() {
            if (this.newlyRegistered) {
                return "Welcome to your Concierge portal";
            }
            return "It's good to see you again";
        },
        name() {
            return this.$store.getters["admin/admin"]["first-name"];
        },
        show() {
            const show = !this.$store.getters["account/entered"] || this.newlyRegistered;
            if (show) {
                this.disableScroll();
            }
            return show;
        }
    },
    methods: {
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
        close() {
            this.$refs.container.children[0].classList.add('exit');
            const that = this;
            setTimeout(() => {
                that.enableScroll();
                const parent = that.$refs.container.parentNode;
                parent.removeChild(that.$refs.container);
            }, 750);
        }
    },
    mounted() {
        this.$store.commit("account/entered", true);
        this.$store.commit("account/newlyRegistered", false);
    },
    beforeUnmount() {
        this.enableScroll();
    }
}
</script>

<style scoped>
.welcome-container {
    background: rgba(0, 0, 0, .67);
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    z-index: 29;
    padding: 24px;
}

.welcome-card {
    flex-direction: column;
    border-radius: var(--border-radius);
    padding: 36px 24px;
    background: #FFF;
    z-index: 30;
    box-shadow: rgba(0, 0, 0, 0.33) 0 0 18px;
    animation-name: slideDown;
    animation-duration: .5s;
    animation-fill-mode: forwards;
}

@keyframes slideDown {
    0% {
        transform: translateY(-100%);
    }

    100% {
        transform: translateY(0);
    }
}

.welcome-card h1 {
    text-align: center;
}

.welcome-card p {
    color: rgba(0, 0, 0, .67);
    max-width: 75%;
    text-align: center;
    margin-top: 24px;
    margin-bottom: 24px;
}

.exit {
    animation-name: slideOut;
    animation-duration: .75s;
    animation-fill-mode: forwards;
}

@keyframes slideOut {
    0% {
        transform: translateY(0);
    }

    100% {
        transform: translateY(150%);
    }
}
</style>