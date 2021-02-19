<template>
    <section :id="category.toLowerCase()" :style="'--columns: ' + gridData.columns"
             class="marketplace-section" ref="container">
        <h2 class="section-header">{{ category }}</h2>
        <div class="section-content centred">
            <div class="row centred" v-for="serviceRow in chunks" v-bind:key="serviceRow">
                <marketplace-item :ref="setRef" :service-data="service"
                                  v-for="service in serviceRow" v-bind:key="service"></marketplace-item>
            </div>
        </div>
    </section>
</template>

<script>
    import MarketplaceItem from "./marketplace-item.vue";
    import backend from "../../../assets/scripts/backend/backend";

    export default {
        name: "marketplace-section",
        components: {MarketplaceItem},
        props: {
            category: String, gridData: {
                type: Object, default: {
                    columns: 3
                }
            }
        },
        computed: {
            chunks() {
                const chunks = []
                for (let i = 0, j = this.services.length; i < j; i += this.gridData.columns)
                    chunks.push(this.services.slice(i, i + this.gridData.columns));
                return chunks;
            },
        },
        data() {
            return {
                serviceRefs: [],
                services: [],
            }
        },
        beforeUpdate() {
            this.serviceRefs = [];
        },
        created() {
            this.fetchServicesInCategory();
        },
        methods: {
            fetchServicesInCategory() {
                backend.fetchServicesInCategory(this.category).then(result => {
                    this.services = result;
                })
            },
            setRef(el) {
                this.serviceRefs.push(el);
            },
            search(searchTerm) {
                const resultSet = [];
                const category = this.category.toLowerCase();
                if (searchTerm.includes(category) || category.includes(searchTerm)) {
                    for (let service of this.serviceRefs) {
                        resultSet.push(service.getData());
                    }
                } else {
                    for (let service of this.serviceRefs) {
                        const serviceData = service.search(searchTerm);
                        if (serviceData !== null) {
                            resultSet.push(serviceData);
                        }
                    }
                }
                return resultSet;
            },
        }
    }
</script>

<style scoped>
    .marketplace-section {
        --columns: 3;
        width: 100%;
    }

    .marketplace-section:not(:last-child) {
        margin-bottom: 32px;
    }

    .marketplace-section .section-header {
        border-bottom: 2px solid var(--header-color);
        padding-bottom: 32px;
        margin-bottom: 32px;
        width: 100%;
        text-transform: capitalize;
    }

    .section-content {
        flex-direction: column;
    }

    .section-content > .row {
        justify-content: flex-start;
        width: 100%;
    }

    .section-content > .row:not(:last-child) {
        margin-bottom: 24px;
    }

    .section-content > .row > * {
        max-width: calc(100% / var(--columns) - 24px);
        margin-left: 12px;
    }

    .section-content > .row > *:not(:last-child) {
        margin-right: 12px;
    }
</style>