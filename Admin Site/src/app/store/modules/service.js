const state = () => ({
    activeService: {}
})

const getters = {
    activeService: state => {
        return state.activeService;
    },
}

const actions = {
    service({commit}, service) {
        commit('setService', service);
    },
}

const mutations = {
    setService(state, service) {
        state.activeService = service;
    },
}

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations
}