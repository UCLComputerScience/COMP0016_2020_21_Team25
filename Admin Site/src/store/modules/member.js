import toKebabCase from "webpack-cli/lib/utils/to-kebab-case.js";
import {members} from "../../../tests/unit/util/constants.js";
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
            const apiResponse = await api.addServiceToUser(member.id, serviceId);
            if (!apiResponse.success) {
                if (apiResponse.message === "Service is already assigned to user.") {
                    const message = `This service is already assigned to ${member["first-name"]} ${member["last-name"]}. No changes were made for this member.`;
                    alert(message);
                } else {
                    response.message = apiResponse.message;
                    response.success = false;
                    break;
                }
            }
        }
    },
    async updateMember({ dispatch, commit, getters, rootGetters }, form) {
        const member = getters.activeMember;
        for (const [key, field] of Object.entries(form)) {
            if (field === "") {
                form[key] = member[toKebabCase(key)];
            }
        }

        const newData = { ...toKebabCaseMap(form) };
        const response = await api.updateMember(getters["activeId"],
            newData["first-name"], newData["last-name"], newData["phone-number"],
            newData["prefix"], newData["profile-picture"]);
        form.success = response.success;
        form.response = response.message;
        if (response.success) {
            newData.id = getters["activeId"];
            commit("updateMember", newData);
        }
    },
    async removeMember({ dispatch, commit, getters, rootGetters }, userId) {
        const response = await api.removeMember(userId);
        if (response.success) {
            await commit("removeMember");
            alert("Member successfully removed from your circle.");
            await router.push({
                name: "people",
                username: rootGetters["admin/username"]
            });
        } else {
            alert(response.message);
        }
    },
    async addMember({ dispatch, commit, getters, rootGetters }, form) {
        const username = rootGetters["admin/username"];
        const newData = { username, ...form };
        const response = await api.addMember(newData);
        form.response = response.message;
        form.success = response.success;
        if (response.success) {
            await dispatch("fetchMembers", username);
            const newId = response["user-id"];
            const newMember = getters.members[newId];
            await dispatch("activeMember", newId);
            const name = newMember["first-name"] + " " + newMember["last-name"];
            await router.push({
                name: "user-details",
                params: {
                    username,
                    person: name.replace(/[ ]/g, "-").toLowerCase(),
                },
            });
            const firstName = newMember["first-name"];
            const words = response["registration-code"];
            alert(`${name} was added to your circle. Inform ${firstName} to enter the code: "${words[0]} ${words[1]} ${words[2]}" to activate their Concierge app.`);
        }
    },
    activeMember({ dispatch, commit, getters, rootGetters }, id) {
        commit("setActiveId", id);
    },
    async updateMemberPic({ dispatch, commit, getters, rootGetters }, newPic) {
        const member = { ...getters.activeMember };
        member["profile-picture"] = newPic;
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
        state.history = [...history];
    },
    setMemberServices(state, services) {
        state.memberServices = [...services];
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
