<template>
    <div class="user-history centred">
        <div ref="header" class="header-container">
            <p class="tagline">{{ prefix }}</p>
            <h1 class="user-title">{{ title }} Usage History</h1>
            <p>Below is the full service usage history for <b>{{ name }}</b> including the date and time of service
                access. Click on an item to view the service in
                the marketplace.</p>
        </div>
        <div class="history-content centred noselect">
            <span v-show="noHistory">No service usage history for {{ name }}. Come back after they've started using
                the Concierge app; their usage history will appear here.</span>
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
        noHistory() {
            return this.history.length === 0;
        },
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
    padding: 16px;
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
    padding: 16px;
}

.history-content span {
    width: 100%;
    text-align: center;
}

.user-title {
    margin: 0;
    width: 100%;
    font-size: 36px;
    text-transform: capitalize;
}

@media (max-width: 320px) {
    .history-content {
        padding: 0;
    }
}

@media (min-width: 900px) {
    .user-history, .history-content {
        padding: 32px;
    }

    .history-content,
    .header-container {
        max-width: 95%;
    }
}
</style>