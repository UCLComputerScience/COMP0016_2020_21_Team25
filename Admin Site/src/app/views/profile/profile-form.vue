<template>
    <form class="profile-form centred" v-on:submit.prevent="update">
        <div class="row centred">
            <text-input :object="profileData" autocomplete="given-name"
                        icon="perm_identity" id="profile-first-name" key-name="firstName"
                        label="First Name" placeholder="John" ref="first-name" type="text">
            </text-input>

            <text-input :object="profileData" autocomplete="family-name"
                        icon="people" id="profile-last-name" key-name="lastName"
                        label="Last Name" placeholder="Doe" ref="last-name" type="text">
            </text-input>
        </div>

        <div class="row centred">
            <text-input :maxlength="255" :no-spaces="true" :object="profileData"
                        autocomplete="email" icon="mail"
                        id="profile-email" key-name="email" label="Email Address"
                        placeholder="you@mail.co.uk" ref="email" type="text">
            </text-input>

            <text-input :maxlength="11" :no-spaces="true" :object="profileData"
                        autocomplete="tel" icon="phone"
                        id="profile-phone-number" key-name="phoneNumber"
                        label="Phone Number" placeholder="07..."
                        ref="phone-number" type="text">
            </text-input>
        </div>

        <text-input :no-spaces="true" :object="profileData" icon="lock"
                    id="profile-password" key-name="currentPassword"
                    label="Current Password" placeholder="Current Password"
                    ref="current-password" type="password">
        </text-input>

        <text-input :no-spaces="true" :object="profileData"
                    autocomplete="new-password" icon="lock" id="new-password"
                    key-name="newPassword" label="New Password"
                    placeholder="New Password" ref="new-password" type="password">
        </text-input>
        <text-input :no-spaces="true" :object="profileData" icon="repeat"
                    id="profile-repeat" key-name="repeatPassword" label="Repeat Password"
                    placeholder="Repeat Password" ref="repeat" type="password">
        </text-input>

        <flat-button text="Update Profile" v-on:click.prevent="update"></flat-button>
        <slot></slot>
    </form>
</template>

<script>
    import TextInput from "../../components/widgets/text-input/text-input.vue";
    import FlatButton from "../../components/widgets/buttons/flat-button.vue";
    import backend from "../../../assets/scripts/backend/backend";

    export default {
        name: "profile-form",
        components: {FlatButton, TextInput},
        computed: {
            user() {
                return this.$store.getters["admin/admin"];
            },
            currentPassword() {
                return backend.getPassword(this.user.username);
            }
        },
        data() {
            return {
                profileData: {
                    firstName: "",
                    lastName: "",
                    email: "",
                    phoneNumber: "",
                    currentPassword: "",
                    newPassword: "",
                    repeatPassword: "",
                    response: null,
                },
            }
        },
        created() {
            this.profileData = {
                firstName: this.user.firstName,
                lastName: this.user.lastName,
                email: this.user.email,
                phoneNumber: this.user.phoneNumber,
                currentPassword: "",
                newPassword: "",
                repeatPassword: "",
                response: null,
            }
        },
        methods: {
            activate() {
                this.clearInputs();
                this.$refs["first-name"].focus();
            },
            clearInputs() {
                this.profileData.response = null;
                this.$refs["first-name"].clearInput();
                this.$refs["last-name"].clearInput();
                this.$refs.email.clearInput();
                this.$refs["phone-number"].clearInput();
                this.$refs["current-password"].clearInput();
                this.$refs["new-password"].clearInput();
                this.$refs.repeat.clearInput();
            },
            clearSensitiveInputs() {
                this.profileData.response = null;
                this.$refs["current-password"].clearInput();
                this.$refs["new-password"].clearInput();
                this.$refs.repeat.clearInput();
            },
            validInputs() {
                if (this.profileData.phoneNumber.length < 11) {
                    return {
                        message: "Your phone number is invalid",
                        ref: "phone-number",
                    };
                }
                if (this.profileData.newPassword.length !== 0) {
                    if (this.profileData.currentPassword.length === 0) {
                        return {
                            message: "To change your password, please enter your password" +
                                "in the 'Current Password' field.",
                            ref: "current-password"
                        }

                    }
                    if (this.profileData.newPassword.length < 5) {
                        return {
                            message: "Your new password must be at least five characters long.",
                            ref: "new-password"
                        };
                    }
                    if (this.profileData.newPassword !== this.profileData.repeatPassword) {
                        return {
                            message: "The two entered passwords do not match.",
                            ref: "new-password"
                        };
                    }
                }
                return {message: "valid", ref: null};
            },
            update() {
                const messageAndField = this.validInputs();
                const profileData = {
                    firstName: this.profileData.firstName,
                    lastName: this.profileData.lastName,
                    email: this.profileData.email,
                    phoneNumber: this.profileData.phoneNumber,
                    password: this.profileData.newPassword,
                    response: null,
                }
                if (messageAndField["message"] === "valid") {
                    this.$store.dispatch('admin/profile', profileData).then(r => {
                        if (this.profileData.response !== null) {
                            alert("Update failed. " + profileData.response);
                            this.clearSensitiveInputs();
                        } else {
                            this.$router.go();
                        }
                    });
                } else {
                    alert("Update failed. " + messageAndField["message"]);
                    this.clearSensitiveInputs();
                    this.$refs[messageAndField["ref"]].clearInput();
                }
            },
        }
    }
</script>

<style>
    .profile-form, .profile-form .row {
        flex-direction: column;
        width: 100%;
    }

    .profile-form .text-input {
        margin-bottom: 16px;
    }

    .profile-form .flat-button, .profile-form .text-input {
        flex: 1;
        width: 100%;
    }

    @media (min-width: 900px) {
        .profile-form .row {
            flex-direction: row;
        }

        .profile-form .row .text-input:first-child {
            margin-right: 16px;
        }
    }
</style>