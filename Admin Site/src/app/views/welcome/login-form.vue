<template>
    <form class="login-form centred" v-on:submit.prevent="login">
        <text-input :maxlength="255" :no-spaces="true" :object="loginData"
                   autocomplete="username" id="login-username-or-email"
                    key-name="usernameOrEmail" label="Username or Email"
                    placeholder="Username or email address" ref="username"
                    type="text" icon="person">
        </text-input>

        <text-input :no-spaces="true" :object="loginData"
                    autocomplete="current-password" id="login-password"
                    key-name="password" label="Password" placeholder="Password"
                    ref="password" type="password" icon="lock">
        </text-input>

        <v-link class="inline-link" href="/forgot" text="Forgot Password?"></v-link>

        <flat-button text="Log In" v-on:click.prevent="login"></flat-button>
    </form>
</template>

<script>
    import TextInput from "../../components/widgets/text-input/text-input.vue";
    import FlatButton from "../../components/widgets/buttons/flat-button.vue";
    import VLink from "../../components/widgets/buttons/v-link.vue";

    export default {
        name: "login-form",
        components: {VLink, FlatButton, TextInput},
        data() {
            return {
                loginData: {
                    usernameOrEmail: "",
                    password: ""
                }
            }
        },
        methods: {
            activate() {
                this.clearInputs();
                this.$refs.username.focus();
            },
            clearInputs() {
                this.$refs.username.clearInput();
                this.$refs.password.clearInput();
            },
            validInputs() {
                if (this.loginData.usernameOrEmail === "" ||
                    this.loginData.password === "") {
                    return "invalid";
                }
                return "valid";
            },
            login() {
                const message = this.validInputs();
                if (message === "valid") {
                    this.$store.dispatch('login', {
                        usernameOrEmail: this.loginData.usernameOrEmail,
                        password: this.loginData.password
                    });
                } else {
                    this.$refs.password.clearInput();
                    this.$refs.username.focus();
                }
            },
        }
    }
</script>

<style>
    .login-form {
        flex-direction: column;
        width: 100%;
    }

    .text-input {
        margin-bottom: 16px;
    }

    .flat-button, .text-input {
        flex: 1;
        width: 100%;
    }

    .login-form a {
        margin-left: auto;
        color: var(--text-color);
        margin-top: 2px;
        margin-bottom: 16px;
    }

    @media (max-width: 640px) {
        .text-input label {
            color: #fff !important;
        }
    }
</style>