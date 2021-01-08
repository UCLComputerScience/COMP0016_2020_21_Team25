<template>
    <admin-circle :fn="go" class="people-carousel" ref="people">
        <slot></slot>
    </admin-circle>
</template>

<script>
    import AdminCircle from "../../../components/widgets/app/admin-circle/admin-circle.vue";

    export default {
        name: "sidebar-content",
        components: {AdminCircle},
        computed: {
            activeMember() {
                return this.$store.getters["member/activeMember"];
            },
        },
        watch: {
            $route(to, from) {
                if (to.params.person !== undefined || to.name === "people") {
                    this.$nextTick(() => {
                        this.toggle();
                    });
                }
            },
            activeMember: {
                handler() {
                    this.toggle();
                },
                deep: true,
            }
        },
        methods: {
            go(user, el) {
                this.$store.dispatch('member/activeMember', user.id).then(r => {
                    const person = user.firstName + " " + user.lastName;
                    const param = person.replace(" ", "-").toLowerCase();
                    this.$router.push({name: "user-details", params: {person: param}});
                });
            },
            toggle() {
                const elements = this.$refs.people.elements();
                for (let el of elements) {
                    if (this.$route.params.person !== undefined
                        && this.$route.name !== "people" &&
                        el.user().id === this.activeMember.id) {
                        el.activate();
                    } else {
                        el.deactivate();
                    }
                }
            }
        },
        mounted() {
            this.toggle();
        },
    }
</script>

<style>
    .people-carousel {
        flex-direction: column;
        padding: 18px !important;
        min-width: var(--max-sidebar-width);
        position: relative;
        margin: 0;
    }

    .people-carousel > *:not(:last-child) {
        margin-right: 0 !important;
    }

    .people-carousel .user {
        flex-direction: row;
        padding: 6px !important;
        border-radius: 10px;
    }

    .people-carousel .user .profile-image {
        width: 2em;
        height: 2em;
        margin-right: 8px;
        background: #d9d9d9;
        border: 2px solid #d9d9d9;
    }

    .people-carousel .user .name {
        font-size: 16px;
        margin: 0;
        line-height: 2em;
        max-height: 2em;
    }

    @media (pointer: fine) {
        .minimised-sidebar .people-carousel .user:hover {
            background: none;
        }

        .minimised-sidebar .people-carousel .user:hover .profile-image {
            transform: translateY(-3px) scale(1.03);
            border: 2px solid var(--green);
        }
    }

    .add-icon {
        font-weight: 400;
        color: var(--blue);
        cursor: pointer;
        font-size: 48px;
    }

    .add-icon:hover {
        color: var(--light-blue);
    }

    .minimised-sidebar .add-icon {
        margin: 0;
    }

    .people-carousel .active-user, .user-link.router-link-active {
        pointer-events: none;
    }

    .people-carousel .active-user .profile-image {
        width: 60px;
        height: 60px;
        border: 2px solid var(--blue);
    }

    .people-carousel .active-user .name {
        color: #2a2b30;
    }

    .minimised-sidebar .people-carousel {
        padding: 0 !important;
    }

    .minimised-sidebar .people-carousel .user {
        align-items: center;
        justify-content: center;
    }

    .minimised-sidebar .people-carousel .user .profile-image {
        width: calc(0.65 * var(--min-sidebar-width));
        height: calc(0.65 * var(--min-sidebar-width));
        margin-right: 0 !important;
    }

    .minimised-sidebar .people-carousel .active-user .profile-image {
        width: calc(0.85 * var(--min-sidebar-width));
        height: calc(0.85 * var(--min-sidebar-width));
    }

    .minimised-sidebar .people-carousel .user .name {
        display: none;
    }

    @media (max-width: 1024px) {
        .add-icon {
            font-size: 92px;
            align-items: flex-start;
        }

        .people-carousel {
            flex-direction: unset;
            min-width: unset;
            padding: 0 !important;
            height: 100%;
            width: unset;
            overflow-x: visible;
        }

        .people-carousel .user {
            flex-direction: column;
            width: unset;
            justify-content: unset;
            align-items: center;
            height: 100%;
        }

        .people-carousel .user .profile-image {
            margin-right: 0;
            margin-bottom: 16px;
            height: 75px;
            width: 75px;
        }

        .people-carousel .user .name {
            text-align: center;
        }
    }
</style>