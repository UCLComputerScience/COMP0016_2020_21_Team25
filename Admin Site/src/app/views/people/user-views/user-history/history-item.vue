<template>
    <div class="history-item centred" v-on:click="go">
        <div class="row centred">
            <span class="service-name">{{ serviceName }}</span>
        </div>
        <div class="content-row centred">
            <span class="date">Used on: <b>{{ date }}</b></span>
            <span class="time">At: <b>{{ time }}</b></span>
        </div>
    </div>
</template>

<script>
export default {
    name: "history-item",
    props: { data: Object },
    computed: {
        dateObject() {
            return new Date(this.data.timestamp * 1000);
        },
        date() {
            return this.dateObject.toLocaleDateString();
        },
        time() {
            return this.dateObject.toLocaleTimeString();
        },
        serviceName() {
            return this.data["service_name"];
        },
        id() {
            return this.data["service_id"].toString();
        }
    },
    methods: {
        go() {
            this.$router.push({ name: "marketplace", hash: `#${this.id}` });
        }
    }
};
</script>

<style scoped>
.history-item {
    flex-direction: column;
    border-radius: var(--border-radius);
    border: 1px solid rgba(0, 0, 0, 0.2);
    box-shadow: 0 0 1px 0 rgba(0, 0, 0, 0.125);
    margin: 10px;
    cursor: pointer;
    transition: .2s ease-in-out all;
    width: -webkit-fill-available;
}

.history-item:hover {
    transform: translateY(-16px);
    box-shadow: 0 4px 8px 1px rgba(0, 0, 0, 0.2);
    filter: brightness(65%);
}

.service-name {
    text-transform: capitalize;
    font-weight: 700;
    color: #FFF;
}

.row {
    background: var(--blue);
    border-top-left-radius: var(--border-radius);
    border-top-right-radius: var(--border-radius);
}

.row, .content-row {
    width: 100%;
    padding: 16px;
    flex-direction: column;
    align-items: flex-start;
}

.content-row {
    border-bottom-left-radius: var(--border-radius);
    border-bottom-right-radius: var(--border-radius);
    background: #FFF;
}

.content-row .date {

}

.content-row .time {

}
</style>