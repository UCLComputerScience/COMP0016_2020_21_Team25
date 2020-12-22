import {createRouter, createWebHistory} from "vue-router";
import {nextTick} from "@vue/runtime-core";

import $ from "jquery";
import {routes} from "./routes";
import {store} from "../app/store/store";

const router = createRouter({
    mode: 'history',
    history: createWebHistory(),
    scrollBehavior(to, from, savedPosition) {
        if (to.hash) {
            let section = document.querySelector(to.hash);
            $('html').animate({scrollTop: section.offsetTop}, 500);
            return false;
        } else {
            return {x: 0, y: 0}
        }
    },
    routes,
});

router.beforeEach((to, from, next) => {
    if (to.matched.some(record => record.meta.requiresAuth)) {
        if (!store.getters.loggedIn) {
            next({
                path: '/welcome',
            })
        } else {
            next()
        }
    } else {
        next()
    }
})

const DEFAULT_TITLE = 'Concierge Portal';
const DEFAULT_DESCRIPTION = 'Concierge - providing a helping hand through speech.';
router.afterEach((to, from) => {
    nextTick(() => {
        store.dispatch('route', to.path);
        document.title = to.meta.title(to) + ' — Concierge Portal' || DEFAULT_TITLE;
        let desc = document.querySelector('head meta[name="description"]');
        let content = to.meta.description || DEFAULT_DESCRIPTION;
        desc.setAttribute('content', content);
    });
});

export default router;