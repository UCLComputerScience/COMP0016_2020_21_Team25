<template>
    <div ref="button" :style="'--hover-color: ' + hoverColor"
         class="mobile-nav-item centred" v-on:click="go">
        <span class="nav-icon material-icons">{{ icon }}</span>
        <span class="nav-text">{{ text }}</span>
    </div>
</template>

<script>
export default {
    name: "mobile-nav-item",
    props: {
        text: String,
        href: String,
        icon: String,
        hoverColor: { type: String, default: "var(--blue)" }
    },
    methods: {
        activate() {
            this.$refs.button.classList.add("active-nav-item");
        },
        deactivate() {
            this.$refs.button.classList.remove("active-nav-item");
        },
        go() {
            if (this.href.includes("/"))
                this.$router.push({ path: this.href });
            else
                this.$router.push({ name: this.href });
        }
    },
    mounted() {
        if (this.$route.name === this.href || this.$route.path === this.href) {
            this.activate();
        }
    }
};
</script>

<style scoped>
.mobile-nav-item {
    --hover-color: var(--blue);
    border-radius: 5px;
    font-weight: 700;
    padding: 8px;
    cursor: pointer;
    color: #000;
    justify-content: flex-start;
}

.mobile-nav-item:hover {
    background: var(--light-blue);
}

.mobile-nav-item:not(:last-child) {
    margin-right: 16px;
}

.active-nav-item {
    pointer-events: none;
    background: var(--green);
}

.nav-icon {
    margin-right: 8px;
    color: inherit;
}

.nav-text {
    color: inherit;
    text-align: left;
    line-height: 75%;
}

.mobile-nav-item:hover {
    background: var(--pale-blue);
    color: var(--hover-color);
}
</style>