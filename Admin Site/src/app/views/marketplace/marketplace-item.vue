<template>
    <div class="service noselect" ref="service" v-on:click="go">
        <div class="row centred">
            <span class="service-icon image">
                <img :alt="serviceData.title" :src="serviceData.icon">
            </span>
            <h3 class="service-title">{{ serviceData.title }}</h3>
        </div>
        <p class="service-description">{{ serviceData.description }}</p>
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
                if (searchTerm.includes(title) || title.includes(searchTerm) ||
                    description.includes(searchTerm) || searchTerm.includes(description)) {
                    return this.serviceData;
                }
                return null;
            },
            go() {
                this.$store.dispatch('service/service', this.serviceData).then(r => {
                    this.$router.push({
                        path: 'marketplace/service', query: {"service-id": this.serviceData.id}
                    });
                })
            },
            getData() {
                return this.serviceData;
            }
        }
    }
</script>

<style scoped>
    .service {
        border-radius: var(--border-radius);
        box-shadow: 0 0 2px 1px rgba(0, 0, 0, .2);
        transition: .25s ease-in-out all;
        cursor: pointer;
        min-width: 0;
    }

    .service:hover {
        transform: translateY(-16px);
        box-shadow: 0 8px 8px 0 rgba(0, 0, 0, .2);
        filter: brightness(65%);
    }

    .service .row {
        padding: 12px;
        background: var(--blue);
        width: 100%;
        border-top-left-radius: var(--border-radius);
        border-top-right-radius: var(--border-radius);
        justify-content: flex-start;
    }

    .service-title {
        color: #fff;
        margin-left: 6px;
        margin-top: 0;
        margin-bottom: 0;
    }

    .service-description {
        margin: 12px;
        display: -webkit-box;
        -webkit-line-clamp: 3;
        -webkit-box-orient: vertical;
        overflow: hidden;
        text-overflow: ellipsis;
    }

    .service-icon {
        border: 2px solid var(--light-blue);
        border-radius: var(--border-radius);
        background: #fff;
        width: 18px;
        height: 18px;
    }
</style>