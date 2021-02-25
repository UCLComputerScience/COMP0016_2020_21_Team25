import {store} from "../store/store";
import {getName} from "../assets/scripts/util";

export const routes = [
    {
        path: "/",
        redirect: (route) => {
            return store.getters["admin/loggedIn"]
                ? `/${store.getters["admin/username"]}/people`
                : "/welcome";
        },
    },
    {
        path: "/sign-up",
        redirect: "/welcome",
    },
    {
        path: "/register",
        redirect: "/welcome",
    },
    {
        path: "/sign-in",
        redirect: "/welcome",
    },
    {
        path: "/login",
        redirect: "/welcome",
    },
    {
        path: "/forgot-password",
        redirect: "/forgot",
    },
    {
        path: "/logout",
        redirect: () => {
            store.dispatch("account/logout");
            return "/welcome";
        },
    },
    {
        path: "/sign-out",
        redirect: () => {
            store.dispatch("account/logout");
            return "/welcome";
        },
    },
    {
        path: "/forgot",
        component: () =>
            import(
                /* webpackChunkName: "forgot-password", webpackPrefetch: true */ "../app/views/ForgotPassword.vue"
                ),
        name: "forgot-password",
        meta: {
            title: (route) => {
                return "Reset Your Password";
            },
            description:
                "Quickly gain access to your account again by resetting your password.",
        },
    },
    {
        path: "/:username/marketplace",
        component: () =>
            import(
                /* webpackChunkName: "marketplace", webpackPrefetch: true */ "../app/views/marketplace/Marketplace.vue"
                ),
        children: [
            {
                path: "",
                component: () =>
                    import(
                        /* webpackChunkName: "marketplace-view", webpackPrefetch: true */ "../app/views/marketplace/MarketplaceView.vue"
                        ),
                name: "marketplace",
                meta: {
                    title: (route) => {
                        return "Marketplace";
                    },
                    description:
                        "Find new services to simplify everyday life for the people in your circle.",
                    requiresAuth: true,
                },
            },
            {
                path: "service",
                component: () =>
                    import(
                        /* webpackChunkName: "service", webpackPrefetch: true */ "../app/views/marketplace/ServiceView.vue"
                        ),
                name: "service",
                meta: {
                    title: (route) => {
                        const title =
                            store.getters["service/activeService"]["service_name"];
                        if (title === undefined) {
                            return "";
                        }
                        return title;
                    },
                    description: "",
                    requiresAuth: true,
                },
                props: () => {
                    return store.getters["service/activeService"];
                },
            },
        ],
    },
    {
        path: "/:catchAll(.*)",
        component: () =>
            import(
                /* webpackChunkName: "not-found", webpackPrefetch: true */ "../app/views/NotFound.vue"
                ),
        name: "not-found",
        meta: {
            title: (route) => {
                return "Not Found";
            },
            description: "That page doesn't exist",
        },
    },
    {
        path: "/:username/people",
        component: () =>
            import(
                /* webpackChunkName: "people", webpackPrefetch: true */ "../app/views/people/PeopleDefault.vue"
                ),
        name: "people",
        meta: {
            title: (route) => {
                return "Your Circle";
            },
            description: "Manage the accounts of everyone in your circle.",
            requiresAuth: true,
        },
        children: [
            {
                path: ":person/manage",
                name: "user-details",
                component: () =>
                    import(
                        /* webpackChunkName: "people-details", webpackPrefetch: true */ "../app/views/people/user-views/user-details/UserDetails.vue"
                        ),
                meta: {
                    title: (route) => {
                        return getName() + " Details";
                    },
                    description: "Manage details for members in your circle",
                },
            },
            {
                path: ":person/services",
                name: "user-services",
                component: () =>
                    import(
                        /* webpackChunkName: "people-services", webpackPrefetch: true */ "../app/views/people/user-views/user-services/UserServices.vue"
                        ),
                meta: {
                    title: (route) => {
                        return getName() + " Services";
                    },
                    description:
                        "Manage and remove service data for members in your circle",
                },
            },
            {
                path: ":person/history",
                name: "user-history",
                component: () =>
                    import(
                        /* webpackChunkName: "history", webpackPrefetch: true */ "../app/views/people/user-views/user-history/UserHistory.vue"
                        ),
                meta: {
                    title: (route) => {
                        return getName() + " History";
                    },
                    description:
                        "View service usage history for members in your circle",
                },
            },
        ],
    },
    {
        path: "/:username/profile",
        component: () =>
            import(
                /* webpackChunkName: "profile", webpackPrefetch: true */ "../app/views/profile/Profile.vue"
                ),
        name: "profile",
        meta: {
            title: (route) => {
                return "Your Profile";
            },
            description: "Manage your profile.",
            requiresAuth: true,
        },
    },
    {
        path: "/welcome",
        component: () =>
            import(
                /* webpackChunkName: "welcome", webpackPrefetch: true */ "../app/views/welcome/Welcome.vue"
                ),
        name: "welcome",
        meta: {
            title: (route) => {
                return "Welcome";
            },
            description:
                "Log into your Concierge portal or register a new account.",
        },
    },
];
