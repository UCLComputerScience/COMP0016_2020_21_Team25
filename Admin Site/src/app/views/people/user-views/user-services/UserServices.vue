<template>
    <div class="user-services centred">
        <div ref="header" class="header-container">
            <p class="tagline">{{ prefix }}</p>
            <h1 class="user-title">{{ title }} Assigned Services</h1>
            <p>Below are all the services assigned to <b>{{ name }}</b>. Note that removing a
                service means you must re-enter the required data (if applicable) should you want to assign it to this
                user again. Click the arrow on a service to expand it and view its relevant actions.</p>
        </div>
        <text-input id="user-services-search" ref="search-bar" :maxlength="255"
                    :object="searchData" :on-enter="search" icon="search"
                    :no-spaces="false"
                    key-name="searchTerm" :label="`Search ${title} services`"
                    placeholder="Search assigned services">
        </text-input>
        <div class="services-content centred noselect" v-show="!searching">
            <service-item :key="service" v-for="service in services" :data="service" :ref="setRef">
            </service-item>
        </div>
        <div class="search-results centred noselect" v-show="searching">
            <h2 class="section-header">Search Results for "{{ searchData.searchTerm }}"</h2>
            <div class="services-content centred noselect">
                <service-item :key="service" v-for="service in searchResults" :data="service">
                </service-item>
            </div>
            <span v-show="searchResults.length === 0">No services found.</span>
        </div>
    </div>
</template>

<script>
import {getName} from "../../../../../assets/scripts/util";
import ServiceItem from "./service-item.vue";
import TextInput from "../../../../components/widgets/text-input/text-input.vue";

export default {
    name: "UserServices",
    components: {TextInput, ServiceItem},
    computed: {
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
        }
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
        updateServices() {
            this.$store.dispatch("member/fetchMemberServices").then(_ => {
                this.services = this.$store.getters["member/memberServices"];
            });
        },
        search() {
            if (this.searchData.searchTerm.length < 4)
                return;
            this.searchResults = [];
            for (const service of this.serviceRefs) {
                const resultSet = service.search(this.searchData.searchTerm.toLowerCase());
                if (resultSet !== null) {
                    this.searchResults = this.searchResults.concat(resultSet);
                }
            }
        }
    },
    created() {
        this.updateServices();
    }
}
</script>

<style scoped>
.user-services {
    justify-content: flex-start;
    padding: 32px;
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
    padding: 32px;
    flex-wrap: wrap;
    align-items: flex-start;
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

@media (min-width: 900px) {
    .services-content,
    .header-container {
        max-width: 95%;
    }

    .user-services > .text-input {
        width: 50%;
    }
}
</style>