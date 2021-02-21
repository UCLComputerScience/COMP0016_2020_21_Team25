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
};

const actions = {
    service({commit}, service) {
        commit("setService", service);
    },
    async setServices({commit}) {
        const response = await api.serviceCategories();
        if (response.code === 200) {
            const services = {};
            for (const category of response.categories) {
                const serviceResponse = await api.servicesInCategory(category);
                if (serviceResponse.code === 200) {
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
