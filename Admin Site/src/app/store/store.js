import {createStore} from "vuex";
import createPersistedState from "vuex-persistedstate";
import router from "../../router/router";

export const store = createStore({
    strict: process.env.NODE_ENV !== 'production',
    state: {
        user: {},
    },
    mutations: {
        setUser(state, user) {
            state.user = user;
        },
    },
    actions: {
        async login({dispatch}, form) {
            const {user} = {
                username: form.usernameOrEmail,
                password: form.password
            };
            // check welcome in backend

            // fetch user profile and set in state
            dispatch('fetchUser', user);
        },
        async signup({dispatch}, form) {
            const {user} = {
                username: form.username,
                password: form.password,
                email: form.email,
                firstName: form.firstName,
                lastName: form.lastName,
                phoneNumber: form.phoneNumber
            }

            // create user object in backend


            // fetch user profile and set in state
            dispatch('fetchUser', user);
        },
        async fetchUser({commit}, user) {
            // fetch user profile from backend
            const userProfile = {};

            // set user profile in state
            commit('setUser', userProfile);

            if (router.currentRoute.path === '/welcome') {
                await router.push('/people');
            }
        },
        async logout({commit}) {
            // log user out in backend

            // clear user data from state
            commit('setUser', {});

            await router.push('/welcome');
        },
    },
    plugins: [createPersistedState({
        storage: window.sessionStorage,
    })]
})