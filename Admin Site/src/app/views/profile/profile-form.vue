<template>
    <form class="profile-form centred" v-on:submit.prevent="update">
        <div class="row centred">
            <text-input id="profile-first-name" ref="first-name"
                        :object="profileData" :placeholder="user['first-name']" autocomplete="given-name"
                        icon="perm_identity" key-name="firstName" label="First Name" type="text">
            </text-input>

            <text-input id="profile-last-name" ref="last-name"
                        :object="profileData" :placeholder="user['last-name']" autocomplete="family-name"
                        icon="people" key-name="lastName" label="Last Name" type="text">
            </text-input>
        </div>

        <div class="row centred">
            <text-input id="profile-email" ref="email" :maxlength="255"
                        :no-spaces="true" :object="profileData"
                        :placeholder="user['email']" autocomplete="email" icon="mail"
                        key-name="email" label="Email Address" type="text">
            </text-input>

            <text-input id="profile-phone-number" ref="phone-number" :maxlength="11"
                        :no-spaces="true" :object="profileData"
                        :placeholder="user['phone-number']" autocomplete="tel"
                        icon="phone" key-name="phoneNumber"
                        label="Phone Number" type="text">
            </text-input>
        </div>

        <text-input id="profile-password" ref="current-password" :no-spaces="true"
                    :object="profileData" icon="lock"
                    key-name="currentPassword" label="Current Password"
                    placeholder="Current Password" type="password">
        </text-input>

        <text-input id="new-password" ref="new-password"
                    :no-spaces="true" :object="profileData" autocomplete="new-password"
                    icon="lock" key-name="newPassword"
                    label="New Password" placeholder="New Password" type="password">
        </text-input>
        <text-input id="profile-repeat" ref="repeat" :no-spaces="true"
                    :object="profileData" icon="repeat" key-name="repeatPassword"
                    label="Repeat Password" placeholder="Repeat Password" type="password">
        </text-input>

        <flat-button text="Update Profile" v-on:click.prevent="update"></flat-button>
        <slot></slot>
    </form>
</template>

<script>
import FlatButton from "../../components/widgets/buttons/flat-button.vue";
import TextInput from "../../components/widgets/text-input/text-input.vue";

export default {
    name: "profile-form",
    components: { FlatButton, TextInput },
    computed: {
        user() {
            return this.$store.getters["admin/admin"];
        },
        currentPassword() {
            return this.$store.getters["admin/admin"].password;
        },
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
                response: "",
                success: false
            },
        };
    },
    created() {
        this.reset();
    },
    methods: {
        reset() {
            this.profileData.firstName = this.user["first-name"];
            this.profileData.lastName = this.user["last-name"];
            this.profileData.email = this.user.email;
            this.profileData.phoneNumber = this.user["phone-number"];
            this.profileData.currentPassword = "";
            this.profileData.newPassword = "";
            this.profileData.repeatPassword = "";
        },
        activate() {
            this.clearInputs();
            this.$refs["first-name"].focus();
        },
        clearInputs() {
            this.$refs["first-name"].clearInput();
            this.$refs["last-name"].clearInput();
            this.$refs.email.clearInput();
            this.$refs["phone-number"].clearInput();
            this.$refs["current-password"].clearInput();
            this.$refs["new-password"].clearInput();
            this.$refs.repeat.clearInput();
        },
        clearSensitiveInputs() {
            this.$refs["current-password"].clearInput();
            this.$refs["new-password"].clearInput();
            this.$refs.repeat.clearInput();
        },
        validInputs() {
            const length = this.profileData.phoneNumber.length;
            if (length !== 0 && length !== 11) {
                return {
                    message: "Your phone number is invalid",
                    ref: "phone-number",
                };
            }
            if (this.profileData.newPassword.length !== 0) {
                if (this.profileData.currentPassword.length === 0) {
                    return {
                        message:
                            "To change your password, please enter your password" +
                            " in the 'Current Password' field.",
                        ref: "current-password",
                    };
                }
                if (this.profileData.newPassword.length < 5) {
                    return {
                        message:
                            "Your new password must be at least five characters long.",
                        ref: "new-password",
                    };
                }
                if (
                    this.profileData.newPassword !==
                    this.profileData.repeatPassword
                ) {
                    return {
                        message: "The two entered passwords do not match.",
                        ref: "new-password",
                    };
                }
                if (this.profileData.currentPassword !== this.currentPassword) {
                    return {
                        message: "Your password is incorrect. To change your password, you must correctly enter your current password.",
                        ref: "current-password",
                    };
                }
            }
            return { message: "valid", ref: null };
        },
        async update() {
            const messageAndField = this.validInputs();
            this.profileData.success = false;
            const profileData = {
                firstName: this.profileData.firstName,
                lastName: this.profileData.lastName,
                email: this.profileData.email,
                phoneNumber: this.profileData.phoneNumber,
                password: this.profileData.newPassword,
                response: null,
                success: false
            };
            if (messageAndField["message"] === "valid") {
                await this.$store.dispatch("admin/profile", profileData);
                this.profileData.success = profileData.success;
                this.profileData.response = profileData.response
                if (profileData.success) {
                    alert("Update successful, " + profileData["firstName"] + ".");
                    this.profileData = { ...profileData };
                    await this.$store.dispatch("admin/fetchAdmin", this.$route.params.username);
                    this.reset();
                } else {
                    alert("Update failed. " + profileData.response);
                    this.clearSensitiveInputs();
                }
            } else {
                this.profileData.success = false;
                this.profileData.response = messageAndField["message"];
                alert("Update failed. " + messageAndField["message"]);
                this.clearSensitiveInputs();
                this.$refs[messageAndField["ref"]].clearInput();
            }
        },
    },
};
</script>

<style>
.profile-form,
.profile-form .row {
    flex-direction: column;
    width: 100%;
}

.profile-form .text-input {
    margin-bottom: 16px;
}

.profile-form .flat-button,
.profile-form .text-input {
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