<template>
    <div v-show="redirect" ref="service" class="service-view centred">
        <div class="header-container">
            <p class="tagline">Add Service</p>
            <h1 class="service-title">{{ title }}</h1>
        </div>
        <p class="service-description">{{ description }}</p>
        <p class="service-info">
            Select the users to add this service to. Note that you will need to
            head over to <b>People</b> once the services are added and fill out
            the required details to complete the setup for this service.
        </p>
        <div class="people-container centred">
            <admin-circle :fn="toggle"></admin-circle>
        </div>
        <flat-button
            ref="button"
            text="Confirm"
            v-on:click="confirm"
        ></flat-button>
    </div>
</template>

<script>
import Page from "../../components/layout/Page.vue";
import AdminCircle from "../../components/widgets/app/admin-circle/admin-circle.vue";
import FlatButton from "../../components/widgets/buttons/flat-button.vue";
import WelcomeCard from "../welcome/welcome-card.vue";

export default {
    name: "ServiceView",
    components: { AdminCircle, FlatButton, Page, WelcomeCard },
    computed: {
        redirect() {
            if (
                this.$store.getters["service/activeService"]["service_name"] === undefined
            ) {
                this.$router.push("/404");
                return false;
            }
            return true;
        },
        title() {
            return this["service_name"];
        }
    },
    data() {
        return {
            users: [],
        };
    },
    props: {
        "service_id": Number,
        "service_name": String,
        description: String,
        icon: String,
    },
    methods: {
        toggle(user, el) {
            let contains = false;
            for (const addedUser of this.users) {
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
            el.classList.toggle("selected-user");
        },
        async confirm() {
            const response = { message: "", success: true };
            await this.$store.dispatch("member/addServiceToMembers", {
                serviceId: this["service_id"],
                members: this.users,
                response,
            });
            if (response.success) {
                alert(
                    "Services successfully added. The selected users will now have " +
                    "access to " +
                    this["service_name"] +
                    "."
                );
                this.toMarketplace();
            } else {
                alert(response.message);
            }
        },
        toMarketplace() {
            this.$router.push({ name: "marketplace" });
        },
    },
    watch: {
        users: {
            handler() {
                if (this.users.length === 0) this.$refs.button.disable();
                else this.$refs.button.enable();
            },
            deep: true,
        },
    },
    mounted() {
        this.$refs.button.disable();
    },
};
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

.service-view .service-description,
.service-view .service-info {
    font-size: 18px;
}

.service-view .header-container,
.service-view .service-description,
.service-view .service-info {
    width: 100%;
}

.service-view .people-container {
    max-width: 100%;
}

.service-view .selected-user {
    pointer-events: auto !important;
    background: var(--blue) !important;
    opacity: unset !important;
}

.service-view .selected-user .profile-image {
    width: 16vh !important;
    height: 16vh !important;
    border-color: #FFF !important;
}

.service-view .selected-user .name {
    color: #FFF !important;
}

@media (min-width: 850px) {
    .service-view .header-container,
    .service-view .service-description,
    .service-view .service-info {
        max-width: 75%;
    }
}

@media (min-width: 1200px) {
    .service-view .header-container,
    .service-view .service-description,
    .service-view .service-info {
        max-width: 66%;
    }
}

@media (min-width: 1500px) {
    .service-view .header-container,
    .service-view .service-description,
    .service-view .service-info {
        max-width: 50%;
    }
}
</style>