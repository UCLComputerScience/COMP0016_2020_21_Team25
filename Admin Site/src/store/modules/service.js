import api from "../../backend/api";

const state = () => ({
    activeService: {},
    services: {},
});

const getters = {
    activeService: (state) => {
        return state.activeService;
    },
    allServices: (state) => {
        return state.services;
    },
    dataFields: (state, getters, rootState, rootGetters) => async (serviceId) => {
        const userID = rootGetters["member/activeId"];
        const response = await api.memberServiceData(userID, serviceId);
        if (response.success) {
            return response.fields;
        }
        alert(response.message);
        return {};
    }
};

const actions = {
    async updateDataFields({ dispatch, commit, getters, rootGetters }, form) {
        const defaultData = await getters["dataFields"](form.serviceId);
        for (const [key, value] of Object.entries(form)) {
            if (value === "" || value === null) {
                form[key] = defaultData[key];
            }
        }
        const userID = rootGetters["member/activeId"];
        const response = await api.updateMemberServiceData(userID, form.serviceId, form);
        if (response.success) {
            alert("Service data successfully updated for the assigned member.");
        } else {
            alert(response.message);
        }
    },
    service({ commit }, service) {
        commit("setService", service);
    },
    async setServices({ commit }) {
        const response = await api.serviceCategories();
        if (response.success) {
            const services = {};
            for (const category of response.categories) {
                const serviceResponse = await api.servicesInCategory(category);
                if (serviceResponse.success) {
                    services[category] = serviceResponse.services;
                } else {
                    alert(serviceResponse.message);
                    return;
                }
            }
            commit("setServices", services);
        } else {
            alert(response.message);
        }
    },
};

const mutations = {
    setService(state, service) {
        state.activeService = service;
    },
    setServices(state, services) {
        state.services = services;
    },
};

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations,
};
