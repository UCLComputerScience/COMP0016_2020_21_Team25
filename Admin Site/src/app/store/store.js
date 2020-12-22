import {createStore} from "vuex";
import createPersistedState from "vuex-persistedstate";
import router from "../../router/router";

export const store = createStore({
    strict: process.env.NODE_ENV !== 'production',
    state: {
        user: {},
        route: "/"
    },
    mutations: {
        setUser(state, user) {
            state.user = user;
        },
        setRoute(state, route) {
            state.route = route;
        }
    },
    getters: {
        loggedIn: state => {
            return state.user.username !== undefined;
        },
        username: state => {
            return state.user.username;
        },
        route: state => {
            return state.route;
        }
    },
    actions: {
        route({commit}, route) {
            commit('setRoute', route)
        },
        async login({dispatch}, form) {
            const user = {
                username: form.usernameOrEmail,
                password: form.password
            };
            // check login in backend
            const response = "Login success";
            if (response === "Login success") {
                dispatch('fetchUser', user);
                return null;
            } else {
                return response
            }
        },
        async signup({dispatch}, form) {
            const user = {
                username: form.username,
                password: form.password,
                email: form.email,
                firstName: form.firstName,
                lastName: form.lastName,
                phoneNumber: form.phoneNumber
            }
            // Register new user in backend
            const response = "Registration success";
            if (response === "Registration success") {
                dispatch('fetchUser', user);
                return null;
            } else {
                return response;
            }
        },
        async fetchUser({commit}, user) {
            // TODO Backend returns username in case usernameOrEmail is their email
            // fetch user profile from backend
            const userProfile = {
                username: "ernest",
                password: "12345",
                email: "you@mail.co.uk",
                firstName: "Ernest",
                lastName: "Badu",
                phoneNumber: "07111111111"
            };

            commit('setUser', userProfile);
            if (this.getters.route === '/welcome') {
                await router.push({name: "people", params: {username: userProfile.username}});
            }
        },
        async logout({commit}) {
            // log user out in backend if needed

            commit('setUser', {});
            await router.push('/welcome');
        },
    },
    plugins: [createPersistedState({
        storage: window.localStorage,
    })]
})