<template>
    <div class="details-form centred">
        <add-user-form ref="form" :form="form">
            <flat-button
                class="details-button"
                style="margin-bottom: 12px"
                text="Update Member"
                v-on:click.prevent="updateMember"
            ></flat-button>
            <flat-button
                class="details-button"
                style="--button-bg: var(--red)"
                text="Delete Member"
                v-on:click.prevent="removeMember"
            ></flat-button>
        </add-user-form>
    </div>
</template>

<script>
import FlatButton from "../../../../components/widgets/buttons/flat-button.vue";
import AddUserForm from "../../sidebar/dialog/add-user-form.vue";

export default {
    components: { AddUserForm, FlatButton },
    name: "details-form",
    computed: {
        member() {
            return this.$store.getters["member/activeMember"];
        },
        userId() {
            return this.$store.getters["member/activeId"];
        },
        fullName() {
            return this.member["first-name"] + " " + this.member["last-name"];
        },
    },
    data() {
        return {
            form: {
                firstName: "",
                lastName: "",
                prefix: "",
                phoneNumber: "",
            },
        };
    },
    watch: {
        $route(to, from) {
            const person = to.params.person;
            if (person !== undefined && person !== null && this.member !== undefined) {
                this.updateDefault();
            }
        },
    },
    methods: {
        confirm() {

        },
        updateDefault() {
            this.form = {
                firstName: this.member["first-name"],
                lastName: this.member["last-name"],
                prefix: this.member.prefix,
                phoneNumber: this.member["phone-number"],
            };
            if (this.$refs.form !== null) {
                this.$refs.form.select(this.member.prefix);
            }
        },
        checkForm(form) {
            const length = form.phoneNumber.length;
            if (length > 0 && length !== 11) {
                return {
                    message: "The entered phone number is invalid.",
                    ref: "phone-number",
                };
            }
            return {
                message: "valid",
                ref: null,
            };
        },
        async updateMember() {
            const form = this.setData();
            const messageAndField = this.checkForm(form);
            if (messageAndField["message"] === "valid") {
                await this.$store.dispatch("member/updateMember", form);
                this.form.success = form.success;
                this.form.response = form.response;
                if (form.success) {
                    alert("Member data successfully updated.");
                    this.form = Object.assign({}, form);
                    this.updateRoute();
                } else {
                    alert("Update failed. " + form.response);
                    this.$refs.form.clear();
                }
            } else {
                this.form.success = false;
                this.form.response = messageAndField["message"];
                alert("Update failed. " + messageAndField["message"]);
                if (messageAndField["ref"] !== null)
                    this.$refs.form.failed(messageAndField["ref"]);
            }
        },
        setData() {
            return {
                id: this.$store.getters["member/activeId"],
                profilePicture: this.member["profile-picture"],
                response: null,
                success: false,
                ...this.form
            };
        },
        updateRoute() {
            const name = this.form["firstName"] + " " + this.form["lastName"];
            const person = name.replace(/[ ]/g, "-").toLowerCase();
            this.$router.go();
            this.$router.replace({
                name: "user-details",
                params: {
                    person: person,
                },
            });
        },
        async removeMember() {
            const message = `Are you sure you want to remove ${this.fullName} from your circle? They will no longer have access to the Concierge app and all stored data will be purged. This action cannot be undone.`;
            if (confirm(message)) {
                await this.$store.dispatch("member/removeMember", this.userId);
            }
        },
    },
    mounted() {
        this.updateDefault();
    },
};
</script>

<style>
.details-form {
    width: unset;
}

.details-form .add-user-form {
    width: 100%;
}

.details-form .add-user-form > * {
    width: 100%;
}

.details-form .details-button {
    flex: 1;
    width: 100%;
}

@media (min-width: 800px) {
    .details-form {
        width: 75%;
    }
}

@media (min-width: 920px) {
    .details-form {
        width: 65%;
    }
}
</style>
