import backend from "../../../assets/scripts/backend/backend";
import router from "../../../router/router";

const state = () => ({
    members: {},
    activeId: null,
    profilePicture: null,
});

const getters = {
    members: (state) => {
        return state.members;
    },
    activeMember: (state) => {
        return state.members[state.activeId];
    },
    activeId: (state) => {
        return state.activeId;
    },
    profilePicture: (state) => {
        return state.profilePicture;
    },
};

const actions = {
    async updateMember({ dispatch, commit, getters, rootGetters }, form) {
         backend.updateMember(form.id, form.firstName, form.lastName, form.prefix, form.phoneNumber, form.profilePicture);
      
        const members = backend.fetchMembers(rootGetters["admin/username"]);
        commit('setMembers', {});
        commit("setMembers", members);
        alert("Member data successfully updated.");
    },
    async removeMember({ dispatch, commit, getters, rootGetters }, userId) {
        commit('removeMember');
        backend.removeMember(userId);
        const members = backend.fetchMembers(rootGetters["admin/username"]);
        commit('setMembers', {});
        commit("setMembers", members);
        alert("Member successfully removed from your circle.");
        await router.push({
            name: "people",
        });
    },
    async addMember({ dispatch, commit, getters, rootGetters }, form) {
        const response = backend.addMember(
            form.firstName,
            form.lastName,
            form.prefix,
            form.phoneNumber,
            form.profilePicture
        );
        if (typeof response !== "string") {
            const members = backend.fetchMembers(rootGetters["admin/username"]);
            commit('setMembers', {});
            commit("setMembers", members);
            dispatch("activeMember", response);
            const newMember = members[response];
            const name = newMember.firstName + " " + newMember.lastName;
            await router.push({
                name: "user-details",
                params: {
                    person: name.replace(" ", "-").toLowerCase(),
                },
            });
        } else {
            form.response = response;
        }
    },
    activeMember({ dispatch, commit, getters, rootGetters }, id) {
        commit("setActiveId", id);
        commit("updateProfilePic", getters.activeMember);
    },
    setMembers({ commit }, members) {
        commit("setMembers", members);
    },
    async updateMemberPic({ dispatch, commit, getters, rootGetters }, newPic) {
        const username = rootGetters["account/username"];
        const userId = getters.activeId;
        const user = backend.updateMemberPicture(username, userId, newPic);
        commit("updateProfilePic", user);
    },
    async fetchMembers({ dispatch, commit, getters, rootGetters }, username) {
        commit("setMembers", backend.fetchMembers(username));
    },
};

const mutations = {
    setMembers(state, members) {
        state.members = members;
    },
    setActiveId(state, id) {
        state.activeId = id;
    },
    updateMember(state, member) {
        state.members[member.id] = member;
    },
    updateProfilePic(state, member) {
        state.members[member.id].profilePicture = member.profilePicture;
        state.profilePicture = member.profilePicture;
    },
    removeMember(state) {
        state.profilePicture = null;
        state.activeId = null;
    }
};

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations,
};
