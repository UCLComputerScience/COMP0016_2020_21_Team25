<template>
    <div :style="'--border-color: ' + borderColor
    + '; --border-width: ' + borderWidth"
         class="profile-picture-container image">
        <img :alt="name" :src="profilePicture" class="profile-picture centred">
        <slot></slot>
    </div>
</template>

<script>
import {getProfileImage} from '../../../../assets/scripts/util';

export default {
    name: "profile-picture",
    props: {
        borderColor: { type: String, default: "var(--light-blue)" },
        borderWidth: { type: Number, default: 2 },
    },
    computed: {
        profilePicture() {
            return getProfileImage(this.admin["profile-picture"]);
        },
        name() {
            return this.admin["first-name"];
        },
        admin() {
            return this.$store.getters["admin/admin"];
        }
    }
};
</script>

<style scoped>
.profile-picture-container {
    --border-color: var(--light-blue);
    --border-width: 2;
    border-radius: 50%;
    padding: calc(var(--border-width) * 1px);
    background: var(--border-color);
    width: 256px;
    height: 256px;
}

.profile-picture {
    border-radius: 50%;
    background: var(--green);
    width: 100%;
    height: 100%;
}
</style>