import backend from "../../../assets/scripts/backend/backend";
import router from "../../../router/router";

const state = () => ({
    admin: {},
})

const getters = {
    admin: state => {
        return state.admin;
    },
    loggedIn: state => {
        return state.admin.username !== undefined;
    },
    username: state => {
        return state.admin.username;
    },
}

const actions = {
    async profile({dispatch, commit, getters, rootGetters}, form) {
        const user = getters.admin
        for (let [key, field] of Object.entries(form)) {
            if (field === "") {
                form[key] = user[key];
            }
        }
        const backendResponse = backend.updateProfile(user.username,
            form.firstName, form.lastName, form.phoneNumber, form.email,
            form.password);
        if (backendResponse === "Update success") {
            dispatch('fetchAdmin', user.username);
        } else {
            form.response = backendResponse;
        }
    },
    async fetchAdmin({dispatch, commit, getters, rootGetters}, usernameOrEmail) {
        dispatch('updateAdmin', usernameOrEmail);
        dispatch('member/fetchMembers', null, {root: true});
        if (window.location.pathname === '/welcome') {
            await router.push({name: "people", params: {username: getters.username}});
        }
    },
    async updateAdmin({commit}, usernameOrEmail) {
        commit('setAdmin', backend.fetchAdmin(usernameOrEmail));
    },
    async updateAdminPic({dispatch, commit, getters, rootGetters}, newPic) {
        const image = backend.updateAdminPicture(getters.username, newPic);
        commit('updateProfilePic', image);
    },
}

const mutations = {
    setAdmin(state, admin) {
        state.admin = admin;
    },
    updateProfilePic(state, newPic) {
        state.admin.profilePicture = newPic;
    }
}

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations
}