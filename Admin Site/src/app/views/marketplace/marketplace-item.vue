<template>
    <div class="service noselect" ref="service" v-on:click="go">
        <span class="service-icon image">
            <img :alt="serviceData.title" :src="serviceData.icon" />
        </span>
        <div class="row">
            <h3 class="service-title">{{ serviceData.title }}</h3>
            <p class="service-description">{{ serviceData.description }}</p>
        </div>
    </div>
</template>

<script>
    export default {
        name: "marketplace-item",
        props: {
            serviceData: Object,
        },
        methods: {
            search(searchTerm) {
                const title = this.serviceData.title.toLowerCase();
                const description = this.serviceData.description.toLowerCase();
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
                            query: { "service-id": this.serviceData.id },
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
    .service, .service > * {
        transition: 0.25s ease-in-out all;
    }

    .service {
        border-radius: var(--border-radius);
        box-shadow: 0 0 2px 1px rgba(0, 0, 0, 0.2);
        cursor: pointer;
        min-width: 0;
        overflow: hidden;
        height: 400px;
    }

    .service:hover {
        transform: translateY(-16px);
        box-shadow: 0 4px 8px 1px rgba(0, 0, 0, 0.2);
        filter: brightness(65%);
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
        background: var(--blue);
    }

    .service-title {
        color: #fff;
        margin: 0;
        font-size: 15px;
    }

    .service-description {
        margin-top: 12px;
        display: -webkit-box;
        -webkit-line-clamp: 3;
        -webkit-box-orient: vertical;
        overflow: hidden;
        text-overflow: ellipsis;
        color: #fff;
    }

    .service-icon {
        height: 60%;
    }
</style>