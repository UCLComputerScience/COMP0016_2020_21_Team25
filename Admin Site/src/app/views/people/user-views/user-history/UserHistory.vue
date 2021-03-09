<template>
    <div class="user-history centred">
        <div ref="header" class="header-container">
            <p class="tagline">{{ prefix }}</p>
            <h1 class="user-title">{{ title }} Usage History</h1>
            <p>Below is the full service usage history for <b>{{ name }}</b> including the date and time of service access. Click on an item to view the service in
                the marketplace.</p>
        </div>
        <div class="history-content centred noselect">
            <history-item v-for="item of history" :key="item" :data="item"></history-item>
        </div>
    </div>
</template>

<script>
import {getName} from "../../../../../assets/scripts/util";
import HistoryItem from "./history-item.vue";

export default {
    name: "UserHistory",
    components: { HistoryItem },
    computed: {
        history() {
            return this.$store.getters["member/history"];
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
    created() {
        this.$store.dispatch("member/fetchHistory");
    }
};
</script>

<style scoped>
.user-history {
    justify-content: flex-start;
    padding: 32px;
    max-width: 100%;
}

.user-history .header-container {
    width: 100%;
    border-bottom: 2px solid var(--header-color);
}

.user-history .header-container p b {
    text-transform: capitalize;
}

.user-history,
.history-content {
    flex-direction: column;
    width: 100%;
}

.history-content {
    align-items: flex-start;
    padding: 32px;
}

.user-title {
    margin: 0;
    width: 100%;
    font-size: 36px;
    text-transform: capitalize;
}

@media (min-width: 900px) {
    .history-content,
    .header-container {
        max-width: 95%;
    }
}
</style>