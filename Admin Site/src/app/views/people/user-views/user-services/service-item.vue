<template>
    <marketplace-item ref="service" :marketplace="false" :service-data="serviceData" :id="`${id}-service`">
        <span ref="toggler" class="expand-icon centred material-icons" v-on:click="onClick">expand_more</span>
        <div class="data-row centred">
            <service-data-fields :service-id="id"></service-data-fields>
        </div>
        <div class="button-row centred">
            <flat-button text="Open in marketplace" v-on:click="go"></flat-button>
            <flat-button :style="`--button-bg: var(--red)`" text="Remove" v-on:click="removeService"></flat-button>
        </div>
    </marketplace-item>
</template>

<script>
import {getName} from "../../../../../assets/scripts/util";
import FlatButton from "../../../../components/widgets/buttons/flat-button.vue";
import MarketplaceItem from "../../../marketplace/marketplace-item.vue";
import ServiceDataFields from "./service-data-fields.vue";

export default {
    name: "service-item",
    components: { ServiceDataFields, FlatButton, MarketplaceItem },
    props: { serviceData: Object },
    computed: {
        id() {
            return this.serviceData["service_id"];
        },
    },
    data() {
        return {
            container: null,
        };
    },
    methods: {
        onClick() {
            this.container.classList.toggle("expanded");
        },
        search(searchTerm) {
            return this.$refs.service.search(searchTerm);
        },
        go() {
            this.$router.push({ name: "marketplace", hash: `#${this.id}` });
        },
        async removeService() {
            if (confirm(`Are you sure you want to remove this service from ${getName()} account? They'll no longer have access to this service and you'll have to re-enter any required data should you want to assign this service to them again.`)) {
                await this.$store.dispatch("member/removeServiceFromMember", this.id);
                await this.$parent.updateServices();
            }
        }
    },
    mounted() {
        this.container = document.getElementById(this.id + "-service");
    }
};
</script>

<style>
.services-content .service {
    flex-grow: 1;
    flex-shrink: 0;
    flex-basis: 33vw;
    overflow-y: scroll;
    cursor: default;
    position: relative;
    width: 100%;
}

.services-content .service .data-row,
.services-content .service .button-row {
    flex-direction: column;
    width: 100%;
    max-height: 0;
    overflow: hidden;
}

.services-content .service .flat-button {
    width: 100%;
}

.services-content .service .flat-button:not(:last-child) {
    margin-bottom: 8px;
}

.services-content .service .expand-icon {
    position: absolute;
    right: 16px;
    top: 16px;
    transition: .2s ease-in all;
    font-weight: 900;
    color: #FFF;
    border-radius: 50%;
    background: var(--blue);
    cursor: pointer;
    box-shadow: 0 0 8px 6px rgba(255, 255, 255, .44);
}

.services-content .service .expand-icon:hover {
    filter: brightness(70%);
}

.services-content .service.expanded {
    height: auto !important;
}

.services-content .service.expanded .data-row,
.services-content .service.expanded .button-row {
    max-height: unset;
}

.services-content .service.expanded .expand-icon {
    transform: rotate(-180deg);
}
</style>