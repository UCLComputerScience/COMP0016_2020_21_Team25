<template>
    <div id="desktop-sidebar-container">
        <div id="sidebar" class="desktop-sidebar docs-sidebar">
            <sidebar-group :chapter="chapter"
                           :headings="data" v-for="[chapter, data] in Object.entries(docs)"
            ></sidebar-group>
        </div>
    </div>
</template>

<script>
    import SidebarGroup from "./sidebar-group.vue";
    import DocsNavbar from "./docs-navbar.vue";

    export default {
        name: "docs-sidebar",
        components: {DocsNavbar, SidebarGroup},
        methods: {
            mobileSidebar(sidebar, mobile) {
                sidebar.classList.remove('desktop-sidebar');
                sidebar.classList.add('mobile-sidebar');
                this.changeParent(sidebar, mobile);
                this.isMobile = true;
            },
            desktopSidebar(sidebar, desktop) {
                sidebar.classList.add('desktop-sidebar');
                sidebar.classList.remove('mobile-sidebar');
                this.changeParent(sidebar, desktop);
                this.isMobile = false;
            },
            changeParent(sidebar, newParent) {
                newParent.appendChild(sidebar);
            },
            scaleSidebar(initial = false) {
                let sidebar = document.getElementById('sidebar');
                let mobile = document.getElementById('mobile-sidebar-container');
                let desktop = document.getElementById('desktop-sidebar-container');
                let width = window.innerWidth || document.documentElement.clientWidth ||
                    document.body.clientWidth;
                if (width <= 1024) {
                    if (!this.isMobile || initial) {
                        this.mobileSidebar(sidebar, mobile);
                    }
                } else {
                    if (this.isMobile) {
                        this.desktopSidebar(sidebar, desktop);
                    }
                }
            }
        },
        mounted() {
            this.scaleSidebar(true);
            window.addEventListener('resize', this.scaleSidebar);
        },
        data() {
            return {
                isMobile: false,
                docs: {
                    "Essentials": {
                        "Installation": {
                            href: "installation",
                            subheadings: {
                                "Source": "source"
                            }
                        },
                        "Introduction": {
                            href: "introduction",
                            subheadings: {
                                "What is Concierge?": "what-is-concierge",
                                "Getting Started": "getting-started",
                            }
                        }
                    },

                    "Services": {
                        "Adding Services": {
                            href: "installation",
                            subheadings: {
                                "Testing": ""
                            }
                        }
                    },

                    "Advanced": {
                        "Test Header": {
                            href: "installation",
                            subheadings: {
                                "Test": ""
                            }
                        }
                    },

                    "Accessibility": {
                        "Input": {
                            href: "installation",
                            subheadings: {
                                "Text-to-speech": ""
                            }
                        }
                    }
                }
            }
        }
    }
</script>

<style scoped>
    .docs-sidebar {
        height: 100%;
        overflow-y: auto;
        overflow-x: hidden;
        padding-top: 24px;
        padding-bottom: 24px;
    }

    #desktop-sidebar-container {
        width: 320px;
        height: 100%;
        border-right: 1px solid #eaecef;
    }

    .desktop-sidebar {
        width: 100%;
        height: calc(100% - 48px);
    }

    @media (max-width: 1024px) {
        #desktop-sidebar-container {
            display: none;
        }
    }
</style>