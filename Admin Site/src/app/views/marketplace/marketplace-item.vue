<template>
    <div :id="id" ref="service" class="service noselect" v-on:click="fn">
        <div class="service-icon image">
            <img :alt="title" :src="icon"/>
        </div>
        <div class="row centred">
            <h3 class="service-title">{{ title }}</h3>
            <p class="service-description">{{ description }}</p>
            <slot></slot>
        </div>
    </div>
</template>

<script>
import {getServiceIcon} from "../../../assets/scripts/util";

export default {
    name: "marketplace-item",
    props: {
        serviceData: Object,
        marketplace: {type: Boolean, default: true}
    },
    computed: {
        icon() {
            const icon = this.getAttr("icon");
            if (icon !== "") {
                return getServiceIcon(icon);
            }
            return "";
        },
        fn() {
            return (this.marketplace) ? this.go : () => {};
        },
        id() {
            return this.getAttr("service_id");
        },
        title() {
            return this.getAttr("service_name");
        },
        description() {
            return this.getAttr("description");
        }
    },
    methods: {
        getAttr(attr) {
            if (this.serviceData !== null) {
                return this.serviceData[attr];
            }
            return "";
        },
        search(searchTerm) {
            const title = this.title.toLowerCase();
            const description = this.description.toLowerCase();
            if (
                searchTerm.includes(title) ||
                title.includes(searchTerm) ||
                description.includes(searchTerm) ||
                searchTerm.includes(description)
            ) {
                return this.serviceData;
            }
            return null;
        },
        go() {
            this.$store
                .dispatch("service/service", this.serviceData)
                .then((_) => {
                    this.$router.push({
                        path: "marketplace/service",
                        query: {"service-id": this.serviceData["service_id"]},
                    });
                });
        },
        getData() {
            return this.serviceData;
        },
    },
};
</script>

<style scoped>
.service,
.service > * {
    transition: 0.25s ease-in-out all;
}

.service {
    border-radius: var(--border-radius);
    border: 1px solid rgba(0, 0, 0, 0.2);
    box-shadow: 0 0 1px 0 rgba(0, 0, 0, 0.125);
    cursor: pointer;
    min-width: 0;
    overflow: hidden;
    height: 400px;
    background: var(--blue);
}

.service:hover {
    transform: translateY(-16px);
    box-shadow: 0 4px 8px 1px rgba(0, 0, 0, 0.2);
}

.service:hover .service-icon {
    height: 25%;
}

.service:hover .row {
    height: 75%;
}

.service:hover .row .service-description {
    -webkit-line-clamp: 12;
}

.service .row {
    padding: 16px;
    height: 40%;
    z-index: 2;
    flex-direction: column;
    justify-content: flex-start;
}

.service-title {
    color: #FFF;
    margin: 0;
    font-size: 21px;
    text-transform: capitalize;
    width: 100%;
}

.service-description {
    margin-top: 12px;
    display: -webkit-box;
    -webkit-line-clamp: 3;
    -webkit-box-orient: vertical;
    overflow: hidden;
    text-overflow: ellipsis;
    color: #FFF;
}

.service-icon {
    height: 60%;
    z-index: 1;
}
</style>