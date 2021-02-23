<template>
    <div ref="marketplace" class="marketplace full-page centred">
        <div ref="header" class="header-container">
            <p class="tagline">Discover services</p>
            <h1 class="marketplace-title">Marketplace</h1>
        </div>
        <p ref="subtitle" class="marketplace-subtitle">Welcome to the marketplace. Here,
            you can discover new services to add to members in your circle. Use the
            options below to find the specific service(s) you're looking for.</p>
        <div ref="toolbar" class="marketplace-toolbar centred">
            <text-input id="marketplace-search" ref="search-bar" :maxlength="255"
                        :object="searchData" :on-enter="search" icon="search"
                        key-name="searchTerm" :no-spaces="false"
                        label="Search the marketplace" placeholder="Search for services">
            </text-input>
            <dropdown :items="dropdownItems" label="Select a category"
                      title="View by Category"></dropdown>
        </div>
        <div class="marketplace-content centred">
            <marketplace-section v-for="category in categories" v-show="searchData.searchTerm.length < 4"
                                 :ref="setRef" :category="category" :key="category"
                                 :grid-data="gridData">
            </marketplace-section>
            <search-results v-if="searchData.searchTerm.length >= 4" :columns="gridData.columns"
                            :search-data="searchData"
                            :search-results="searchResults">
            </search-results>
        </div>
    </div>
</template>

<script>
import Dropdown from "../../components/widgets/misc/dropdown/dropdown.vue";
import SearchResults from "./search-results.vue";
import TextInput from "../../components/widgets/text-input/text-input.vue";
import MarketplaceSection from "./marketplace-section.vue";
import WelcomeCard from "../welcome/welcome-card.vue";
import Page from "../../components/layout/Page.vue";

export default {
    name: "MarketplaceView",
    components: {Dropdown, SearchResults, TextInput, MarketplaceSection, WelcomeCard, Page},
    computed: {
        dropdownItems() {
            const items = [];
            for (let category of this.categories) {
                items.push({
                    text: category,
                    fn: () => {
                        this.$router.push("#" + category.toLowerCase());
                    }
                })
            }
            return items;
        },
    },
    watch: {
        searchData: {
            handler() {
                this.search();
            },
            deep: true
        }
    },
    data() {
        return {
            gridData: {
                columns: 3,
            },
            categories: [],
            searchData: {
                searchTerm: "",
            },
            sections: [],
            searchResults: [],
        }
    },
    beforeUpdate() {
        this.sections = [];
    },
    created() {
        this.fetchCategories();
    },
    methods: {
        fetchCategories() {
            this.categories = Object.keys(this.$store.getters["service/allServices"]);
        },
        setRef(el) {
            this.sections.push(el);
        },
        setColumns() {
            const width = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
            if (width >= 768 && width < 1200) {
                this.gridData.columns = 2;
            } else if (width >= 1200 && width < 1600) {
                this.gridData.columns = 3;
            } else if (width >= 1600) {
                this.gridData.columns = 4;
            } else {
                this.gridData.columns = 1;
            }
        },
        search() {
            if (this.searchData.searchTerm.length < 4)
                return;
            this.searchResults = [];
            for (const section of this.sections) {
                const resultSet = section.search(this.searchData.searchTerm.toLowerCase());
                if (resultSet !== null) {
                    for (const service of resultSet) {
                        if (service !== null) {
                            this.searchResults.push(service);
                        }
                    }
                }
            }
        }
    },
    mounted() {
        this.setColumns();
        window.addEventListener('resize', this.setColumns);
    },
    beforeUnmount() {
        window.removeEventListener('resize', this.setColumns);
    }
}
</script>

<style scoped>
.marketplace {
    justify-content: flex-start;
    padding: 32px;
    max-width: 100%;
    width: 100%;
}

.header-container {
    width: 100%;
}

.marketplace, .marketplace-content {
    flex-direction: column;
}

.marketplace-title {
    margin-top: 0;
    width: 100%;
}

.marketplace-subtitle {
    margin-top: 0;
    font-size: 18px;
    text-align: left;
}

.marketplace-toolbar {
    flex-direction: column;
    margin-bottom: 24px;
    width: 100%;
    margin-top: 24px;
}

.marketplace-toolbar > *:not(:last-child) {
    margin-bottom: 32px;
    min-width: 100%;
}

@media (min-width: 900px) {
    .marketplace-toolbar {
        flex-direction: row;
    }

    .marketplace-toolbar > *:not(:last-child) {
        margin-bottom: 0;
        margin-right: 32px;
        min-width: calc(50% - 32px);
    }

    .marketplace-content, .header-container,
    .marketplace-toolbar, .marketplace-subtitle {
        max-width: 66%;
    }
}
</style>