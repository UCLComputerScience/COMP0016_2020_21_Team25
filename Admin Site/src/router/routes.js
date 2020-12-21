export const routes = [
    // TODO - This route should redirect to /people if the user is logged in
    {
        path: "/",
        redirect: "/welcome"
        // redirect: (route) => {
        //     return loggedIn() ? "/" + route.params.username : "/welcome"
        // }
    },
    {
        path: "/sign-up",
        redirect: "/welcome"
    },
    {
        path: "/register",
        redirect: "/welcome"
    },
    {
        path: "/sign-in",
        redirect: "/welcome"
    },
    {
        path: "/login",
        redirect: "/welcome"
    },
    {
        path: "/forgot",
        component: () => import(/* webpackChunkName: "forgot-password", webpackPrefetch: true */ "../app/views/ForgotPassword.vue"),
        name: "forgot-password",
        meta: {
            title: (route) => {
                return "Reset Your Password";
            },
            description: "Quickly gain access to your account again by resetting your password."
        },
    },
    {
        path: "/welcome",
        component: () => import(/* webpackChunkName: "welcome", webpackPrefetch: true */ "../app/views/welcome/Welcome.vue"),
        name: "welcome",
        meta: {
            title: (route) => {
                return "Welcome";
            },
            description: "Log into your Concierge portal or register a new account."
        },
    },
    {
        path: "/:username/people",
        component: () => import(/* webpackChunkName: "people", webpackPrefetch: true */ "../app/views/people/People.vue"),
        name: "people",
        meta: {
            title: (route) => {
                return "Your Circle";
            },
            description: "Manage the accounts of everyone in your circle.",
            requiresAuth: true,
        },
        children: []
    },
    {
        path: "/:username/marketplace",
        component: () => import(/* webpackChunkName: "marketplace", webpackPrefetch: true */ "../app/views/marketplace/Marketplace.vue"),
        name: "marketplace",
        meta: {
            title: (route) => {
                return "Marketplace";
            },
            description: "Find new services to simplify everyday life for the people in your circle.",
            requiresAuth: true,
        },
    },
    {
        path: "/:catchAll(.*)",
        component: () => import(/* webpackChunkName: "not-found", webpackPrefetch: true */ "../app/views/NotFound.vue"),
        name: "not-found",
        meta: {
            title: (route) => {
                return "Not Found";
            }, description: "That page doesn't exist"
        },
    },
];