<template>
    <section>
        <p class="chapter">{{ chapter }}</p>
        <ul class="headings">
            <li class="heading" :id="heading"
                v-for="[heading, data] in Object.entries(headings)">
                <v-link class="sidebar-link" :href="'/documentation/' + data.href"
                        v-on:click="activateHeading(heading)">{{ heading }}
                </v-link>
                <ul class="subheadings">
                    <li class="subheading" :id="subheading"
                        v-for="[subheading, href] in Object.entries(data.subheadings)">
                        <v-link class="sidebar-link" :href="'#' + href"
                                v-on:click="activateSubheading(heading, subheading)">
                            {{ subheading }}
                        </v-link>
                    </li>
                </ul>
            </li>
        </ul>
    </section>
</template>

<script>
    import VLink from "../v-link.vue";

    export default {
        name: "sidebar-group",
        components: {VLink},
        props: {
            chapter: String,
            headings: Object
        },
        methods: {
            showSubheadings(header) {
                let subheadings = header.querySelector('ul');
                subheadings.classList.add('subheadings-visible');
            },
            activateHeading(heading) {
                this.deactivateAll();
                let header = document.getElementById(heading);
                header.classList.add('active-header');
                this.showSubheadings(header);
            },
            activateSubheading(heading, subheading) {
                this.activateHeading(heading);
                let subheader = document.getElementById(subheading);
                subheader.classList.add('active-subheader');
            },
            deactivateAll() {
                this.removeClass('active-header');
                this.removeClass('active-subheader');
                this.removeClass('subheadings-visible');
            },
            removeClass(className) {
                let elements = document.getElementsByClassName(className);
                for (let elem of elements) {
                    elem.classList.remove(className);
                }
            },
            setActive() {
                let headings = document.getElementsByClassName("heading");
                let activeHeading, activeText;
                for (let heading of headings) {
                    if (window.location.href.includes(heading.id.toLowerCase())) {
                        activeText = heading.id;
                        activeHeading = heading;
                        break;
                    }
                }

                let headingActivated = false;
                if (activeHeading) {
                    let subheadings = activeHeading.getElementsByClassName(".subheading");
                    for (let subheading of subheadings) {
                        if (window.location.href.includes(subheading.id.toLowerCase())) {
                            this.activateSubheading(activeText, subheading.id);
                            headingActivated = true;
                            break;
                        }
                    }
                }

                if (!headingActivated && activeHeading) {
                    this.activateHeading(activeText);
                }
            }
        },
        mounted() {
            this.setActive();
        }
    }
</script>

<style scoped>
    section {
        display: block;
    }

    .chapter {
        font-size: 18px;
        font-weight: 900;
        color: var(--text-color);
        padding: 6px 32px;
        width: 100%;
        margin: 0;
    }

    .headings, .subheadings {
        list-style: none;
        margin: 0;
        padding: 0 0 16px;
    }

    .sidebar-link {
        padding: 18px;
        width: 100%;
        text-decoration: none;
        color: inherit;
        font-weight: inherit;
        cursor: pointer;
    }

    .active-header > .sidebar-link, .active-subheader > .sidebar-link,
    .active-header > .sidebar-link:after {
        color: var(--main);
    }

    .heading {
        cursor: pointer;
        margin-top: 12px;
    }

    .heading > a {
        font-size: 16px;
        font-weight: 400;
        padding: 6px 16px 6px 27px;
        border-left: 5px solid transparent;
    }

    .active-header > .sidebar-link {
        font-weight: 600;
        border-left: 5px solid var(--main);
    }

    .heading:hover > a:after, .heading:hover > a,
    .heading > a:hover:after, .heading > a:hover {
        color: var(--accent);
    }

    .heading > a:after {
        content: '\002B';
        color: #777;
        font-weight: bold;
        float: right;
        margin-left: 5px;
        padding-right: 12px;
    }

    .active-header > a:after {
        content: "\2212";
    }

    .subheadings {
        padding: 0;
        display: none;
    }

    .subheadings-visible {
        display: block;
    }

    .subheading {
        margin-top: 10px;
        cursor: pointer;
    }

    .subheading > a {
        font-size: 14px;
        padding-left: 48px;
    }

    .subheading > a:hover {
        color: var(--accent);
    }

    .active-subheader > .sidebar-link {
        font-weight: 300;
    }
</style>