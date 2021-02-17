<template>
    <form class="add-user-form centred" v-on:submit.prevent="submit">
        <text-input
            :object="form"
            autocomplete="given-name"
            icon="perm_identity"
            id="profile-first-name"
            key-name="firstName"
            label="First Name"
            placeholder="John"
            ref="first-name"
            type="text"
        >
        </text-input>

        <text-input
            :object="form"
            autocomplete="family-name"
            icon="people"
            id="profile-last-name"
            key-name="lastName"
            label="Last Name"
            placeholder="Doe"
            ref="last-name"
            type="text"
        >
        </text-input>

        <text-input
            :maxlength="11"
            :no-spaces="true"
            :object="form"
            autocomplete="tel"
            icon="phone"
            id="profile-phone-number"
            key-name="phoneNumber"
            label="Phone Number"
            placeholder="07..."
            ref="phone-number"
            type="text"
        >
        </text-input>

        <dropdown
            :items="prefixes"
            label="Title"
            ref="dropdown"
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
                for (let prefix of prefixes) {
                    items.push({
                        text: prefix,
                        fn: () => {
                            this.form.prefix = prefix;
                        },
                    });
                }
                return items;
            },
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

                if (this.form.phoneNumber.length < 11) {
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
                for (let element of this.$refs) {
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
                this.$nextTick(() => {
                    this.$refs.dropdown.selectByText(text);
                });
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
    }
</style>
