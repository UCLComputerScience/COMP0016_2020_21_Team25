<template>
    <form class="login-form centred">
        <text-input :maxlength="255" :no-spaces="true" :on-enter="login"
                    :variable="loginData.usernameOrEmail" autocomplete="username"
                    id="login-username-or-email" label="Username or Email"
                    placeholder="Username or email address" ref="username"
                    type="text">
        </text-input>

        <text-input :no-spaces="true" :on-enter="login" :variable="loginData.password"
                    autocomplete="current-password" id="login-password"
                    label="Password" placeholder="Password" ref="password"
                    type="password">
        </text-input>

        <flat-button text="Log In" v-on:click="login"></flat-button>
    </form>
</template>

<script>
    import TextInput from "../../components/widgets/text-input/text-input.vue";
    import FlatButton from "../../components/widgets/buttons/flat-button.vue";

    export default {
        name: "login-form",
        components: {FlatButton, TextInput},
        data() {
            return {
                loginData: {
                    usernameOrEmail: "",
                    password: ""
                }
            }
        },
        methods: {
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
                    this.$store.dispatch('welcome', {
                        usernameOrEmail: this.loginData.usernameOrEmail,
                        password: this.loginData.password
                    });
                } else {
                    this.$refs.password.clearInput();
                }
            },
        }
    }
</script>

<style scoped>
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
</style>