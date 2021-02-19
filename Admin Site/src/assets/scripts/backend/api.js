import axios from "axios";

const BASE_URL = "http://localhost:8080/";

async function makeHttpRequest(URL, method, params = {}) {
    let options = {
        method: method.toUpperCase(),
        url: URL,
        baseURL: BASE_URL,
        headers: {
            Accept: "application/json",
            "Content-Type": "application/json;charset=UTF-8",
        },
        data: params,
    };
    const response = await axios(options);
    if (response && response.status === 200) {
        return await response.data;
    }
    return {
        status: response.status,
        statusText: response.statusText,
    };
}

const api = {
    /**
     * TODO - ping server to see if it is running and return true/false
     */
    async ping() {},
    async login(username, password) {
        const response = await makeHttpRequest("login", "GET", {
            username,
            password,
        });
    },
    async register({
        username,
        firstName,
        lastName,
        email,
        phoneNumber,
        password,
    }) {
        const response = await makeHttpRequest("register", "POST", {
            username: username,
            "first-name": firstName,
            "last-name": lastName,
            email: email,
            "phone-number": phoneNumber,
            password: password,
        });
    },
    async admin(username) {
        const response = await makeHttpRequest("admin", "GET", {
            username,
        });
    },
    async members(username) {
        const response = await makeHttpRequest("members", "GET", {
            username: username,
        });
    },
    async memberServices(userID) {
        const response = await makeHttpRequest("member-services", "GET", {
            "user-id": userID,
        });
    },
    async serviceCategories() {
        const response = await makeHttpRequest("service-categories", "GET", {});
        return response.categories;
    },
    async profilePictures() {
        const response = await makeHttpRequest("profile-pictures", "GET", {});
    },
    async servicesInCategory(category) {
        const response = await makeHttpRequest("services-in-category", "GET", {
            category,
        });
        return response.services;
    },
    async addServiceToUser(userID, serviceID) {
        const response = makeHttpRequest("add-service-to-user", "POST", {
            "user-id": userID,
            "service-id": serviceID,
        });
    },
    async updateAdmin({
        username,
        firstName,
        lastName,
        email,
        phoneNumber,
        password,
    }) {
        const response = await makeHttpRequest("update-admin", "POST", {
            username: username,
            "first-name": firstName,
            "last-name": lastName,
            email: email,
            "phone-number": phoneNumber,
            password: password,
        });
    },
    async addMember({
        username,
        firstName,
        lastName,
        phoneNumber,
        prefix,
        profilePicture,
    }) {
        const response = await makeHttpRequest("add-member", "POST", {
            username: username,
            "first-name": firstName,
            "last-name": lastName,
            "phone-number": phoneNumber,
            prefix: prefix,
            "profile-picture": profilePicture,
        });
    },
    async removeMember(userID) {
        const response = makeHttpRequest("remove-member", "DELETE", {
            "user-id": userID,
        });
    },
    async updateMember({
        userID,
        firstName,
        lastName,
        phoneNumber,
        prefix,
        profilePicture,
    }) {
        const response = await makeHttpRequest("update-member", "POST", {
            "user-id": userID,
            "first-name": firstName,
            "last-name": lastName,
            "phone-number": phoneNumber,
            prefix: prefix,
            "profile-picture": profilePicture,
        });
    },
    async memberHistory(userID) {
        const response = await makeHttpRequest("member-history", "GET", {
            "user-id": userID,
        });
    },
};

export default api;
