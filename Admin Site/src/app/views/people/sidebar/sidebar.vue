<template>
    <aside ref="sidebar" class="sidebar centred noselect">
        <sidebar-header></sidebar-header>
        <div class="sidebar-content centred">
            <sidebar-content>
                           <span v-show="showAddButton" class="add-icon centred material-icons"
                                 v-on:click="addNewUser">add_circle</span>
            </sidebar-content>
        </div>
        <div ref="toggler" class="sidebar-toggler centred" v-on:click="toggleSidebar">
            <span class="toggler-close centred material-icons">arrow_back_ios</span>
            <span class="toggler-open centred material-icons">arrow_forward_ios</span>
        </div>
    </aside>

    <add-user-dialog ref="dialog"></add-user-dialog>
</template>

<script>
import AddUserDialog from './dialog/add-user-dialog.vue';
import SidebarContent from "./sidebar-content.vue";
import SidebarHeader from "./sidebar-header.vue";

export default {
    name: "sidebar",
    components: { SidebarHeader, SidebarContent, AddUserDialog },
    computed: {
        showAddButton() {
            return this.users.length < 30;
        },
        users() {
            return this.$store.getters["member/memberIds"];
        }
    },
    data() {
        return {
            minimised: false,
            autoMinimised: false,
        };
    },
    methods: {
        addNewUser() {
            this.$refs.dialog.show();
        },
        toggleSidebar() {
            this.$refs.sidebar.classList.toggle('minimised-sidebar');
            this.$refs.toggler.classList.toggle('open');
            this.minimised = !this.minimised;
            window.localStorage.setItem('sidebar', this.minimised.toString());
        },
        scaleSidebar() {
            let width = window.innerWidth || document.documentElement.clientWidth ||
                document.body.clientWidth;
            if (width > 1024 && width <= 1200) {
                if (!this.minimised) {
                    this.toggleSidebar();
                    this.autoMinimised = true;
                }
            } else if (width > 1300) {
                if (this.minimised && this.autoMinimised) {
                    this.toggleSidebar();
                    this.autoMinimised = false;
                }
            } else {
                if (this.minimised) {
                    this.toggleSidebar();
                }
            }
        }
    },
    mounted() {
        if (window.localStorage.getItem('sidebar') === 'true') {
            this.toggleSidebar();
        } else
            window.localStorage.setItem('sidebar', 'false');
        this.scaleSidebar();
        window.addEventListener('resize', this.scaleSidebar);
    },
    beforeUnmount() {
        window.removeEventListener('resize', this.scaleSidebar);
    }
};
</script>

<style scoped>
.sidebar {
    width: var(--max-sidebar-width);
    background: #F4F5F7;
    position: relative;
    flex-direction: column;
}

.sidebar::after {
    content: "";
    position: absolute;
    top: 0;
    bottom: 0;
    left: var(--max-sidebar-width);
    width: 4px;
    z-index: 2;
    background: linear-gradient(90deg, rgba(9, 30, 66, 0.13) 0, rgba(9, 30, 66, 0.13) 1px, rgba(9, 30, 66, 0.08) 1px, rgba(9, 30, 66, 0) 4px);
}

.sidebar-content {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    overflow: hidden;
    flex-direction: column;
}

.sidebar, .sidebar-toggler, .sidebar::after {
    transition: .3s ease-in-out all;
    -webkit-backface-visibility: hidden;
    -moz-backface-visibility: hidden;
    backface-visibility: hidden;
}

.sidebar-toggler, .sidebar-toggler::after {
    transition-property: left;
}

.minimised-sidebar {
    width: var(--min-sidebar-width);
}

.minimised-sidebar::after {
    left: var(--min-sidebar-width);
}

.minimised-sidebar .sidebar-toggler {
    left: calc(var(--min-sidebar-width) - 12px);
}

.sidebar-toggler {
    background: var(--blue);
    border-radius: 50%;
    padding: 0;
    position: fixed;
    bottom: 28px;
    left: calc(var(--max-sidebar-width) - 12px);
    width: 24px;
    height: 24px;
    box-shadow: 0 0 0 1px rgba(9, 30, 66, .08), 0 2px 4px 1px rgba(9, 30, 66, .08);
    color: #FFF;
    cursor: pointer;
    z-index: 10;
}

.sidebar-toggler:hover {
    background: var(--light-blue);
}

.sidebar-toggler span {
    zoom: 0.5;
    font-weight: 800;
}

.sidebar-toggler .toggler-close {
    display: block;
}

.sidebar-toggler .toggler-open {
    display: none;
}

.open .toggler-open {
    display: block;
}

.open .toggler-close {
    display: none;
}

@media (max-width: 1024px) {
    .sidebar {
        width: 100%;
        height: unset;
    }

    .sidebar::after {
        content: none;
    }

    .sidebar-content {
        position: relative;
        padding: 16px;
        flex-direction: row;
        align-items: flex-start;
        max-width: 100%;
        overflow-x: auto;
    }

    .sidebar-toggler {
        display: none;
    }
}
</style>