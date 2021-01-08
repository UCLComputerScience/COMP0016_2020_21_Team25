<template>
    <li :id="id" class="user centred" ref="container" v-on:click="onClick">
        <div class="profile-image image">
            <img :alt="fullName" :src="profilePicture">
        </div>
        <p class="name">{{ fullName }}</p>
    </li>
</template>

<script>
    export default {
        name: "user",
        props: {
            data: Object, fn: {
                type: Function, default: (user, el) => {
                }
            },
        },
        computed: {
            firstName() {
                return this.data.firstName;
            },
            fullName() {
                return this.firstName + " " + this.lastName;
            },
            id() {
                return `${this.firstName.toLowerCase()}-${this.lastName.toLowerCase()}-${this.userId}`
            },
            lastName() {
                return this.data.lastName;
            },
            userId() {
                return this.data.id;
            },
            profilePicture() {
                const activeId = this.$store.getters['member/activeId'];
                if (activeId === this.userId) {
                    return this.$store.getters['member/profilePicture'];
                }
                return this.data.profilePicture;
            }
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
            }
        }
    }
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

    .user, .user > * {
        transition: .2s ease-in-out all;
    }

    .user .profile-image {
        border-radius: 50%;
        background: #d9d9d9;
        border: 2px solid #d9d9d9;
        width: 16vh;
        height: 16vh;
        overflow: hidden;
    }

    .user .name {
        margin: 12px 0 0;
        text-overflow: ellipsis;
        font-size: calc(.75rem + .5vw);
        overflow: hidden;
        text-align: center;
        color: #d9d9d9;
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
            color: #2a2b30;
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
        color: #2a2b30;
    }
</style>