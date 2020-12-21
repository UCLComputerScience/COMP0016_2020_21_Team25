<template>
    <div>
        <page>
            <div class="welcome centred">
                <div class="form-content centred">
                    <h1>{{ title }}</h1>
                    <p>{{ subtitle }}</p>
                    <div class="welcome-form centred">
                        <div class="login container centred" ref="login">
                            <login-form ref="login-form"></login-form>
                        </div>
                        <div class="signup container centred" ref="signup">
                            <signup-form ref="signup-form"></signup-form>
                        </div>
                    </div>
                </div>
                <div class="info-container centred">
                    <p class="info-text">{{ infoText }}</p>
                    <flat-button :text="buttonText" v-on:click="toggle"></flat-button>
                </div>
            </div>
        </page>
    </div>
</template>

<script>
    import Page from "../../components/layout/Page.vue";
    import LoginForm from "./login-form.vue";
    import SignupForm from "./signup-form.vue";
    import FlatButton from "../../components/widgets/buttons/flat-button.vue";

    export default {
        name: "Login",
        components: {FlatButton, SignupForm, LoginForm, Page},
        data() {
            return {
                activeTab: this.$refs.login,
            }
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
            buttonText() {
                return (this.activeTab === this.$refs.login) ? "Sign Up" : "Sign In";
            }
        },
        methods: {
            toggle() {
                this.activeTab.classList.remove("active-tab");
                this.activeTab = (this.activeTab === this.$refs.login) ?
                    this.$refs.signup : this.$refs.login;
                this.activeTab.classList.add("active-tab");
            }
        },
        mounted() {
            this.activeTab = this.$refs.login;
            const redirectedFrom = this.$route.redirectedFrom;
            if (redirectedFrom !== undefined) {
                const redirect = redirectedFrom.path;
                if (redirect === "/sign-up" || redirect === "/register") {
                    this.activeTab = this.$refs.signup;
                }
            }
            this.activeTab.classList.add("active-tab");
        }
    }
</script>

<style scoped>
    .welcome {
        align-items: flex-start;
        flex-direction: column-reverse;
        width: 100%;
        min-height: 100vh;
    }

    h1 {
        font-size: 36px;
        margin-bottom: 0;
    }

    .form-content {
        flex-direction: column;
        padding: 16px;
        flex: 3;
        overflow: hidden;
        justify-content: flex-start;
        width: 100%;
    }

    .form-content > h1, .form-content > p {
        margin-right: auto;
    }

    .form-content p {
        font-size: 18px;
    }

    .welcome-form {
        height: 100%;
        width: 100%;
        position: relative;
    }

    .container {
        opacity: 0;
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        z-index: -1;
        transition: .2s ease-in-out opacity;
        width: 100%;
    }

    .active-tab {
        position: relative;
        opacity: 1;
        z-index: 1;
    }

    .info-container {
        position: relative;
        flex-direction: column;
        padding: 32px;
        flex: 1;
        height: 100%;
        width: 100%;
    }

    .info-container:after {
        content: '';
        position: absolute;
        opacity: 0.5;
        width: 100%;
        height: 100%;
        z-index: 0;
        background: rgba(90, 93, 240, .8);
        background: linear-gradient(to bottom, rgba(90, 93, 240, 1), rgba(0, 0, 0, 1));
    }

    .info-container > * {
        z-index: 1;
    }

    .info-container p {
        color: #fff;
        font-size: 21px;
        text-align: center;
        text-shadow: 0 0 6px rgba(255, 255, 255, .66);
    }

    @media (min-width: 450px) {

    }

    @media (min-width: 640px) {
        .form-content {
            padding: 24px;
        }
    }

    @media (min-width: 768px) {
        .form-content {
            padding: 36px;
        }
    }

    @media (min-width: 812px) {
        .form-content {
            padding: 48px;
        }
    }

    @media (min-width: 900px) {
        .form-content {
            padding: 64px;
        }
    }

    @media (min-width: 1024px) {
        .welcome {
            flex-direction: row;
            border-radius: var(--border-radius);
            box-shadow: 0 0 16px 1px rgba(0, 0, 0, .33);
            background: #ddd;
            width: 80vw;
            height: 67vh;
            min-height: unset;
        }

        .form-content {
            border-top-left-radius: var(--border-radius);
            border-bottom-left-radius: var(--border-radius);
            justify-content: center;
            padding-top: 36px;
            padding-bottom: 36px;
        }

        .container {
            margin-left: 64px;
            margin-right: 64px;
        }

        .info-container, .info-container:after {
            border-top-right-radius: var(--border-radius);
            border-bottom-right-radius: var(--border-radius);
        }
    }

    @media (min-width: 1366px) {
        .welcome {
            width: 70vw;
            height: 72vh;
        }

        .form-content {
            padding: 64px 108px;
        }

        .container {
            margin-left: 96px;
            margin-right: 96px;
        }
    }

    @media (min-width: 1600px) {
        .welcome {
            width: 60vw;
        }
    }
</style>