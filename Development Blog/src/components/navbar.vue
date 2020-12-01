<template>
    <header class="navbar" role="banner">
        <v-link class="nav-title" href="/">
            <span class="nav-icon material-icons">face</span>
            <p>Concierge</p>
        </v-link>

        <div class="nav-toggler" v-on:click="toggleNav">
            <span class="material-icons">menu</span>
        </div>

        <nav class="nav-buttons" role="navigation">
            <ul id="nav-buttons">
                <li :id="href" class="nav-item"
                    v-for="[href, text] in Object.entries(routes)">
                    <v-link :href="href" :text="text" v-on:click="setActive"></v-link>
                </li>

                <slot></slot>
            </ul>
        </nav>
    </header>
</template>

<script>
    import {routes} from "../routes.js";
    import VLink from "./v-link.vue";

    export default {
        name: "navbar",
        components: {VLink},
        data() {
            return {
                routes: routes,
                navOpen: false,
            }
        },
        methods: {
            toggleNav(dummy, immediate) {
                immediate = immediate || false;
                let toggler = document.querySelector('.nav-toggler');
                toggler.classList.toggle('clicked');
                let navLinks = document.getElementById('nav-buttons');
                let navButtons = document.querySelector('.nav-buttons');
                if (window.location.href.includes('documentation')) {
                    if (navLinks.classList.contains('docs-nav-visible')) {
                        this.navOpen = false;
                        toggler.classList.remove('clicked');
                        navButtons.classList.remove('slide-right');
                        navButtons.classList.add('slide-left');
                        if (immediate) {
                            this.toggleClasses(navButtons, navLinks);
                        } else {
                            setTimeout(() => {
                                this.toggleClasses(navButtons, navLinks)
                            }, 250);
                        }
                    } else {
                        navButtons.classList.add('slide-right');
                        this.toggleClasses(navButtons, navLinks);
                        this.navOpen = true;
                    }
                } else {
                    navLinks.classList.toggle('nav-visible');
                }
            },

            toggleClasses(navButtons, navLinks) {
                navButtons.classList.remove('slide-left');
                navButtons.classList.toggle('docs-nav');
                navLinks.classList.toggle('docs-nav-visible');
                navLinks.classList.toggle('nav-visible');
                let sidebar = document.getElementById('sidebar');
                sidebar.classList.toggle('mobile-sidebar-visible');
            },

            toggle(button) {
                this.deactivateAll();
                button.classList.add('active-item');
            },

            deactivateAll() {
                let btns = document.getElementsByClassName('active-item');
                for (let btn of btns) {
                    btn.classList.remove('active-item');
                }
            },

            setActive() {
                let btns = document.getElementsByClassName('nav-item');
                let activated = false;
                for (let btn of btns) {
                    let url = btn.id;
                    if (url === "/")
                        continue;
                    if (window.location.href.includes(url)) {
                        activated = true;
                        this.toggle(btn);
                        break;
                    }
                }
                if (!activated) {
                    this.toggle(btns[0])
                }
            },
            toggleSidebar() {
                let width = window.innerWidth || document.documentElement.clientWidth ||
                    document.body.clientWidth;
                if (width > 1024 && this.navOpen) {
                    this.toggleNav(null,true);
                }
            }
        },
        mounted() {
            this.setActive();
            if (window.location.href.includes('documentation')) {
                window.addEventListener('resize', this.toggleSidebar);
            }
        }
    }
</script>

