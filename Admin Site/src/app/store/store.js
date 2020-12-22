import {createStore} from "vuex";
import createPersistedState from "vuex-persistedstate";
import router from "../../router/router";
import backend from "../../assets/scripts/backend/backend";


export const store = createStore({
    strict: process.env.NODE_ENV !== 'production',
    state: {
        user: {},
        route: "/",
        entered: false,
        newlyRegistered: false,
    },
    mutations: {
        setUser(state, user) {
            state.user = user;
        },
        setRoute(state, route) {
            state.route = route;
        },
        entered(state, entered) {
            state.entered = entered;
        },
        newlyRegistered(state, newlyRegistered) {
            state.newlyRegistered = newlyRegistered;
        }
    },
    getters: {
        user: state => {
            return state.user;
        },
        loggedIn: state => {
            return state.user.username !== undefined;
        },
        username: state => {
            return state.user.username;
        },
        route: state => {
            return state.route;
        },
        entered: state => {
            return state.entered;
        },
        newlyRegistered: state => {
            return state.newlyRegistered;
        }
    },
    actions: {
        route({commit}, route) {
            commit('setRoute', route)
        },
        entered({commit}, entered) {
            commit("entered", entered);
        },
        async login({dispatch}, form) {
            const backendResponse = backend.login(form.usernameOrEmail, form.password);
            if (backendResponse === "Login success") {
                dispatch('fetchUser', form.usernameOrEmail);
            } else {
                form.response = backendResponse;
            }
        },
        async signup({dispatch}, form) {
            const backendResponse = backend.register(form.username, form.firstName,
                form.lastName, form.email, form.phoneNumber, form.password);
            if (backendResponse === "Registration success") {
                dispatch('completeSignup');
                dispatch('fetchUser', form.username);
            } else {
                form.response = backendResponse;
            }
        },
        async completeSignup({commit}) {
            commit('newlyRegistered', true);
        },
        async welcomed({commit}) {
            commit('newlyRegistered', false);
        },
        async fetchUser({dispatch}, usernameOrEmail) {
            dispatch('updateUser', usernameOrEmail);
            if (this.getters.route === '/welcome') {
                await router.push({name: "people", params: {username: this.getters.username}});
            }
        },
        async updateUser({commit}, usernameOrEmail) {
            const user = backend.fetchAdmin(usernameOrEmail);
            commit('setUser', user);
        },
        async logout({commit}) {
            backend.logout();
            commit('setUser', {});
            commit('entered', false);
            await router.push('/welcome');
        },
    },
    plugins: [createPersistedState({
        storage: window.localStorage,
    })]
})