import Reference from "firebase-storage-lite";
import api from "../../backend/api";

const base = "gs://fise-concierge.appspot.com/";
const baseBucket = new Reference(base);
const imgFormat = ".webp";

async function getImage(src, bucket) {
    try {
        const imageRef = bucket.child(src + imgFormat);
        return await imageRef.getDownloadURL();
    } catch {
        return null;
    }
}

const fetchImages = async (folder, names) => {
    const map = {};
    const bucket = await baseBucket.child(folder + "/");
    for (const name of names) {
        map[name] = await getImage(name, bucket);
    }
    return map;
};

const state = () => ({
    profileImages: {},
    profileIds: {},
    serviceImages: {},
});

const getters = {
    profileImages: (state) => {
        return state.profileImages;
    },
    profileIds: (state) => {
        return state.profileIds
    },
    serviceIcons: (state) => {
        return state.serviceImages;
    },
};

const actions = {
    async setProfileImages({ dispatch, commit, getters, rootGetters }) {
        const imageNamesResponse = await api.profilePictures();
        if (!imageNamesResponse.success) {
            return;
        }
        commit("setProfileIds", imageNamesResponse["profile-pictures"]);
        const imageNames = Object.values(
            imageNamesResponse["profile-pictures"]
        );
        const profileImages = await fetchImages("profile-images", imageNames);
        commit("setProfileImages", profileImages);
    },
    async setServiceIcons({ dispatch, commit, getters, rootGetters }) {
        const services = rootGetters["service/allServices"];
        const imageNames = [];

        for (const [category, servicesInCategory] of Object.entries(services)) {
            for (const service of servicesInCategory) {
                if (!imageNames.includes(service.icon)) {
                    imageNames.push(service.icon)
                }
            }
        }
        const serviceImages = await fetchImages(
            "service-icons",
            imageNames
        );
        commit("setServiceIcons", serviceImages);
    },
};

const mutations = {
    setProfileImages(state, profileImages) {
        state.profileImages = profileImages;
    },
    setProfileIds(state, profileIds) {
        state.profileIds = profileIds;
    },
    setServiceIcons(state, serviceImages) {
        state.serviceImages = serviceImages;
    },
};

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations,
};
