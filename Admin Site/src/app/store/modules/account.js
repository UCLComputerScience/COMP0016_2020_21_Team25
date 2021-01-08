import backend from "../../../assets/scripts/backend/backend";
import router from "../../../router/router";

const state = () => ({
    entered: false,
    newlyRegistered: false,
})

const getters = {
    entered: state => {
        return state.entered;
    },
    newlyRegistered: state => {
        return state.newlyRegistered;
    },
}

const actions = {
    async login({dispatch}, form) {
        const backendResponse = backend.login(form.usernameOrEmail, form.password);
        if (backendResponse === "Login success") {
            dispatch('admin/fetchAdmin', form.usernameOrEmail, {root: true});
        } else {
            form.response = backendResponse;
        }
    },
    async signup({dispatch, commit}, form) {
        const backendResponse = backend.register(form.username, form.firstName,
            form.lastName, form.email, form.phoneNumber, form.password);
        if (backendResponse === "Registration success") {
            commit('newlyRegistered', true);
            dispatch('admin/fetchAdmin', form.username, {root: true});
        } else {
            form.response = backendResponse;
        }
    },
    logout({commit}) {
        backend.logout();
        commit('admin/setAdmin', {}, {root: true});
        commit('member/setMembers', {}, {root: true});
        commit('member/setActiveId', null, {root: true});
        commit('service/setService', {}, {root: true});
        commit('entered', false);
        router.push('/welcome');
    },
}

const mutations = {
    entered(state, entered) {
        state.entered = entered;
    },
    newlyRegistered(state, newlyRegistered) {
        state.newlyRegistered = newlyRegistered;
    },
}

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations
}