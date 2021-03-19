<template>
    <div class="user-services centred">
        <div ref="header" class="header-container">
            <p class="tagline">{{ prefix }}</p>
            <h1 class="user-title">{{ title }} Assigned Services</h1>
            <p>Below are all the services assigned to <b>{{ name }}</b>. Note that removing a
                service means you must re-enter the required data (if applicable) should you want to assign it to this
                user again. Click the arrow on a service to expand it and view its relevant actions.</p>
        </div>
        <text-input id="user-services-search" ref="search-bar" :label="`Search ${title} services`"
                    :maxlength="255" :no-spaces="false" :object="searchData"
                    :on-enter="search"
                    icon="search" key-name="searchTerm"
                    placeholder="Search assigned services">
        </text-input>
        <div v-show="!searching" class="services-content centred noselect">
            <span v-show="noServices" class="centred">No services added. Head over to the&nbsp;
                <v-link :href="{name: 'marketplace', params: {'username': $route.params.username}}"
                        text="Marketplace"></v-link> &nbsp;to add services to {{ title }} account.</span>
            <service-item v-for="service in services" :ref="setRef" :service-data="service">
            </service-item>
        </div>
        <div v-show="searching" class="search-results centred noselect">
            <h2 class="section-header">Search Results for "{{ searchData.searchTerm }}"</h2>
            <div class="services-content centred noselect">
                <service-item v-for="service in searchResults" :key="service" :service-data="service">
                </service-item>
            </div>
            <span v-show="searchResults.length === 0">No services found.</span>
        </div>
    </div>
</template>

<script>
import {getName} from "../../../../../assets/scripts/util";
import VLink from "../../../../components/widgets/buttons/v-link.vue";
import TextInput from "../../../../components/widgets/text-input/text-input.vue";
import ServiceItem from "./service-item.vue";

export default {
    name: "UserServices",
    components: { VLink, TextInput, ServiceItem },
    computed: {
        noServices() {
            return this.services.length === 0;
        },
        searching() {
            return this.searchData.searchTerm.length >= 4;
        },
        name() {
            if (this.user !== undefined) {
                return this.user["first-name"] + " " + this.user["last-name"];
            }
        },
        title() {
            if (this.user !== undefined) {
                return getName();
            }
        },
        user() {
            return this.$store.getters["member/activeMember"];
        },
        prefix() {
            const user = this.user;
            if (user === undefined) {
                return "";
            }
            return user.prefix;
        },
    },
    data() {
        return {
            services: [],
            searchData: {
                searchTerm: "",
            },
            searchResults: [],
            serviceRefs: [],
        };
    },
    watch: {
        searchData: {
            handler() {
                this.search();
            },
            deep: true
        }
    },
    beforeUpdate() {
        this.serviceRefs = [];
    },
    methods: {
        setRef(el) {
            this.serviceRefs.push(el);
        },
        async updateServices() {
            await this.$store.dispatch("member/fetchMemberServices");
            this.services = this.$store.getters["member/memberServices"];
        },
        search() {
            this.$nextTick(() => {
                if (this.searchData.searchTerm.length < 4)
                    return;
                this.searchResults = [];
                for (const service of this.serviceRefs) {
                    const resultSet = service.search(this.searchData.searchTerm.toLowerCase());
                    if (resultSet !== null) {
                        this.searchResults.push(resultSet);
                    }
                }
            });
        }
    },
    created() {
        this.updateServices();
    }
};
</script>

<style scoped>
.user-services {
    justify-content: flex-start;
    padding: 16px;
    max-width: 100%;
    flex-direction: column;
}

.user-services .header-container {
    border-bottom: 2px solid var(--header-color);
    margin-bottom: 24px;
}

.user-services .header-container p b {
    text-transform: capitalize;
}

.user-services,
.services-content, .search-results, .user-title,
.user-services .header-container {
    width: 100%;
}

.services-content, .search-results {
    padding: 16px;
    flex-wrap: wrap;
    align-items: flex-start;
}

.services-content {
    margin-top: 16px;
    display: grid;
    grid-auto-rows: 1fr;
    grid-template-columns: repeat(auto-fill, minmax(15rem, 10fr));
    grid-gap: 1rem;
}

.services-content span {
    text-align: center;
    grid-column-start: 1;
    grid-column-end: 4;
}

.services-content span a {
    display: contents;
    color: var(--blue);
    font-weight: 900;
}

.services-content span a:hover {
    filter: brightness(1.65);
}

.search-results {
    flex-direction: column;
}

.user-services .section-header {
    margin-top: 0;
    margin-bottom: 0;
    margin-left: 48px;
    width: 100%;
    padding-left: 48px;
}

.user-title {
    margin: 0;
    font-size: 36px;
    text-transform: capitalize;
}

@media (max-width: 320px) {
    .services-content {
        padding: 0;
    }
}

@media (min-width: 900px) {
    .user-services, .services-content {
        padding: 32px;
    }

    .services-content,
    .header-container {
        max-width: 95%;
    }

    .user-services > .text-input {
        width: 50%;
    }
}
</style>