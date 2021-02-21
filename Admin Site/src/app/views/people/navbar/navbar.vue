<template>
    <div ref="navbar" class="people-navbar noselect centred">
        <h2 class="people-navbar-header">Viewing {{ person }}</h2>
        <ul class="people-nav-buttons centred">
            <nav-item ref="manage" href="user-details" text="Manage"></nav-item>
            <nav-item
                ref="services"
                href="user-services"
                text="Services"
            ></nav-item>
            <nav-item
                ref="history"
                href="user-history"
                text="History"
            ></nav-item>
        </ul>
    </div>
</template>

<script>
import NavItem from "./nav-item.vue";

export default {
    name: "navbar",
    components: {NavItem},
    computed: {
        person() {
            const member = this.$store.getters["member/activeMember"];
            if (member !== null && member !== undefined) {
                return member.firstName + " " + member.lastName;
            }
            return "";
        },
    },
};
</script>

<style scoped>
.people-navbar {
    width: 100%;
    height: var(--people-nav-height);
    min-height: var(--people-nav-height);
    background: #F4F5F7;
    position: relative;
    max-width: 100vw;
}

.people-navbar::after {
    content: "";
    position: absolute;
    left: 0;
    right: 0;
    top: 100%;
    height: 4px;
    background: linear-gradient(
        180deg,
        rgba(9, 30, 66, 0.13) 0,
        rgba(9, 30, 66, 0.13) 1px,
        rgba(9, 30, 66, 0.08) 1px,
        rgba(9, 30, 66, 0) 4px
    );
}

.people-navbar-header {
    position: absolute;
    left: 16px;
    margin: 0;
    font-weight: 400;
    font-size: 20px;
    text-transform: capitalize;
}

.people-nav-buttons {
    padding: 0;
    margin: 0;
}

@media (max-width: 1024px) {
    .people-navbar {
        flex-direction: column-reverse;
        height: unset;
        padding-bottom: 32px;
    }

    .people-nav-buttons {
        margin-top: 12px;
    }

    .people-navbar-header {
        text-align: center;
        left: 0;
        position: relative;
        margin-top: 16px;
    }
}
</style>
