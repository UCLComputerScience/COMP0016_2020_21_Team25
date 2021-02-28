import api from "../../backend/api";
import router from "../../router/router";

const state = () => ({
    members: {},
    memberIds: [],
    history: [],
    memberServices: [],
    activeId: null,
});

const getters = {
    members: (state) => {
        return state.members;
    },
    memberIds: (state) => {
        return state.memberIds;
    },
    activeMember: (state) => {
        return state.members[state.activeId];
    },
    activeId: (state) => {
        return state.activeId;
    },
    history: (state) => {
        return state.history;
    },
    memberServices: (state) => {
        return state.memberServices;
    }
};

const actions = {
    async fetchMemberServices({dispatch, commit, getters, rootGetters}) {
        const response = await api.memberServices(getters.activeId);
        if (response.code === 200) {
            commit("setMemberServices", response.services);
        } else {
            alert(response.message);
        }
    },
    async fetchHistory({dispatch, commit, getters, rootGetters}) {
        const response = await api.memberHistory(getters.activeId);
        if (response.code === 200) {
            commit("setHistory", response.history);
        } else {
            alert(response.message);
        }
    },
    async removeServiceFromMember(
        {dispatch, commit, getters, rootGetters}, serviceID
    ) {
        const response = await api.removeServiceFromUser(getters.activeId, serviceID);
        if (response.code === 200) {
            alert("Service successfully removed.");
        } else {
            alert(response.message);
        }
    },
    async addServiceToMembers(
        {dispatch, commit, getters, rootGetters},
        {serviceId, members, response}
    ) {
        for (const member of members) {
            const apiResponse = await api.addServiceToUser(
                member.id,
                serviceId
            );
            if (apiResponse.code !== 200) {
                response.message = apiResponse.message;
                response.success = false;
                break;
            }
        }
    },
    async updateMember({dispatch, commit, getters, rootGetters}, form) {
        const member = getters.activeMember;
        for (let [key, field] of Object.entries(form)) {
            if (field === "") {
                form[key] = member[key];
            }
        }
        form.userID = form.id;
        const response = await api.updateMember(form);
        if (response.code === 200) {
            commit("updateMember", form);
        } else {
            form.response = response.message;
        }
    },
    async removeMember({dispatch, commit, getters, rootGetters}, userId) {
        const response = await api.removeMember(userId);
        if (response.code === 200) {
            await commit("removeMember");
            alert("Member successfully removed from your circle.");
            await router.push({
                name: "people",
            });
        } else {
            alert(response.message);
        }
    },
    async addMember({dispatch, commit, getters, rootGetters}, form) {
        form.username = rootGetters["admin/username"];
        const response = await api.addMember(form);
        if (response.code === 200) {
            await dispatch("fetchMembers", form.username);
            const newId = response["user-id"];
            const newMember = getters.members[newId];
            await dispatch("activeMember", newId);
            const name = newMember.firstName + " " + newMember.lastName;
            await router.push({
                name: "user-details",
                params: {
                    person: name.replaceAll(" ", "-").toLowerCase(),
                },
            });
        } else {
            form.response = response.message;
        }
    },
    activeMember({dispatch, commit, getters, rootGetters}, id) {
        commit("setActiveId", id);
    },
    async updateMemberPic({dispatch, commit, getters, rootGetters}, newPic) {
        const member = {...getters.activeMember};
        member.profilePicture = newPic;
        await dispatch("updateMember", member);
    },
    async fetchMembers({dispatch, commit, getters, rootGetters}, username) {
        const response = await api.members(username);
        if (response.code === 200) {
            commit("setMembers", response.members);
        } else {
            alert(response.message);
        }
    },
};

const mutations = {
    setMembers(state, members) {
        state.members = Object.assign({}, members);
        state.memberIds = [];
        for (const strKey of Object.keys(members)) {
            state.memberIds.push(parseInt(strKey));
        }
    },
    setActiveId(state, id) {
        state.activeId = id;
    },
    setHistory(state, history) {
        state.history = Object.assign({}, history);
    },
    setMemberServices(state, services) {
        state.memberServices = Object.assign({}, services);
    },
    updateMember(state, member) {
        const allMembers = {...state.members};
        allMembers[member.id] = member;
        state.members = Object.assign({}, allMembers);
    },
    removeMember(state, id) {
        const index = state.memberIds.indexOf(id);
        state.memberIds.splice(index, 1);
        state.activeId = null;
    },
};

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations,
};