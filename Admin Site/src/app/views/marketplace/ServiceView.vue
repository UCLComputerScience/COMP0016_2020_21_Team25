<template>
    <div class="service-view centred" ref="service" v-show="redirect">
        <div class="header-container">
            <p class="tagline">Add Service</p>
            <h1 class="service-title">{{ title }}</h1>
        </div>
        <p class="service-description">{{ description }}</p>
        <p class="service-info">Select the users to add this service to. Note that
            you will need to head over to <b>People</b> once the services are added and
            fill out the required details to complete the setup for this service.
        </p>
        <div class="people-container centred">
            <admin-circle :fn="toggle"></admin-circle>
        </div>
        <flat-button ref="button" text="Confirm" v-on:click="confirm"></flat-button>
    </div>
</template>

<script>
    import WelcomeCard from "../welcome/welcome-card.vue";
    import Page from "../../components/layout/Page.vue";
    import FlatButton from "../../components/widgets/buttons/flat-button.vue";
    import AdminCircle from "../../components/widgets/app/admin-circle/admin-circle.vue";
    import backend from "../../../assets/scripts/backend/backend";

    export default {
        name: "ServiceView",
        components: {AdminCircle, FlatButton, Page, WelcomeCard},
        computed: {
            redirect() {
                if (this.$store.getters["service/activeService"].title === undefined) {
                    this.$router.push("/404");
                    return false;
                }
                return true;
            }
        },
        data() {
            return {
                users: [],
            }
        },
        props: {
            id: Number,
            title: String,
            description: String,
            icon: String,
        },
        methods: {
            toggle(user, el) {
                let contains = false;
                for (let addedUser of this.users) {
                    if (addedUser.id === user.id) {
                        const index = this.users.indexOf(addedUser);
                        this.users.splice(index, 1);
                        contains = true;
                        break;
                    }
                }
                if (!contains) {
                    this.users.push(user);
                }
                el.classList.toggle('selected-user');
            },
            confirm() {
                backend.addServiceToUsers(this.id, this.users);
                alert("Services successfully added. The selected users will now have " +
                    "access to " + this.title + ".");
                this.toMarketplace();
            },
            toMarketplace() {
                this.$router.push({name: "marketplace"});
            }
        },
        watch: {
            users: {
                handler() {
                    if (this.users.length === 0)
                        this.$refs.button.disable();
                    else
                        this.$refs.button.enable();
                },
                deep: true,
            },
        },
        mounted() {
            this.$refs.button.disable();
        }
    }
</script>

<style>
    .service-view {
        flex-direction: column;
        padding: 32px;
        width: 100vw;
        justify-content: flex-start;
    }

    .service-view > .header-container {
        border-bottom: 2px solid var(--header-color);
    }

    .service-view .service-title {
        margin-top: 0;
    }

    .service-view .service-description, .service-view .service-info {
        font-size: 18px;
    }

    .service-view .header-container, .service-view .service-description,
    .service-view .service-info {
        width: 100%;
    }

    .service-view .people-container {
        max-width: 100%;
    }

    .selected-user {
        pointer-events: auto !important;
        background: var(--blue) !important;
        opacity: unset !important;
    }

    .selected-user .profile-image {
        width: 16vh !important;
        height: 16vh !important;
        border-color: #fff !important;
    }

    .selected-user .name {
        color: #fff !important;
    }

    @media (min-width: 850px) {
        .service-view .header-container, .service-view .service-description,
        .service-view .service-info {
            max-width: 75%;
        }
    }

    @media (min-width: 1200px) {
        .service-view .header-container, .service-view .service-description,
        .service-view .service-info {
            max-width: 66%;
        }
    }

    @media (min-width: 1500px) {
        .service-view .header-container, .service-view .service-description,
        .service-view .service-info {
            max-width: 50%;
        }
    }
</style>