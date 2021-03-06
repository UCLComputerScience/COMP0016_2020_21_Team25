<template>
    <div>
        <welcome-card></welcome-card>
        <page>
            <div class="profile centred">
                <div ref="header" class="header-container">
                    <p class="tagline">Update your details</p>
                    <h1 class="profile-title">My Profile</h1>
                </div>
                <p ref="subtitle" class="profile-subtitle">Here you can keep your
                    profile up-to-date by filling out the fields below. If you wish to
                    change a field, enter the new value in the corresponding input,
                    otherwise leave the default value unchanged. To change your
                    profile picture, click your current one below. To change your
                    password, you must first enter your current password.</p>
                <div class="profile-content centred">
                    <h2>Profile Picture</h2>
                    <profile-picture v-on:click="edit">
                        <span class="edit-icon centred material-icons">edit</span>
                    </profile-picture>
                    <profile-form>
                        <flat-button class="logout-button"
                                     style="--button-bg: var(--red)" text="Sign out"
                                     v-on:click.prevent="logout"></flat-button>
                    </profile-form>
                </div>
            </div>
            <footer-logo></footer-logo>
        </page>
    </div>

    <profile-pic-chooser ref="chooser" :data="picData" :fn="updateProfilePic">
    </profile-pic-chooser>
</template>

<script>
import Page from "../../components/layout/Page.vue";
import FlatButton from "../../components/widgets/buttons/flat-button.vue";
import LogoutButton from "../../components/widgets/buttons/logout-button.vue";
import FooterLogo from "../../components/widgets/misc/footer-logo.vue";
import ProfilePicture from "../../components/widgets/misc/profile-picture.vue";
import WelcomeCard from "../welcome/welcome-card.vue";
import ProfileForm from "./profile-form.vue";
import ProfilePicChooser from "./profile-pic-chooser.vue";

export default {
    name: "Profile",
    components: {
        ProfilePicChooser, FlatButton, LogoutButton, ProfilePicture,
        ProfileForm, FooterLogo, Page, WelcomeCard
    },
    computed: {
        currentPicture() {
            return this.$store.getters["admin/admin"]["profile-picture"];
        }
    },
    data() {
        return {
            picData: {
                selected: this.currentPicture,
                original: this.currentPicture,
            }
        };
    },
    methods: {
        logout() {
            if (confirm("Are you sure you want to sign out? You'll need to sign" +
                " in again."))
                this.$store.dispatch("account/logout");
        },
        edit() {
            this.$refs.chooser.show();
        },
        updateProfilePic() {
            this.$store.dispatch("admin/updateAdminPic", this.picData.selected);
        }
    },
};
</script>

<style scoped>
.profile {
    justify-content: flex-start;
    padding: 32px;
    max-width: 100%;
}

.profile, .profile .header-container, .profile, .profile-title, .profile-content {
    width: 100%;
}

.profile, .profile-content {
    flex-direction: column;
}

.profile-title {
    margin-top: 0;
}

.profile-subtitle {
    margin-top: 0;
    font-size: 18px;
    text-align: left;
}

.profile-picture-container {
    cursor: pointer;
    position: relative;
    margin-bottom: 36px;
}

.profile-picture-container:hover {
    filter: brightness(65%);
}

.edit-icon {
    font-size: 96px;
    font-weight: 800;
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    color: #FFF;
    display: none;
    border-radius: 50%;
}

.profile-picture-container:hover .edit-icon {
    display: flex;
}

.logout-button {
    margin-top: 16px;
}

@media (min-width: 900px) {
    .profile-content, .profile .header-container, .profile-subtitle {
        max-width: 66%;
    }
}
</style>