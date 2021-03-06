<template>
    <li v-show="!hasService" :id="id" ref="container" class="user centred" v-on:click="onClick">
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
        userId: String,
        fn: {
            type: Function,
            default: (user, el) => {
            },
        },
    },
    data() {
        return {
            hasService: false,
        };
    },
    computed: {
        data() {
            const allData = this.$store.getters["member/members"];
            return allData[this.userId];
        },
        firstName() {
            return this.data["first-name"];
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
            return this.data["last-name"];
        },
        profilePicture() {
            return getProfileImage(this.data["profile-picture"]);
        },
    },
    created() {
        if (this.$route.name === "service") {
            this.setHasService();
        }
    },
    methods: {
        async setHasService() {
            await this.$store.dispatch(
                "member/fetchMemberServices",
                this.userId
            );
            const memberServices = [
                ...this.$store.getters["member/memberServices"],
            ];
            const activeService = this.$route.params["service-id"];
            for (const service of memberServices) {
                if (service["service-id"] === activeService) {
                    this.hasService = true;
                    break;
                }
            }
        },
        user() {
            return { id: this.userId, ...this.data };
        },
        onClick() {
            this.$refs.container.classList.toggle("active-user");
            this.fn({ id: this.userId, ...this.data }, this.$refs.container);
        },
        activate() {
            if (this.$refs.container !== undefined)
                this.$refs.container.classList.add("active-user");
        },
        deactivate() {
            if (this.$refs.container !== undefined)
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