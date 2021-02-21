<template>
    <li :id="id" ref="container" class="user centred" v-on:click="onClick">
        <div class="profile-image image">
            <img :alt="fullName" :src="profilePicture"/>
        </div>
        <p class="name">{{ fullName }}</p>
    </li>
</template>

<script>
import {getProfileImage} from "../../../../../assets/scripts/util";

export default {
    name: "user",
    props: {
        userId: Number,
        fn: {
            type: Function,
            default: (user, el) => {
            },
        },
    },
    computed: {
        data() {
            const allData = this.$store.getters["member/members"];
            return allData[this.userId];
        },
        firstName() {
            return this.data.firstName;
        },
        fullName() {
            return this.firstName + " " + this.lastName;
        },
        id() {
            return `${this.firstName.toLowerCase()}-${this.lastName.toLowerCase()}-${
                this.userId
            }`;
        },
        lastName() {
            return this.data.lastName;
        },
        profilePicture() {
            return getProfileImage(this.data.profilePicture);
        },
    },
    methods: {
        user() {
            return this.data;
        },
        onClick() {
            this.$refs.container.classList.toggle("active-user");
            this.fn(this.data, this.$refs.container);
        },
        activate() {
            this.$refs.container.classList.add("active-user");
        },
        deactivate() {
            this.$refs.container.classList.remove("active-user");
        },
    },
};
</script>

<style scoped>
.user {
    flex-direction: column;
    flex: 1;
    padding: 12px;
    cursor: pointer;
    border-radius: var(--border-radius);
    opacity: 0.7;
    justify-content: flex-start;
}

.user,
.user > * {
    transition: 0.2s ease-in-out all;
}

.user .profile-image {
    border-radius: 50%;
    background: #D9D9D9;
    border: 2px solid #D9D9D9;
    width: 16vh;
    height: 16vh;
    overflow: hidden;
}

.user .name {
    margin: 12px 0 0;
    text-overflow: ellipsis;
    font-size: calc(0.75rem + 0.5vw);
    overflow: hidden;
    text-align: center;
    color: #D9D9D9;
    text-transform: capitalize;
}

@media (pointer: fine) {
    .user {
        opacity: unset;
    }

    .user:hover {
        background: var(--pale-blue);
        opacity: unset;
    }

    .user .name {
        color: #2A2B30;
    }

    .user:hover .name {
        color: var(--blue);
    }
}

.active-user {
    pointer-events: none;
}

.active-user .profile-image {
    width: 60px;
    height: 60px;
    border: 2px solid var(--blue);
}

.active-user .name {
    color: #2A2B30;
}
</style>