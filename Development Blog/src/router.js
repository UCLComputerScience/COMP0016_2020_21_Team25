import {createRouter, createWebHistory} from "vue-router";
import {nextTick} from "@vue/runtime-core";

import Home from "./views/Home.vue";
import Documentation from "./views/Documentation.vue";
import Usage from "./views/Usage.vue";
import Blog from "./views/Blog.vue";
import NotFound from "./views/NotFound.vue";


import Installation from "./components/docs/pages/essentials/Installation.vue";
import Introduction from "./components/docs/pages/essentials/Introduction.vue";
import Default from "./components/docs/pages/Default.vue";

const routes = [
    {
        path: "/", name: "Home", component: Home,
        meta: {title: "Providing a helping hand through speech",
        description: "Concierge - providing a helping hand through speech."},
    },
    {
        path: "/documentation", name: "Documentation", component: Documentation,
        meta: {title: "Documentation", description: "You're browsing the documentation" +
                " for Concierge. Here, you can learn how to use Concierge to make your " +
                "services available via our voice assistant."},
        children: [
            {path: "", name: "", component: Default,
                meta: {title: 'Documentation', description: "You're browsing the documentation" +
                        " for Concierge. Here, you can learn how to use Concierge to make your " +
                        "services available via our voice assistant."}},
            {
                path: "installation", name: "Installation",
                component: Installation, meta: {title: 'Installation',
                    description: ""}
            },
            {
                path: "introduction", name: "Introduction", component: Introduction
                , meta: {title: 'Introduction',
                    description: ""}
            },
        ]
    },
    {path: "/usage", name: "Usage", component: Usage,
        meta: {title: "Usage", description: "Learn how Concierge can simplify your everyday life."},},
    {path: "/blog", name: "Blog", component: Blog,
        meta: {title: "Blog",  description: "Read about how we set about building Concierge."},},
    {path: "/:catchAll(.*)", component: NotFound,
        meta: {title: "Not Found", description: ""},},
];

const router = createRouter({
    history: createWebHistory(),
    scrollBehavior(to, from, savedPosition) {
        if (to.hash) {
            return window.scrollTo({
                top: document.querySelector(to.hash).offsetTop - 50,
                behavior: 'smooth'
            })
        } else {
            return {x: 0, y: 0}
        }
    },
    routes,
});

const DEFAULT_TITLE = 'Concierge | Providing a helping hand, through speech';
const DEFAULT_DESCRIPTION = 'Concierge - providing a helping hand through speech.';
router.afterEach((to, from) => {
    nextTick(() => {
        document.title = 'Concierge | ' + to.meta.title || DEFAULT_TITLE;
        let desc = document.querySelector('head meta[name="description"]');
        let content = to.meta.description || DEFAULT_DESCRIPTION;
        desc.setAttribute('content', content);
    });
});

export default router;