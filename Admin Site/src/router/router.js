import {createRouter, createWebHistory} from "vue-router";
import {nextTick} from "@vue/runtime-core";

import {routes} from "./routes";
import {store} from "../store/store";

const router = createRouter({
    mode: "history",
    history: createWebHistory(),
    scrollBehavior(to, from, savedPosition) {
        if (to.hash) {
            return {selector: to.hash}
        } else if (savedPosition) {
            return savedPosition;
        } else {
            return {x: 0, y: 0}
        }
    },
    routes,
});

router.beforeEach((to, from, next) => {
    if (to.matched.some((record) => record.meta.requiresAuth)) {
        if (!store.getters["admin/loggedIn"]) {
            next({
                path: "/welcome",
            });
        } else {
            next();
        }
    } else {
        next();
    }
});

const DEFAULT_TITLE = "Concierge Portal";
const DEFAULT_DESCRIPTION =
    "Concierge - providing a helping hand through speech.";
router.afterEach((to, from) => {
    nextTick(() => {
        document.title =
            to.meta.title(to) + " — Concierge Portal" || DEFAULT_TITLE;
        const desc = document.querySelector('head meta[name="description"]');
        const content = to.meta.description || DEFAULT_DESCRIPTION;
        desc.setAttribute("content", content);
    });
});

export default router;
