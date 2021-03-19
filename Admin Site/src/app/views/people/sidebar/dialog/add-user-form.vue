<template>
    <form class="add-user-form centred" v-on:submit.prevent="submit">
        <text-input
            id="member-first-name"
            ref="first-name"
            :object="form"
            autocomplete="given-name"
            icon="perm_identity"
            key-name="firstName"
            label="First Name"
            :placeholder="firstName"
            type="text"
        >
        </text-input>

        <text-input
            id="member-last-name"
            ref="last-name"
            :object="form"
            autocomplete="family-name"
            icon="people"
            key-name="lastName"
            label="Last Name"
            :placeholder="lastName"
            type="text"
        >
        </text-input>

        <text-input
            id="member-phone-number"
            ref="phone-number"
            :maxlength="11"
            :no-spaces="true"
            :object="form"
            autocomplete="tel"
            icon="phone"
            key-name="phoneNumber"
            label="Phone Number"
            :placeholder="phoneNumber"
            type="text"
        >
        </text-input>

        <dropdown
            ref="dropdown"
            :items="prefixes"
            label="Title"
            title="Select a title"
        ></dropdown>
        <slot></slot>
    </form>
</template>

<script>
import Dropdown from "../../../../components/widgets/misc/dropdown/dropdown.vue";
import TextInput from "../../../../components/widgets/text-input/text-input.vue";

export default {
    name: "add-user-form",
    components: { TextInput, Dropdown },
    props: { form: Object },
    computed: {
        prefixes() {
            const prefixes = ["Mr", "Ms", "Mrs", "Mx", "Miss", "Dr", "Prof", "Sir"];
            const items = [];
            for (const prefix of prefixes) {
                items.push({
                    text: prefix,
                    fn: () => {
                        this.form.prefix = prefix;
                    },
                });
            }
            return items;
        },
        firstName() {
            if (this.form["firstName"] !== undefined) {
                return this.form["firstName"];
            }
            return "John";
        },
        lastName() {
            if (this.form["lastName"] !== undefined) {
                return this.form["lastName"];
            }
            return "Doe";
        },
        phoneNumber() {
            if (this.form["phoneNumber"] !== undefined) {
                return this.form["phoneNumber"];
            }
            return "07...";
        }
    },
    methods: {
        checkForm() {
            if (this.form.profilePicture === null) {
                return {
                    message: "Please select a profile picture.",
                    ref: null,
                };
            }
            if (this.form.firstName === "") {
                return {
                    message: "Please enter a first name.",
                    ref: "first-name",
                };
            }
            if (this.form.lastName === "") {
                return {
                    message: "Please enter a last name.",
                    ref: "last-name",
                };
            }
            if (this.form.phoneNumber === "") {
                return {
                    message: "Please enter a phone number.",
                    ref: "phone-number",
                };
            }

            if (this.form.phoneNumber.length !== 11) {
                return {
                    message: "The entered phone number is invalid.",
                    ref: "phone-number",
                };
            }

            if (this.form.prefix === "") {
                return {
                    message: "Please select a prefix.",
                    ref: null,
                };
            }
            return {
                message: "valid",
                ref: null,
            };
        },
        clear() {
            for (const element of Object.values(this.$refs)) {
                element.clearInput();
            }
            this.$refs["first-name"].focus();
        },
        submit() {
            this.$parent.confirm();
        },
        failed(ref) {
            this.$refs[ref].clearInput();
        },
        select(text) {
            this.$refs.dropdown.selectByText(text);
        },
    },
};
</script>

<style scoped>
.add-user-form {
    flex-direction: column;
}

.add-user-form > * {
    margin-bottom: 24px;
    width: 100%;
}
</style>
