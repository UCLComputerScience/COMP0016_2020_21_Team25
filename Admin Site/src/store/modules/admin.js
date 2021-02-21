import api from "../../backend/api";
import router from "../../router/router";

const state = () => ({
    admin: {},
});

const getters = {
    admin: (state) => {
        return state.admin;
    },
    loggedIn: (state) => {
        return state.admin.username !== undefined;
    },
    username: (state) => {
        return state.admin.username;
    },
};

const actions = {
    async profile({dispatch, commit, getters, rootGetters}, form) {
        const admin = {...getters.admin};
        for (let [key, field] of Object.entries(form)) {
            if (field === "") {
                form[key] = admin[key];
            }
        }
        form.username = admin.username;
        const response = await api.updateAdmin(form);
        if (response.code === 200) {
            commit("setAdmin", form);
        } else {
            form.response = response.message;
        }
    },
    async fetchAdmin(
        {dispatch, commit, getters, rootGetters},
        usernameOrEmail
    ) {
        await dispatch("updateAdmin", usernameOrEmail);
        const username = getters.username;
        await dispatch("member/fetchMembers", username, {root: true});
        if (window.location.pathname === "/welcome") {
            router.push({
                name: "people",
                params: {username},
            });
        }
    },
    async updateAdmin({commit}, usernameOrEmail) {
        const response = await api.admin(usernameOrEmail);
        if (response.code === 200) {
            commit("setAdmin", response.data);
        } else {
            alert(response.message);
        }
    },
    async updateAdminPic({dispatch, commit, getters, rootGetters}, newPic) {
        const form = {...getters.admin};
        form.profilePicture = newPic;
        await dispatch("profile", form);
    },
};

const mutations = {
    setAdmin(state, admin) {
        state.admin = Object.assign({}, admin);
    },
    updateProfilePic(state, newPic) {
        state.admin.profilePicture = newPic;
    },
};

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations,
};
