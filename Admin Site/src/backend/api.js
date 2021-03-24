import axios from "axios";
import {toKebabCaseMap, toSnakeCaseMap} from "../assets/scripts/util";

const PORT = 8100;
const BASE_URL = `http://localhost:${PORT}/`;

async function makeHttpRequest(URL, method, params = {}) {
    const formattedParams = toSnakeCaseMap(params);
    const options = {
        method: method.toUpperCase(),
        url: URL,
        baseURL: BASE_URL,
        headers: {
            Accept: "application/json",
            "Content-Type": "application/json;charset=UTF-8",
            "Access-Control-Allow-Origin": "*",
        },
        params: formattedParams,
    };
    const response = await axios(options);
    if (response && response.status === 200) {
        const data = await response.data;
        return toKebabCaseMap(data);
    }
    return {
        status: response.status,
        statusText: response.statusText,
        success: false
    };
}

const MOCK_RESPONSE = {
    message: "",
    code: 200,
    success: true,
};

const api = {
    async ping() {
        return await makeHttpRequest("", "GET", {});
    },
    async login(username, password) {
        return await makeHttpRequest("login", "GET", {
            "username-or-email": username,
            password,
        });
    },
    async logout() {

    },
    async register({ username, firstName, lastName, email, phoneNumber, password, }) {
        return await makeHttpRequest("register", "POST", {
            username: username,
            "first-name": firstName,
            "last-name": lastName,
            email: email,
            "phone-number": phoneNumber,
            password: password,
        });
    },
    async admin(username) {
        return await makeHttpRequest("admin", "GET", {
            username: username,
        });
    },
    async members(username) {
        return await makeHttpRequest("members", "GET", {
            username,
        });
    },
    async memberServices(userID) {
        return await makeHttpRequest("member-services", "GET", {
            "user-id": userID,
        });
    },
    async serviceCategories() {
        return await makeHttpRequest("service-categories", "GET", {});
    },
    async profilePictures() {
        return await makeHttpRequest("profile-pictures", "GET", {});
    },
    async servicesInCategory(category) {
        return await makeHttpRequest("services-in-category", "GET", {
            category,
        });
    },
    async addServiceToUser(userID, serviceID) {
        return makeHttpRequest("add-service-to-user", "POST", {
            "user-id": userID, "service-id": serviceID,
        });
    },
    async removeServiceFromUser(userID, serviceID) {
        return makeHttpRequest("remove-service-from-user", "POST", {
            "user-id": userID, "service-id": serviceID,
        });
    },
    async updateAdmin(username, firstName, lastName, email, phoneNumber, password, profilePicture) {
        return await makeHttpRequest("update-admin", "POST", {
            username,
            "first-name": firstName,
            "last-name": lastName,
            email: email,
            "phone-number": phoneNumber,
            password: password,
            "picture_id": profilePicture
        });
    },
    async addMember({ username, firstName, lastName, phoneNumber, prefix, profilePicture }) {
        return await makeHttpRequest("add-member", "POST", {
            username,
            "first-name": firstName,
            "last-name": lastName,
            "phone-number": phoneNumber,
            prefix,
            "picture_id": profilePicture,
        });
    },
    async removeMember(userID) {
        return makeHttpRequest("remove-member", "DELETE", {
            "user-id": userID,
        });
    },
    async updateMember(userID, firstName, lastName, phoneNumber, prefix, profilePicture ) {
        return await makeHttpRequest("update-member", "POST", {
            "user-id": userID,
            "first-name": firstName,
            "last-name": lastName,
            "phone-number": phoneNumber,
            prefix,
            "picture_id": profilePicture,
        });
    },
    async memberHistory(userID) {
        return await makeHttpRequest("member-history", "GET", {
            "user-id": userID,
        });
    },
    async memberServiceData(userID, serviceId) {
        // return await makeHttpRequest("member-service-data", "GET", {
        //     "user-id": userID,
        //     "service-id": serviceId
        // });
        const response = { ...MOCK_RESPONSE };
        // const random = Math.random();
        // if (random < 0.33) {
        //     response.fields = {
        //         "ACCOUNT_NUMBER": 123456789101112, "SORT_CODE": 123456
        //     };
        // } else if (random >= 0.33 && random < 0.67) {
        //     response.fields = {
        //         "GP_PHONE_NUMBER": "07111111111",
        //     };
        // } else {
        //     response.fields = {};
        // }
        response.fields = {};
        return response;
    },
    async updateMemberServiceData(userID, serviceId, data) {
        // return await makeHttpRequest("update-member-service-data", "POST", {
        //     "user-id": userID,
        //     "service-id": serviceId,
        //     data
        // });
        return MOCK_RESPONSE;
    }
};

export default api;
