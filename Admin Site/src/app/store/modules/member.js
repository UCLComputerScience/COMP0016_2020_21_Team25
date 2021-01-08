import backend from "../../../assets/scripts/backend/backend";

const state = () => ({
    members: {},
    activeId: null,
    profilePicture: null,
})

const getters = {
    members: state => {
        return state.members;
    },
    activeMember: state => {
        return state.members[state.activeId];
    },
    activeId: state => {
        return state.activeId;
    },
    profilePicture: state => {
        return state.profilePicture;
    },
}

const actions = {
    activeMember({dispatch, commit, getters, rootGetters}, id) {
        commit('setActiveId', id);
        commit('updateProfilePic', getters.activeMember);
    },
    setMembers({commit}, members) {
        commit('setMembers', members);
    },
    async updateMemberPic({dispatch, commit, getters, rootGetters}, newPic) {
        const username = rootGetters["account/username"];
        const userId = getters.activeId;
        const user = backend.updateMemberPicture(username, userId, newPic);
        commit('updateProfilePic', user);
    },
    async fetchMembers({dispatch, commit, getters, rootGetters}, username) {
        commit('setMembers', backend.fetchMembers(username));
    }
}

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
    }
}

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations
}