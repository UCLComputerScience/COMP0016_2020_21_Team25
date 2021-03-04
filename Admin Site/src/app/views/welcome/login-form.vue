<template>
    <form class="login-form centred" v-on:submit.prevent="login">
        <text-input
            id="login-username-or-email"
            ref="username"
            :maxlength="255"
            :no-spaces="true"
            :object="loginData"
            autocomplete="username"
            icon="person"
            key-name="usernameOrEmail"
            label="Username"
            placeholder="Username"
            type="text"
        >
        </text-input>

        <text-input
            id="login-password"
            ref="password"
            :no-spaces="true"
            :object="loginData"
            autocomplete="current-password"
            icon="lock"
            key-name="password"
            label="Password"
            placeholder="Password"
            type="password"
        >
        </text-input>

        <flat-button text="Log In" v-on:click.prevent="login"></flat-button>
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
                password: "",
                response: null,
            },
        };
    },
    methods: {
        activate() {
            this.clearInputs();
            this.$refs.username.focus();
        },
        clearInputs() {
            this.loginData.response = null;
            this.$refs.username.clearInput();
            this.$refs.password.clearInput();
        },
        clearSensitiveInputs() {
            this.loginData.response = null;
            this.$refs.password.clearInput();
            this.$refs.username.focus();
        },
        validInputs() {
            if (
                this.loginData.usernameOrEmail === "" ||
                this.loginData.password === ""
            ) {
                return "invalid";
            }
            return "valid";
        },
        async login() {
            const message = this.validInputs();
            if (message === "valid") {
                await this.$store.dispatch("account/login", this.loginData);
                if (this.loginData.response !== null) {
                    alert("Login failed. " + this.loginData.response);
                    this.clearSensitiveInputs();
                }
            } else {
                this.clearSensitiveInputs();
            }
        },
    },
};
</script>

<style>
.login-form {
    flex-direction: column;
    width: 100%;
}

.login-form .text-input {
    margin-bottom: 16px;
}

.login-form .flat-button,
.login-form .text-input {
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
    .login-form .text-input label {
        color: #FFF !important;
    }
}
</style>