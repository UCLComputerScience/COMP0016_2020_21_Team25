<template>
    <div :ref="setRef" class="mobile-navbar noselect centred" tabindex="-1"
         v-on:blur.prevent="toggle">

        <profile-picture></profile-picture>
        <h2 class="full-name">{{ fullName }}</h2>
        <separator></separator>
        <div class="account-links">
            <mobile-nav-item href="profile" icon="person" text="Profile"></mobile-nav-item>
            <mobile-nav-item hover-color="var(--red)" href="/logout" icon="meeting_room"
                             text="Sign out">
            </mobile-nav-item>
        </div>
        <separator></separator>
        <div class="page-links">
            <p class="tagline">Places</p>
            <mobile-nav-item href="people" icon="group" text="People"></mobile-nav-item>
            <mobile-nav-item href="marketplace" icon="shopping_bag" text="Marketplace">
            </mobile-nav-item>
        </div>
        <separator></separator>
        <collaborators></collaborators>
        <authors></authors>
    </div>
</template>

<script>
import ProfilePicture from "../../../widgets/misc/profile-picture.vue";
import Separator from "../../../widgets/misc/separator.vue";
import Authors from "./authors.vue";
import Collaborators from "./collaborators/collaborators.vue";
import MobileNavItem from "./mobile-nav-item.vue";

export default {
    name: "mobile-navbar",
    components: { Separator, MobileNavItem, Authors, Collaborators, ProfilePicture },
    computed: {
        fullName() {
            const user = this.$store.getters["admin/admin"];
            return user["first-name"] + " " + user["last-name"];
        }
    },
    data() {
        return {
            navOpen: false,
            refs: [],
        };
    },
    beforeUpdate() {
        this.refs = [];
    },
    methods: {
        setRef(el) {
            this.refs.push(el);
        },
        isChild(e) {
            if (e === undefined)
                return false;
            const element = e.target;
            return element.classList.contains("mobile-nav-toggler") ||
                this.refs[0].contains(element);
        },
        toggle(e) {
            if (this.navOpen && !this.isChild(e)) {
                this.close();
            } else
                this.open();
        },
        open() {
            this.navOpen = true;
            this.refs[0].classList.add("mobile-nav-visible");
            setTimeout(() => {
                document.getElementById('app').addEventListener('click', this.toggle, false);
            }, 250);
        },
        close() {
            this.navOpen = false;
            this.refs[0].classList.remove("mobile-nav-visible");
            this.$parent.close();
            document.getElementById('app').removeEventListener('click', this.toggle, false);
        },
        toggleSidebar() {
            const width = window.innerWidth || document.documentElement.clientWidth ||
                document.body.clientWidth;
            if (width > 768) {
                if (this.navOpen) {
                    this.toggle();
                }
            }
        }
    },
    mounted() {
        this.$nextTick(() => {
            this.toggleSidebar();
            window.addEventListener('resize', this.toggleSidebar);
        });
    },
    beforeUnmount() {
        window.removeEventListener('resize', this.toggleSidebar);
        document.getElementById('app').removeEventListener('click', this.toggle, false);

    }
};
</script>

<style scoped>
.mobile-navbar {
    position: fixed;
    top: var(--nav-height);
    left: -60%;
    width: 60%;
    height: calc(100% - var(--nav-height));
    background: #F4F5F7;
    z-index: 21;
    -webkit-transform: translate3d(0, 0, 0);
    transform: translate3d(0, 0, 0);
    transition: .25s ease-in-out all;
    flex-direction: column;
    justify-content: flex-start;
    overflow-y: auto;
    padding: 16px;
}

.mobile-nav-visible {
    left: 0;
    box-shadow: 4px 0 8px 0 rgba(0, 0, 0, 0.22);
}

.mobile-navbar:focus {
    outline: none;
    border: none;
}

.mobile-nav-toggler {
    position: absolute;
    right: 16px;
    cursor: pointer;
    font-size: 32px;
    color: #FFF;
}

.profile-picture-container {
    width: 128px;
    height: 128px;
}

.full-name {
    text-align: center;
    margin-bottom: 8px;
}
</style>