<template>
    <header ref="navbar" class="navbar noselect centred" role="banner">
        <nav-logo></nav-logo>
        <nav v-show="!mobileNav" class="nav-buttons centred" role="navigation">
            <nav-item ref="people" href="people" text="People"></nav-item>
            <nav-item ref="marketplace" href="marketplace" text="Marketplace"></nav-item>
        </nav>
        <div v-show="!mobileNav" class="profile-toggler" v-on:click.prevent="toProfile">
            <profile-picture></profile-picture>
        </div>
        <span v-show="mobileNav" ref="toggler"
              class="mobile-nav-toggler material-icons" v-on:click.prevent="toggleMobileNav">menu</span>
    </header>
    <mobile-navbar v-show="mobileNav" ref="mobile"></mobile-navbar>
</template>

<script>
import ProfilePicture from "../../widgets/misc/profile-picture.vue";
import MobileNavbar from "./mobile-nav/mobile-navbar.vue";
import NavItem from "./nav-item.vue";
import NavLogo from "./nav-logo.vue";

export default {
    name: "navbar",
    components: { ProfilePicture, MobileNavbar, NavItem, NavLogo },
    computed: {
        mobileNav() {
            return this.width <= 768;
        },
        showShadow() {
            return this.scrollTop > 56;
        }
    },
    data() {
        return {
            width: window.innerWidth || document.documentElement.clientWidth ||
                document.body.clientWidth,
            scrollTop: document.body.scrollTop,
        };
    },
    watch: {
        scrollTop() {
            this.toggleNavShadow();
        }
    },
    methods: {
        toProfile() {
            this.$router.push({ name: "profile" });
        },
        toggleNavShadow() {
            if (this.showShadow)
                this.$refs.navbar.classList.add('nav-triggered');
            else
                this.$refs.navbar.classList.remove('nav-triggered');
        },
        toggleMobileNav() {
            this.$refs.toggler.classList.toggle('clicked');
            this.$refs.mobile.toggle();
        },
        close() {
            this.$nextTick(() => {
                this.$refs.toggler.classList.remove('clicked');
                this.$refs.navbar.focus();
            });
        },
        updateScroll() {
            this.scrollTop = document.scrollingElement.scrollTop;
        },
        updateSize() {
            this.width = window.innerWidth || document.documentElement.clientWidth ||
                document.body.clientWidth;
        }
    },
    mounted() {
        this.$nextTick(() => {
            this.$refs.people.deactivate();
            this.$refs.marketplace.deactivate();
            if (this.$route.name !== "profile") {
                if (this.$route.path.includes("/people"))
                    this.$refs.people.activate();
                else
                    this.$refs.marketplace.activate();
            }
            this.toggleNavShadow();
            window.addEventListener('scroll', this.updateScroll);
            window.addEventListener('resize', this.updateSize);
        });
    },
    beforeUnmount() {
        window.removeEventListener('scroll', this.updateScroll);
        window.removeEventListener('resize', this.updateSize);
    }
};
</script>

<style scoped>
.navbar {
    height: var(--nav-height);
    position: fixed;
    background: var(--blue);
    z-index: 21;
    top: 0;
    left: 0;
    right: 0;
    transition: .2s ease-in-out box-shadow;
}

.nav-triggered {
    box-shadow: 0 0 8px 1px rgba(0, 0, 0, .55);
}

.mobile-nav-toggler {
    position: absolute;
    right: 16px;
    cursor: pointer;
    font-size: 32px;
    color: #FFF;
}

.profile-toggler, .profile-toggler:before {
    position: absolute;
    cursor: pointer;
    border-radius: 50%;
}

.profile-toggler {
    height: 28px;
    width: 28px;
    right: 24px;
}

.profile-toggler:hover {
    filter: brightness(65%);
}

.profile-toggler .profile-picture-container {
    width: 100%;
    height: 100%;
    z-index: 1;
}

@media (pointer: fine) {
    .mobile-nav-toggler:hover {
        color: var(--light-green);
    }
}

.mobile-nav-toggler:active, .clicked {
    color: var(--green) !important;
}
</style>