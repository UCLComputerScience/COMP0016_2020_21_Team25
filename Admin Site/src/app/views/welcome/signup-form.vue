<template>
    <form class="signup-form centred">
        <text-input :no-spaces="true" :on-enter="signup"
                    :variable="signupData.username" id="signup-username"
                    label="Username" placeholder="JohnDoe123" ref="username"
                    type="text" autocomplete="username">
        </text-input>

        <div class="row centred">
            <text-input :on-enter="signup" :variable="signupData.firstName"
                        id="signup-first-name" label="First Name" placeholder="John"
                        ref="first-name" type="text" autocomplete="given-name">
            </text-input>

            <text-input :on-enter="signup" :variable="signupData.lastName"
                        id="signup-last-name" label="Last Name" placeholder="Doe"
                        ref="last-name" type="text" autocomplete="family-name">
            </text-input>
        </div>

        <div class="row centred">
            <text-input :maxlength="255" :no-spaces="true" :on-enter="signup"
                        :variable="signupData.email" id="signup-email"
                        label="Email Address" placeholder="you@mail.co.uk" ref="email"
                        type="text" autocomplete="email">
            </text-input>

            <text-input :maxlength="11" :no-spaces="true" :on-enter="signup"
                        :variable="signupData.phoneNumber" id="signup-phone-number"
                        label="Phone Number" placeholder="07..." ref="phone-number"
                        type="text" autocomplete="tel">
            </text-input>
        </div>

        <text-input :no-spaces="true" :on-enter="signup"
                    :variable="signupData.password" id="signup-password"
                    label="Password" placeholder="Password" ref="password"
                    type="password" autocomplete="new-password">
        </text-input>
        <text-input :no-spaces="true" :on-enter="signup" :variable="repeatPassword"
                    id="signup-repeat" label="Repeat Password"
                    placeholder="Repeat Password" ref="repeat" type="password">
        </text-input>

        <flat-button text="Log In" v-on:click="signup"></flat-button>
    </form>
</template>

<script>
    import TextInput from "../../components/widgets/text-input/text-input.vue";
    import FlatButton from "../../components/widgets/buttons/flat-button.vue";

    export default {
        name: "signup-form",
        components: {FlatButton, TextInput},
        data() {
            return {
                signupData: {
                    username: "",
                    password: "",
                    email: "",
                    firstName: "",
                    lastName: "",
                    phoneNumber: ""
                },
                repeatPassword: "",
            }
        },
        methods: {
            clearInputs() {
                this.$refs.username.clearInput();
                this.$refs.firstName.clearInput();
                this.$refs.lastName.clearInput();
                this.$refs.email.clearInput();
                this.$refs.phoneNumber.clearInput();
                this.$refs.password.clearInput();
                this.$refs.repeat.clearInput();
            },
            clearSensitiveInputs() {
                this.$refs.password.clearInput();
                this.$refs.repeat.clearInput();
            },
            validInputs() {
                for (let field of this.signupData) {
                    if (field === "") {
                        return "Please ensure no fields are left blank.";
                    }
                }
                if (this.signupData.username.length < 5) {
                    return "Your username must be at least five characters long.";
                }
                if (this.signupData.phoneNumber.length < 11) {
                    return "Your phone number is invalid";
                }
                if (this.signupData.password.length < 5) {
                    return "Your password must be at least five characters long.";
                }
                if (this.signupData.password !== this.repeatPassword) {
                    return "The two entered passwords do not match";
                }
                return "valid"
            },
            signup() {
                const message = this.validInputs();
                if (message === "valid") {
                    this.$store.dispatch('signup', {
                        username: this.signupData.username,
                        password: this.signupData.password,
                        email: this.signupData.email,
                        firstName: this.signupData.firstName,
                        lastName: this.signupData.lastName,
                        phoneNumber: this.signupData.phoneNumber
                    });
                } else {
                    alert("Sign up failed. " + message);
                    this.clearSensitiveInputs();
                }
            },
        }
    }
</script>

<style scoped>
    .signup-form, .row {
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

    @media (min-width: 900px) {
        .row {
            flex-direction: row;
        }

        .row .text-input:first-child {
            margin-right: 16px;
        }
    }
</style>