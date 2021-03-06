<template>
    <div class="welcome full-page centred">
        <div class="form-content centred">
            <div class="form-card centred">
                <h1>{{ title }}</h1>
                <p>{{ subtitle }}</p>
                <div ref="login" class="login container centred">
                    <login-form ref="login-form"></login-form>
                </div>
                <div ref="signup" class="signup container centred">
                    <signup-form ref="signup-form"></signup-form>
                </div>
                <a class="inline-link" v-on:click="toggle">{{ infoText }}</a>
            </div>
        </div>
        <footer-logo></footer-logo>
    </div>
</template>

<script>
import FlatButton from "../../components/widgets/buttons/flat-button.vue";
import FooterLogo from "../../components/widgets/misc/footer-logo.vue";
import LoginForm from "./login-form.vue";
import SignupForm from "./signup-form.vue";

export default {
    name: "Login",
    components: { FooterLogo, FlatButton, SignupForm, LoginForm },
    data() {
        return {
            activeTab: this.$refs.login,
        };
    },
    computed: {
        title() {
            return (this.activeTab === this.$refs.login) ? "Sign In" : "Sign Up";
        },
        subtitle() {
            return (this.activeTab === this.$refs.login) ?
                "Enter your username or email and password to sign into your account." :
                "Fill out the details below to register a new account.";
        },
        infoText() {
            return (this.activeTab === this.$refs.login) ?
                "Don't have an account? Create one now" :
                "Already have an existing account? Sign in now";
        },
    },
    methods: {
        toggle() {
            this.activeTab.classList.remove("active-tab");
            this.activeTab = (this.activeTab === this.$refs.login) ?
                this.$refs.signup : this.$refs.login;
            this.activate();
        },
        activate() {
            this.activeTab.classList.add("active-tab");
            const activeForm = (this.activeTab === this.$refs.login) ?
                this.$refs["login-form"] :
                this.$refs["signup-form"];
            activeForm.activate();
        }
    },
    mounted() {
        this.activeTab = this.$refs.login;
        if (this.$route.redirectedFrom !== undefined) {
            const redirect = this.$route.redirectedFrom.path;
            if (redirect === "/sign-up" || redirect === "/register") {
                this.activeTab = this.$refs.signup;
            }
        }
        this.activate();
    }
};
</script>

<style scoped>
.welcome {
    flex-direction: column;
    padding: 24px;
}

.welcome::after {
    background: var(--blue);
    clip-path: polygon(0 0, 100% 0, 100% 60%, 0 90%);
    content: "";
    z-index: 0;
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
}

h1 {
    margin-bottom: 0;
}

.form-content {
    min-height: calc(90vh - 108px);
    margin-bottom: 16px;
}

.form-card {
    flex-direction: column;
    overflow: hidden;
    z-index: 1;
    border-radius: var(--border-radius);
}

.form-card > h1, .form-card > p {
    margin-right: auto;
    color: #FFF;
}

.form-card p {
    font-size: 18px;
}

.container {
    display: none;
    width: 100%;
}

.active-tab {
    display: flex;
}

.form-card > a {
    margin-top: 16px;
    color: var(--text-color);
    text-align: center;
}

@media (min-width: 640px) {
    .form-card p, .form-card h1 {
        color: var(--text-color);
    }

    .form-card {
        padding: 24px;
        background: var(--page-bg-color);
        box-shadow: rgba(0, 0, 0, 0.33) 0 0 18px;
    }
}

@media (min-width: 768px) {
    .form-card {
        padding: 36px;
    }
}

@media (min-width: 812px) {
    .form-card {
        padding: 48px;
    }
}

@media (min-width: 900px) {
    .form-card {
        padding: 64px;
    }
}

@media (min-width: 1024px) {
    .form-card {
        padding: 64px 80px;
    }
}

@media (min-width: 1600px) {
    .form-card {
        padding: 72px 90px;
    }
}
</style>