<template>
    <section id="search-results" ref="container"
             :style="'--columns: ' + columns" class="marketplace-section">
        <h2 class="section-header">Search Results for "{{ searchData.searchTerm }}"</h2>
        <div class="section-content centred">
            <div v-for="serviceRow in chunkedServices" class="row centred">
                <marketplace-item v-for="service in serviceRow" :service-data="service">
                </marketplace-item>
            </div>
            <span v-show="chunkedServices.length === 0">No services found.</span>
        </div>
    </section>
</template>

<script>
import MarketplaceItem from "./marketplace-item.vue";

export default {
    name: "search-results",
    components: { MarketplaceItem },
    props: {
        searchData: Object,
        searchResults: Array,
        columns: { type: Number, default: 3 },
    },
    computed: {
        chunkedServices() {
            const chunks = [];
            for (let i = 0, j = this.searchResults.length; i < j; i += this.columns) {
                chunks.push(this.searchResults.slice(i, i + this.columns));
            }
            return chunks;
        },
    },
    methods: {
        width() {
            return this.$refs.container.offsetWidth;
        }
    }
};
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
    width: calc(100% - 24px);
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
}

.section-content > .row > *:not(:last-child) {
    margin-right: 24px;
}
</style>