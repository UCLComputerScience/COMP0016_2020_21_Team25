import {toKebabCaseMap} from "../../assets/scripts/util";
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
    async profile({ dispatch, commit, getters, rootGetters }, form) {
        const admin = { ...getters.admin };
        for (const [key, field] of Object.entries(form)) {
            if (field === "") {
                form[key] = admin[key];
            }
        }
        if (form["profilePicture"] === undefined) {
            form["profilePicture"] = admin["profile-picture"];
        }
        form.username = admin.username
        form = {...toKebabCaseMap(form)};
        const response = await api.updateAdmin(form.username,
            form["first-name"], form["last-name"], form["email"], form["phone-number"],
            form["password"], form["profile-picture"]);
        if (response.success) {
            commit("setAdmin", form);
        } else {
            form.response = response.message;
        }
    },
    async fetchAdmin(
        { dispatch, commit, getters, rootGetters },
        usernameOrEmail
    ) {
        await dispatch("updateAdmin", usernameOrEmail);
        const username = getters.username;
        await dispatch("member/fetchMembers", username, { root: true });
        if (window.location.pathname === "/welcome") {
            await router.push({
                name: "people",
                params: { username },
            });
        }
    },
    async updateAdmin({ commit }, usernameOrEmail) {
        const response = await api.admin(usernameOrEmail);
        if (response.success) {
            const data = { ...response.data };
            data["username"] = usernameOrEmail;
            commit("setAdmin", data);
        } else {
            alert(response.message);
        }
    },
    async updateAdminPic({ dispatch, commit, getters, rootGetters }, newPic) {
        const form = { ...getters.admin };
        form.profilePicture = newPic;
        await dispatch("profile", form);
    },
};

const mutations = {
    setAdmin(state, admin) {
        state.admin = Object.assign({}, admin);
    },
    updateProfilePic(state, newPic) {
        state.admin["profile-picture"] = newPic;
    },
};

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations,
};
