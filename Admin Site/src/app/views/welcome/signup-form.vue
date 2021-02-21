<template>
    <form class="signup-form centred" v-on:submit.prevent="signup">
        <text-input id="signup-username" ref="username" :no-spaces="true"
                    :object="signupData" :required="true" autocomplete="username"
                    icon="person" key-name="username" label="Username"
                    placeholder="JohnDoe123" type="text">
        </text-input>

        <div class="row centred">
            <text-input id="signup-first-name" ref="first-name" :object="signupData"
                        :required="true" autocomplete="given-name" icon="perm_identity"
                        key-name="firstName" label="First Name" placeholder="John" type="text">
            </text-input>

            <text-input id="signup-last-name" ref="last-name" :object="signupData"
                        :required="true" autocomplete="family-name" icon="people"
                        key-name="lastName" label="Last Name" placeholder="Doe" type="text">
            </text-input>
        </div>

        <div class="row centred">
            <text-input id="signup-email" ref="email" :maxlength="255"
                        :no-spaces="true" :object="signupData" :required="true"
                        autocomplete="email" icon="mail" key-name="email"
                        label="Email Address" placeholder="you@mail.co.uk" type="text">
            </text-input>

            <text-input id="signup-phone-number" ref="phone-number" :maxlength="11"
                        :no-spaces="true" :object="signupData" :required="true"
                        autocomplete="tel" icon="phone"
                        key-name="phoneNumber" label="Phone Number"
                        placeholder="07..." type="text">
            </text-input>
        </div>

        <text-input id="signup-password" ref="password" :no-spaces="true"
                    :object="signupData" :required="true" autocomplete="new-password"
                    icon="lock" key-name="password" label="Password"
                    placeholder="Password" type="password">
        </text-input>
        <text-input id="signup-repeat" ref="repeat" :no-spaces="true" :object="signupData"
                    :required="true" icon="repeat" key-name="repeatPassword"
                    label="Repeat Password" placeholder="Repeat Password" type="password">
        </text-input>

        <flat-button text="Sign Up" v-on:click.prevent="signup"></flat-button>
    </form>
</template>

<script>
import TextInput from "../../components/widgets/text-input/text-input.vue";
import FlatButton from "../../components/widgets/buttons/flat-button.vue";
import {toKebabCase} from "../../../assets/scripts/util";

export default {
    name: "signup-form",
    components: {FlatButton, TextInput},
    data() {
        return {
            signupData: {
                username: "",
                firstName: "",
                lastName: "",
                email: "",
                phoneNumber: "",
                password: "",
                repeatPassword: "",
                response: null,
            },
        }
    },
    methods: {
        activate() {
            this.clearInputs();
            this.$refs.username.focus();
        },
        clearInputs() {
            this.signupData.response = null;
            this.$refs.username.clearInput();
            this.$refs["first-name"].clearInput();
            this.$refs["last-name"].clearInput();
            this.$refs.email.clearInput();
            this.$refs["phone-number"].clearInput();
            this.$refs.password.clearInput();
            this.$refs.repeat.clearInput();
        },
        clearSensitiveInputs() {
            this.signupData.response = null;
            this.$refs.password.clearInput();
            this.$refs.repeat.clearInput();
        },
        validInputs() {
            for (let [key, field] of Object.entries(this.signupData)) {
                if (field === "") {
                    return {
                        message: "Please ensure no fields are left blank.",
                        ref: toKebabCase(key),
                    };
                }
            }
            if (this.signupData.username.length < 5) {
                return {
                    message: "Your username must be at least five characters long.",
                    ref: "username"
                };
            }
            if (this.signupData.phoneNumber.length < 11) {
                return {
                    message: "Your phone number is invalid.",
                    ref: "phone-number",
                };
            }
            if (this.signupData.password.length < 5) {
                return {
                    message: "Your password must be at least five characters long.",
                    ref: "password"
                };
            }
            if (this.signupData.password !== this.signupData.repeatPassword) {
                return {
                    message: "The two entered passwords do not match.",
                    ref: "password"
                };
            }
            return {message: "valid", ref: null};
        },
        signup() {
            const messageAndField = this.validInputs();
            if (messageAndField["message"] === "valid") {
                this.$store.dispatch('account/signup', this.signupData).then(r => {
                    if (this.signupData.response !== null) {
                        alert("Sign up failed. " + this.signupData.response);
                        this.clearSensitiveInputs();
                    }
                });
            } else {
                alert("Sign up failed. " + messageAndField["message"]);
                this.clearSensitiveInputs();
                this.$refs[messageAndField["ref"]].clearInput();
            }
        },
    }
}
</script>

<style>
.signup-form, .signup-form .row {
    flex-direction: column;
    width: 100%;
}

.signup-form .text-input {
    margin-bottom: 16px;
}

.signup-form .flat-button, .signup-form .text-input {
    flex: 1;
    width: 100%;
}

@media (max-width: 640px) {
    .signup-form .text-input label {
        color: #FFF !important;
        text-shadow: -1px -1px 0 var(--blue), 1px -1px 0 var(--blue), -1px 1px 0 var(--blue), 1px 1px 0 var(--blue);
    }
}

@media (min-width: 900px) {
    .signup-form .row {
        flex-direction: row;
    }

    .signup-form .row .text-input:first-child {
        margin-right: 16px;
    }
}
</style>