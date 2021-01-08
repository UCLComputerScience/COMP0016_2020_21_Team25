<template>
    <div class="user-details centred">
        <div class="header-container" ref="header">
            <p class="tagline">{{ user.prefix }}</p>
            <h1 class="user-title">{{ title }} Details</h1>
        </div>
        <div class="user-content centred">
            <h2>Profile Picture</h2>
            <div class="user-picture-container image" v-on:click="edit">
                <img :alt="user.firstName" :src="currentPicture"
                     class="user-picture centred">
                <span class="edit-icon centred material-icons">edit</span>
            </div>
            <details-form></details-form>
        </div>
    </div>

    <profile-pic-chooser :data="picData" :fn="updateProfilePic" ref="chooser">
    </profile-pic-chooser>
</template>

<script>
    import ProfilePicChooser from "../../../profile/profile-pic-chooser.vue";
    import DetailsForm from "./details-form.vue";
    import {getName} from "../../../../../assets/scripts/util";

    export default {
        name: "UserDetails",
        components: {DetailsForm, ProfilePicChooser},
        computed: {
            currentPicture() {
                return this.$store.getters["member/profilePicture"];
            },
            title() {
                return getName();
            },
            user() {
                return this.$store.getters["member/activeMember"];
            },
        },
        data() {
            return {
                picData: {
                    selected: this.currentPicture,
                    original: this.currentPicture,
                },
            }
        },
        methods: {
            edit() {
                this.$refs.chooser.show();
            },
            updateProfilePic() {
                this.$store.dispatch("member/updateMemberPic", this.picData.selected);
            }
        }
    }
</script>

<style scoped>
    .user-details {
        justify-content: flex-start;
        padding: 32px;
        max-width: 100%;
        width: 100%;
    }

    .user-details .header-container {
        width: 100%;
        border-bottom: 2px solid var(--header-color);
        margin-bottom: 16px;
    }

    .user-details, .user-content {
        flex-direction: column;
    }

    .user-title {
        margin-top: 0;
        width: 100%;
    }

    .user-picture-container {
        cursor: pointer;
        position: relative;
        margin-bottom: 36px;
    }

    .user-picture-container:hover {
        filter: brightness(65%);
    }

    .user-picture-container:hover .edit-icon {
        display: flex;
    }

    .user-picture-container {
        --border-color: var(--light-blue);
        --border-width: 2;
        border-radius: 50%;
        padding: calc(var(--border-width) * 1px);
        background: var(--border-color);
        width: 256px;
        height: 256px;
    }

    .user-picture {
        border-radius: 50%;
        background: var(--green);
        width: 100%;
        height: 100%;
    }

    .edit-icon {
        font-size: 96px;
        font-weight: 800;
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        color: #fff;
        display: none;
        border-radius: 50%;
    }

    @media (min-width: 900px) {
        .user-content, .header-container {
            max-width: 50%;
        }
    }
</style>