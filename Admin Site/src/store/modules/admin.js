import {toKebabCase, toKebabCaseMap} from "../../assets/scripts/util";
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
                form[key] = admin[toKebabCase(key)];
            }
        }
        if (form["profilePicture"] === undefined &&
            form["profile-picture"] === undefined) {
            form["profile-picture"] = admin["profile-picture"];
        }
        const newData = { ...toKebabCaseMap(form) };
        newData.username = admin.username;
        const response = await api.updateAdmin(newData.username,
            newData["first-name"], newData["last-name"], newData["email"],
            newData["phone-number"], newData["password"],
            newData["profile-picture"]);
        form.response = response.message;
        form.success = response.success;
        if (response.success) {
            commit("setAdmin", newData);
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
        form["profile-picture"] = newPic;
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
