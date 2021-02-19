<template>
    <div class="marketplace full-page centred" ref="marketplace">
        <div class="header-container" ref="header">
            <p class="tagline">Discover services</p>
            <h1 class="marketplace-title">Marketplace</h1>
        </div>
        <p class="marketplace-subtitle" ref="subtitle">Welcome to the marketplace. Here,
            you can discover new services to add to members in your circle. Use the
            options below to find the specific service(s) you're looking for.</p>
        <div class="marketplace-toolbar centred" ref="toolbar">
            <text-input :maxlength="255" :object="searchData" :on-enter="search"
                        icon="search" id="marketplace-search" key-name="searchTerm"
                        label="Search the marketplace"
                        placeholder="Search for services" ref="search-bar">
            </text-input>
            <dropdown :items="dropdownItems" label="Select a category"
                      title="View by Category"></dropdown>
        </div>
        <div class="marketplace-content centred">
            <marketplace-section :category="category" :grid-data="gridData"
                                 :ref="setRef" v-for="category in categories"
                                 v-show="searchData.searchTerm.length < 4">
            </marketplace-section>
            <search-results :columns="columns" :search-data="searchData"
                            :search-results="searchResults"
                            v-if="searchData.searchTerm.length >= 4">
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
    import backend from "../../../assets/scripts/backend/backend";

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
                backend.fetchCategories().then(result => {
                    this.categories = result;
                })
            },
            setRef(el) {
                if (!this.sections.includes(el))
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
                    this.gridData.columns = 2;
                }
            },
            search() {
                if (this.searchData.searchTerm.length < 4)
                    return;
                this.searchResults = [];
                for (let section of this.sections) {
                    const resultSet = section.search(this.searchData.searchTerm.toLowerCase());
                    this.searchResults = this.searchResults.concat(resultSet);
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