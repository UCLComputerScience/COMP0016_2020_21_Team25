<template>
    <page>
        <h1>Providing a helping hand, through speech</h1>
        <p class="subtitle">Working as part of the IBM FISE v2 portfolio in speech analysis and synthesis. We aim to
            connect users and organisations in such unprecedented times.</p>
        <div class="button-group">
            <flat-button text="I'm a user" href="#users"></flat-button>
            <flat-button text="I'm an organisation" href="#organisations"></flat-button>
        </div>
        <separator></separator>

        <section id="users">
            <div class="group">
                <div class="column content-column">
                    <p class="tagline">Concierge for regular users</p>
                    <h2>Your very own personal assistant, on-hand</h2>
                    <p>Tell it what you want, Concierge understands you.</p>
                    <p>Craving fish and chips? Need to be reminded to take your medication? Concierge simplifies
                        your everyday life - all mundane tasks under one app. Literally, just tell it what you want
                        to be done. </p>
                    <p>Many elderly individuals have been left feeling isolated as a result of lockdowns. If you're
                        left feeling the same way, you can tell Concierge to check in with family members for a chat
                        or just as confirmation you're managing okay. Concierge is with you.</p>
                    <p>In an emergency? Tell your Concierge! Concierge can dial emergency services, informing them
                        of your situation. Concierge keeps you safe.</p>
                    <flat-button clas="floating-button" text="Learn More" href="/usage"></flat-button>
                </div>
                <div class="column">
                    <div style="margin-left: 64px" class="image">
                        <img alt="" src="../assets/images/home/mountain.jpg">
                    </div>
                </div>
            </div>
        </section>

        <separator></separator>

        <section id="organisations">
            <div class="group">
                <div class="column">
                    <div style="margin-right: 64px" class="image">
                        <img alt="" src="../assets/images/home/mountain.jpg">
                    </div>
                </div>

                <div class="column content-column">
                    <p class="tagline">Concierge for organisations</p>
                    <h2>Integrate speech recognition into your ecosystem</h2>
                    <p>Concierge can help make your services available via speech commands with no hassle.</p>
                    <p>Concierge provides RESTful APIs to connect your services to its localised (yay privacy!)
                        speech and enquiry engine.</p>
                    <p>Once integrated, Concierge allows you to manage services, allowing for the addition of new
                        services as you create them.</p>
                    <p>View the documentation for more information on integrating your Concierge into your
                        workforce.</p>
                    <flat-button class="floating-button" text="View Documentation" href="/documentation"></flat-button>
                </div>
            </div>
        </section>

        <separator></separator>

        <section id="dev-blog">
            <div class="row">
                <p class="tagline">view our progress</p>
                <h2>Development Blog</h2>
                <p>Here are some extracts from our development blog posts. Head over to our
                    <v-link href="/blog" class="inline-link" text="blog"></v-link>
                    to read more about our development process.
                </p>
            </div>

            <div class="group">
                <div class="column">
                    <p class="tagline">Lorem Ipsum</p>
                    <h3>Gathering Requirements</h3>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut
                        labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco
                        laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in
                        voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat
                        cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
                    <flat-button class="central-button" text="Read more"
                                 href="/blog#gathering-requirements"></flat-button>
                </div>

                <v-separator></v-separator>

                <div class="column">
                    <p class="tagline">Lorem Ipsum</p>
                    <h3>Brainstorming</h3>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut
                        labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco
                        laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in
                        voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat
                        cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
                    <flat-button class="central-button" text="Read more"
                                 href="/blog#brainstorming"></flat-button>
                </div>

                <v-separator></v-separator>

                <div class="column">
                    <p class="tagline">Lorem Ipsum</p>
                    <h3>Planning</h3>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut
                        labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco
                        laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in
                        voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat
                        cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
                    <flat-button class="central-button" text="Read more"></flat-button>
                </div>
            </div>
        </section>

        <separator></separator>
    </page>
</template>

<script>
    import Page from "../Page.vue";
    import separator from "../components/layout/separator.vue";
    import VSeparator from "../components/layout/v-separator.vue";
    import FlatButton from "../components/flat-button.vue";
    import VLink from "./../components/v-link.vue";

    export default {
        name: "home",
        components: {Page, separator, FlatButton, VSeparator, VLink},
    }
</script>

<style scoped>
    section {
        padding-top: 8px;
    }

    .button-group {
        margin-top: 32px;
        margin-left: auto;
        margin-right: auto;
        display: flex;
        width: 50%;
    }

    .button-group a {
        flex: 1;
    }

    .button-group a:first-child {
        margin-right: 16px;
    }

    flat-button {
        margin-top: 16px;
    }

    .floating-button {
        float: right;
    }

    .central-button {
        float: none;
    }

    .content-column {
        display: block;
        flex: 0.5 !important;
    }

    .image img {
        background: var(--main);
    }

    @media (max-width: 1024px) {
        .floating-button {
            float: none !important;
            width: inherit;
        }

        .image {
            margin: 32px 0 0 !important;
        }

        .button-group {
            display: flex;
            flex-direction: column;
            width: 100%;
        }

        .button-group a:first-child {
            margin-right: 0;
            margin-bottom: 16px;
        }

        #organisations .group {
            flex-direction: column-reverse;
        }

        #dev-blog .group .column {
            margin: 16px 0 !important;
        }
    }
</style>