<template>
    <div class="add-user-dialog">
        <modal-dialog ref="dialog">
            <h2 class="header">Add new user</h2>
            <p class="description">Fill out the details below to add a
                new user to your circle. You can edit these details at any time.</p>
            <flat-button class="profile-pic-button"
                         text="Select Profile Picture" v-on:click="openProfilePicture"></flat-button>
            <add-user-form ref="form" :form="form">
            </add-user-form>
            <div class="button-group centred">
                <flat-button class="confirm" text="Confirm" v-on:click="confirm">
                </flat-button>
                <flat-button class="cancel" style="--button-bg: var(--red)"
                             text="Cancel" v-on:click="cancel"></flat-button>
            </div>
        </modal-dialog>
    </div>
    <profile-pic-chooser ref="chooser" :data="form" :fn="setProfilePicture"></profile-pic-chooser>
</template>


<script>
import FlatButton from '../../../../components/widgets/buttons/flat-button.vue';
import ModalDialog from '../../../../components/widgets/containers/modal-dialog.vue';
import ProfilePicChooser from '../../../profile/profile-pic-chooser.vue';
import AddUserForm from './add-user-form.vue';

export default {
    name: "add-user-dialog",
    components: { AddUserForm, FlatButton, ModalDialog, ProfilePicChooser },
    computed: {},
    data() {
        return {
            form: {
                firstName: "",
                lastName: "",
                phoneNumber: "",
                prefix: "",
                profilePicture: null,
                original: null,
                response: null,
                success: false
            }
        };
    },
    methods: {
        openProfilePicture() {
            this.$refs.chooser.show();
        },
        setProfilePicture() {
            this.form.profilePicture = this.form.selected;
            this.form.original = this.form.selected;
            this.$refs.dialog.show();
        },
        show() {
            this.reset();
            this.$refs.dialog.show();
        },
        checkForm() {
            return this.$refs.form.checkForm();
        },
        async confirm() {
            const messageAndField = this.checkForm();
            if (messageAndField["message"] === "valid") {
                await this.$store.dispatch("member/addMember", this.form);
                if (this.form.success) {
                    this.$refs.dialog.confirm();
                } else {
                    alert("Creation failed. " + this.form.response);
                    this.$refs.form.clear();
                }
            } else {
                this.form.success = false;
                this.form.response = messageAndField["message"];
                alert("Creation failed. " + messageAndField["message"]);
                if (messageAndField["ref"] !== null)
                    this.$refs.form.failed(messageAndField["ref"]);
            }
        },
        cancel() {
            this.reset();
            this.$refs.dialog.cancel();
        },
        reset() {
            this.form = {
                firstName: "",
                lastName: "",
                phoneNumber: "",
                prefix: "",
                profilePicture: null,
                original: null,
                response: null,
                success: false,
            };
        }
    },
};
</script>

<style scoped>
.description {
    color: rgba(0, 0, 0, .67);
    text-align: center;
    margin-top: 0;
    margin-bottom: 24px;
    font-weight: 400;
    font-size: 18px;
}

.profile-pic-button {
    margin-bottom: 24px;
}

form, .button-group, .confirm, .cancel, .profile-pic-button {
    width: 100%;
}

.button-group {
    flex-direction: column;
}

.confirm {
    margin-bottom: 12px;
}

.confirm, .cancel {
    flex: 1;
}

@media (min-width: 620px) {
    .button-group, .profile-pic-button, .description, form {
        width: 50%;
        max-width: 50%;
    }

    .button-group {
        flex-direction: row;
    }

    .confirm {
        margin-bottom: 0;
        margin-right: 12px;
    }

    .cancel {
        margin-left: 12px;
    }
}
</style>