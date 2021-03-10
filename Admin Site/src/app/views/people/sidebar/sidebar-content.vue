<template>
    <admin-circle ref="people" :fn="go" class="people-carousel">
        <slot></slot>
    </admin-circle>
</template>

<script>
import AdminCircle from "../../../components/widgets/app/admin-circle/admin-circle.vue";

export default {
    name: "sidebar-content",
    components: { AdminCircle },
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
        },
    },
    methods: {
        async go(user, el) {
            await this.$store.dispatch("member/activeMember", user.id);
            const person = user["first-name"] + " " + user["last-name"];
            const param = person.replace(/[ ]/g, "-").toLowerCase();
            await this.$router.push({
                name: "user-details",
                params: { person: param },
            });
        },
        toggle() {
            this.$nextTick(() => {
                const elements = this.$refs.people.elements();
                for (const el of elements) {
                    if (el === null || el === undefined) continue;
                    el.deactivate();
                    if (
                        this.$route.params.person !== undefined &&
                        this.$route.name !== "people" &&
                        this.activeMember !== undefined &&
                        el.user().id === this.$store.getters["member/activeId"]
                    ) {
                        el.activate();
                    }
                }
            });
        },
    },
};
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
    flex-direction: row !important;
    padding: 6px !important;
    border-radius: 10px !important;
}

.people-carousel .user .profile-image {
    width: 2em !important;
    height: 2em !important;
    margin-right: 8px;
    background: #D9D9D9;
    border: 2px solid #D9D9D9;
}

.people-carousel .user .name {
    font-size: 16px !important;
    margin: 0 !important;
    line-height: 2em;
    max-height: 2em;
    text-align: left !important;
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

.people-carousel .active-user,
.user-link.router-link-active {
    pointer-events: none;
}

.people-carousel .active-user .profile-image {
    width: 60px !important;
    height: 60px !important;
    border: 2px solid var(--blue);
}

.people-carousel .active-user .name {
    color: #2A2B30;
}

.minimised-sidebar .people-carousel {
    padding: 0 !important;
}

.minimised-sidebar .people-carousel .user {
    align-items: center;
    justify-content: center;
}

.minimised-sidebar .people-carousel .user .profile-image {
    width: calc(0.65 * var(--min-sidebar-width)) !important;
    height: calc(0.65 * var(--min-sidebar-width)) !important;
    margin-right: 0 !important;
}

.minimised-sidebar .people-carousel .active-user .profile-image {
    width: calc(0.85 * var(--min-sidebar-width)) !important;
    height: calc(0.85 * var(--min-sidebar-width)) !important;
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
        flex-direction: column !important;
        width: unset;
        justify-content: unset !important;
        align-items: center;
    }

    .people-carousel .user .profile-image {
        margin-right: 0 !important;
        margin-bottom: 16px;
        height: 75px !important;
        width: 75px !important;
    }

    .people-carousel .user .name {
        text-align: center !important;
    }
}
</style>