<style scoped>
    .navbar {
        height: 75px;
        display: flex;
        align-items: center;
        width: 100%;
    }

    .nav-title {
        text-decoration: none;
        display: flex;
        align-items: center;
        justify-content: center;
        margin-left: 50px;
        transition: 0.2s ease all;
        cursor: pointer;
    }

    .nav-icon {
        margin-right: 8px;
        color: var(--header);
        margin-top: -2px !important;
    }

    .nav-title p {
        margin: 0;
        font-size: 24px;
        font-weight: 700;
        color: var(--header);
    }

    .nav-title:hover p, .nav-title:hover .nav-icon {
        color: var(--main);
    }

    .nav-buttons {
        flex: 1;
        margin-right: 50px;
    }

    .nav-buttons ul {
        display: inline-flex;
        list-style: none;
        float: right;
    }

    .nav-item {
        display: flex;
        align-items: center;
        justify-content: center;
    }

    .nav-item a {
        text-decoration: none;
        font-weight: 700;
        color: var(--header);
        padding: 18px 17px;
        font-size: 17px;
    }

    .nav-item:hover a {
        color: var(--main);
    }

    .active-item a {
        color: var(--accent) !important;
        pointer-events: none;
    }

    .nav-toggler {
        display: none;
        color: var(--header);
        float: right;
        margin-right: 8px;
        cursor: pointer;
    }

    .nav-toggler span {
        font-size: 32px !important;
        color: var(--header);
    }

    @media (pointer: fine) {
        .nav-toggler:hover span {
            color: var(--main);
        }

        .clicked:hover span {
            color: black;
        }
    }

    .nav-toggler:active, .clicked span {
        color: var(--main) !important;
    }


    .docs-nav {
        background: white;
        overflow-y: auto;
        overflow-x: hidden;
        height: calc(100vh - 76px);
        border-right: 1px solid #eaecef;
        display: flex;
        flex-direction: row;
        box-shadow: 5px 0 8px -6px rgba(0, 0, 0, 0.25);
        -webkit-box-shadow: 5px 0 8px -6px rgba(0, 0, 0, 0.25);
        -moz-box-shadow: 5px 0 8px -6px rgba(0, 0, 0, 0.25);
    }

    .docs-nav-visible {
        margin-bottom: 0 !important;
    }

    .slide-right {
        animation: slideIn 0.2s forwards;
        -webkit-animation: slideIn 0.2s forwards;
        -moz-animation: slideIn 0.2s forwards;
    }

    .slide-left {
        animation: slideOut 0.2s forwards !important;
        -webkit-animation: slideOut 0.2s forwards !important;
        -moz-animation: slideOut 0.2s forwards !important;
    }

    @media only screen and (pointer: coarse) {
        .nav-title {
            margin-top: 0 !important;
        }

        .nav-title span {
            margin-top: 0 !important;
        }
    }

    @media (max-width: 1024px) {
        .nav-visible {
            display: flex !important;
        }

        .navbar {
            display: block;
            padding-top: 20px;
        }

        .nav-toggler {
            display: block;
        }

        .nav-title {
            margin-left: 8px;
            margin-top: 8px;
            float: left;
        }

        .nav-buttons {
            width: 100%;
            margin: 0;
        }

        .docs-nav {
            margin: 55px 0 0;
            width: 70%;
        }

        .docs-nav ul {
            margin-top: 0 !important;
        }

        .docs-nav ul li {
            width: inherit;
        }

        #nav-buttons {
            display: none;
        }

        .nav-buttons ul {
            display: flex;
            flex-direction: column;
            margin-right: 0;
            padding: 0;
            width: 100%;
        }

        .nav-item {
            display: flex;
            text-align: center;
            align-items: center;
            justify-content: center;
            background: var(--main);
            cursor: pointer;
            padding: 0;
            width: 100%;
            margin: 0;
            border-bottom: 1px solid var(--accent);
        }

        .nav-item:last-child {
            border-bottom: none;
        }

        .nav-item a {
            flex: 1;
            color: white;
            width: 100%;
            margin: 0;
            padding: 16px 32px;
        }

        .nav-item:hover {
            background: var(--accent);
        }

        .nav-item:hover a {
            color: white;
        }

        .active-item {
            pointer-events: none;
        }

        .active-item a {
            color: white !important;
        }
    }
</style>