import api from "../../backend/api";
import router from "../../router/router";

const state = () => ({
    entered: false,
    newlyRegistered: false,
});

const getters = {
    entered: (state) => {
        return state.entered;
    },
    newlyRegistered: (state) => {
        return state.newlyRegistered;
    },
};

const actions = {
    async login({dispatch, commit, getters, rootGetters}, form) {
        const response = await api.login(form.usernameOrEmail, form.password);
        if (response.success) {
            await dispatch("admin/fetchAdmin", form.usernameOrEmail, {root: true});
        } else {
            form.response = response.message;
        }
    },
    async signup({dispatch, commit}, form) {
        const response = await api.register(form);
        if (response.success) {
            commit("newlyRegistered", true);
            dispatch("admin/fetchAdmin", form.username, {root: true});
        } else {
            form.response = response.message;
        }
    },
    async logout({commit}) {
        await api.logout();
        commit("admin/setAdmin", {}, {root: true});
        commit("member/setMembers", {}, {root: true});
        commit("member/setActiveId", null, {root: true});
        commit("member/setHistory", {}, {root: true});
        commit("member/setMemberServices", {}, {root: true});
        commit("service/setService", {}, {root: true});
        // TODO - Debug only
        commit("service/setServices", {}, {root: true});
        // END TODO;
        // NOTE - Reset these if profile pictures or service icons were added
        commit("media/setProfileImages", {}, {root: true});
        commit("media/setServiceIcons", {}, {root: true});
        commit("entered", false);
        await router.push("/welcome");
    },
};

const mutations = {
    entered(state, entered) {
        state.entered = entered;
    },
    newlyRegistered(state, newlyRegistered) {
        state.newlyRegistered = newlyRegistered;
    },
};

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations,
};
