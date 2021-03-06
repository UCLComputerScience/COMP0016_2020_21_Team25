import {toKebabCaseMap} from "../../assets/scripts/util";
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
    async fetchMemberServices({ dispatch, commit, getters, rootGetters }, userId = undefined) {
        const id = (userId === undefined) ? getters.activeId : userId;
        const response = await api.memberServices(id);
        if (response.success) {
            commit("setMemberServices", response.services);
        } else {
            alert(response.message);
        }
    },
    async fetchHistory({ dispatch, commit, getters, rootGetters }) {
        const response = await api.memberHistory(getters.activeId);
        if (response.success) {
            commit("setHistory", response.history);
        } else {
            alert(response.message);
        }
    },
    async removeServiceFromMember(
        { dispatch, commit, getters, rootGetters }, serviceID
    ) {
        const response = await api.removeServiceFromUser(getters.activeId, serviceID);
        if (response.success) {
            alert("Service successfully removed.");
        } else {
            alert(response.message);
        }
    },
    async addServiceToMembers(
        { dispatch, commit, getters, rootGetters },
        { serviceId, members, response }
    ) {
        for (const member of members) {
            const apiResponse = await api.addServiceToUser(
                member.id,
                serviceId
            );
            if (!apiResponse.success) {
                response.message = apiResponse.message;
                response.success = false;
                break;
            }
        }
    },
    async updateMember({ dispatch, commit, getters, rootGetters }, form) {
        const member = getters.activeMember;
        for (const [key, field] of Object.entries(form)) {
            if (field === "") {
                form[key] = member[key];
            }
        }
        form = {...toKebabCaseMap(form)};
        const response = await api.updateMember(getters["activeId"],
            form["first-name"], form["last-name"], form["phone-number"],
            form["prefix"], form["profile-picture"]);
        if (response.success) {
            commit("updateMember", form);
        } else {
            form.response = response.message;
        }
    },
    async removeMember({ dispatch, commit, getters, rootGetters }, userId) {
        const response = await api.removeMember(userId);
        if (response.success) {
            await commit("removeMember");
            alert("Member successfully removed from your circle.");
            await router.push({
                name: "people",
            });
        } else {
            alert(response.message);
        }
    },
    async addMember({ dispatch, commit, getters, rootGetters }, form) {
        form.username = rootGetters["admin/username"];
        const response = await api.addMember(form);
        if (response.success) {
            await dispatch("fetchMembers", form.username);
            const newId = response["user-id"];
            const newMember = getters.members[newId];
            await dispatch("activeMember", newId);
            const name = newMember["first-name"] + " " + newMember["last-name"];
            await router.push({
                name: "user-details",
                params: {
                    person: name.replaceAll(" ", "-").toLowerCase(),
                },
            });
            const firstName = newMember["first-name"];
            const words = response["registration-code"]
            alert(`${name} was added to your circle. Inform ${firstName} to enter the code: "${words[0]} ${words[1]} ${words[2]}" to activate their Concierge app.`);
        } else {
            form.response = response.message;
        }
    },
    activeMember({ dispatch, commit, getters, rootGetters }, id) {
        commit("setActiveId", id);
    },
    async updateMemberPic({ dispatch, commit, getters, rootGetters }, newPic) {
        const member = { ...getters.activeMember };
        member["profilePicture"] = newPic;
        await dispatch("updateMember", member);
    },
    async fetchMembers({ dispatch, commit, getters, rootGetters }, username) {
        const response = await api.members(username);
        if (response.success) {
            commit("setMembers", response.members);
        } else {
            alert(response.message);
        }
    },
};

const mutations = {
    setMembers(state, members) {
        state.members = Object.assign({}, members);
        state.memberIds = Object.keys(members);
    },
    setActiveId(state, id) {
        state.activeId = id;
        state.activeMember = state["members"][id];
    },
    setHistory(state, history) {
        state.history = Object.assign({}, history);
    },
    setMemberServices(state, services) {
        state.memberServices = Object.assign({}, services);
    },
    updateMember(state, member) {
        const allMembers = { ...state.members };
        allMembers[member.id] = member;
        state.members = Object.assign({}, allMembers);
        state.activeMember = state["members"][member.id];
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
