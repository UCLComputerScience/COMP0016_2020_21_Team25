import axios from "axios";

const PORT = 8080;
const BASE_URL = `http://localhost:${PORT}/`;

async function makeHttpRequest(URL, method, params = {}) {
    const options = {
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

const MOCK_RESPONSE = {
    message: "",
    code: 200,
};

const api = {
    /**
     * TODO - ping server to see if it is running and return true/false
     */
    async ping() {
    },
    async login(username, password) {
        // const response = await makeHttpRequest("login", "GET", {
        //     username,
        //     password,
        // });
        admin = {
            username: "ernest",
            firstName: "Ernest",
            lastName: "Badu",
            email: "you@mail.co.uk",
            phoneNumber: "07111111111",
            profilePicture: "apples",
            password: "12345",
        };

        members = {
            1: {
                id: 1,
                firstName: "Jane",
                lastName: "Williams",
                phoneNumber: "07111111111",
                prefix: "Ms",
                profilePicture: "apples",
            },
            2: {
                id: 2,
                firstName: "Margaret",
                lastName: "Phillips",
                phoneNumber: "07111111111",
                prefix: "Mrs",
                profilePicture: "hills",
            },
            3: {
                id: 3,
                firstName: "Mark",
                lastName: "Phillips",
                phoneNumber: "07111111111",
                prefix: "Mr",
                profilePicture: "tulip",
            },
            4: {
                id: 4,
                firstName: "George",
                lastName: "Best",
                phoneNumber: "07111111111",
                prefix: "Mr",
                profilePicture: "mountains",
            },
        };
        return MOCK_RESPONSE;
    },
    async logout() {
        admin = {};
        members = {};
        ids = [];
    },
    async register({
                       username,
                       firstName,
                       lastName,
                       email,
                       phoneNumber,
                       password,
                   }) {
        // const response = await makeHttpRequest("register", "POST", {
        //     username: username,
        //     "first-name": firstName,
        //     "last-name": lastName,
        //     email: email,
        //     "phone-number": phoneNumber,
        //     password: password,
        // });
        admin = {
            username,
            firstName,
            lastName,
            email,
            phoneNumber,
            password,
            profilePicture: "mountains",
        };
        return MOCK_RESPONSE;
    },
    async admin(username) {
        // const response = await makeHttpRequest("admin", "GET", {
        //     username,
        // });
        const response = {...MOCK_RESPONSE};
        response.data = admin;
        return response;
    },
    async members(username) {
        // const response = await makeHttpRequest("members", "GET", {
        //     username: username,
        // });
        const response = {...MOCK_RESPONSE};
        response.members = members;
        return response;
    },
    async memberServices(userID) {
        // const response = await makeHttpRequest("member-services", "GET", {
        //     "user-id": userID,
        // });
        const categoriesResponse = await this.serviceCategories();
        const categories = categoriesResponse.categories;
        const response = {...MOCK_RESPONSE};
        response.services = [];
        for (const id of ids) {
            response.services.push({
                "service_id": id,
                "service_name": "Service Name " + id,
                description:
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit," +
                    " sed do eiusmod tempor incididunt ut labore et dolore magna aliqua." +
                    " Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris" +
                    " nisi ut aliquip ex ea commodo consequat.",
                icon: categories[Math.floor(Math.random() * categories.length)],
            });
        }
        return response;
    },
    async serviceCategories() {
        // const response = await makeHttpRequest("service-categories", "GET", {});
        // return response;
        const response = {...MOCK_RESPONSE};
        response.categories = ["entertainment", "food", "utility"];
        return response;
    },
    async profilePictures() {
        const response = await makeHttpRequest("profile-pictures", "GET", {});
        return response;
    },
    async servicesInCategory(category) {
        // const response = await makeHttpRequest("services-in-category", "GET", {
        //     category,
        // });
        // return response;
        const response = {...MOCK_RESPONSE};
        response.services = [];
        for (const n of [...Array(6).keys()]) {
            const id = new Date().valueOf() + Math.floor(Math.random() * 20 + 1);
            ids.push(id);
            const service = {
                "service_id": id,
                "service_name": "Service Name " + id,
                description:
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit," +
                    " sed do eiusmod tempor incididunt ut labore et dolore magna aliqua." +
                    " Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris" +
                    " nisi ut aliquip ex ea commodo consequat.",
                icon: category,
            };
            response.services.push(service);
        }
        return response;
    },
    async addServiceToUser(userID, serviceID) {
        // const response = makeHttpRequest("add-service-to-user", "POST", {
        //     "user-id": userID,
        //     "service-id": serviceID,
        // });
        return MOCK_RESPONSE;
    },
    async removeServiceFromUser(userID, serviceID) {
        // const response = makeHttpRequest("remove-service-from-user", "POST", {
        //     "user-id": userID,
        //     "service-id": serviceID,
        // });
        return MOCK_RESPONSE;
    },
    async updateAdmin({
                          username,
                          firstName,
                          lastName,
                          email,
                          phoneNumber,
                          password,
                          profilePicture,
                      }) {
        // const response = await makeHttpRequest("update-admin", "POST", {
        // username: username,
        // "first-name": firstName,
        // "last-name": lastName,
        // email: email,
        // "phone-number": phoneNumber,
        // password: password,
        // "profile-picture": profilePicture
        // });
        admin = {
            username,
            firstName,
            lastName,
            email,
            phoneNumber,
            password,
            profilePicture,
        };
        return MOCK_RESPONSE;
    },
    async addMember({
                        username,
                        firstName,
                        lastName,
                        phoneNumber,
                        prefix,
                        profilePicture,
                    }) {
        // const response = await makeHttpRequest("add-member", "POST", {
        //     username,
        //     "first-name": firstName,
        //     "last-name": lastName,
        //     "phone-number": phoneNumber,
        //     prefix,
        //     "profile-picture": profilePicture,
        // });
        const userID = 5;
        members[userID] = {
            id: userID,
            firstName,
            lastName,
            phoneNumber,
            prefix,
            profilePicture,
        };
        const response = {...MOCK_RESPONSE};
        response["user-id"] = userID;
        return response;
    },
    async removeMember(userID) {
        // const response = makeHttpRequest("remove-member", "DELETE", {
        //     "user-id": userID,
        // });
        delete members[userID];
        return MOCK_RESPONSE;
    },
    async updateMember({
                           userID,
                           firstName,
                           lastName,
                           phoneNumber,
                           prefix,
                           profilePicture,
                       }) {
        // const response = await makeHttpRequest("update-member", "POST", {
        //     "user-id": userID,
        //     "first-name": firstName,
        //     "last-name": lastName,
        //     "phone-number": phoneNumber,
        //     prefix: prefix,
        //     "profile-picture": profilePicture,
        // });
        members[userID] = {
            id: userID,
            firstName,
            lastName,
            phoneNumber,
            prefix,
            profilePicture,
        };
        return MOCK_RESPONSE;
    },
    async memberHistory(userID) {
        // const response = await makeHttpRequest("member-history", "GET", {
        //     "user-id": userID,
        // });
        const response = {...MOCK_RESPONSE};
        response.history = [];
        for (const id of ids) {
            response.history.push({
                "service_id": id,
                "service_name": "Service Name",
                "timestamp": 1613996038,
            })
        }
        return response;
    },
    async memberServiceData(userID, serviceName) {
        const response = {...MOCK_RESPONSE};
        const random = Math.random();
        if (random < 0.33) {
            response.fields = {
                "ACCOUNT_NUMBER": 123456789101112,
                "SORT_CODE": 123456
            };
        } else if (random >= 0.33 && random < 0.67) {
            response.fields = {
                "GP_PHONE_NUMBER": "07111111111",
            };
        } else {
            response.fields = {};
        }
        return response;
    },
    async updateMemberServiceData(userID, serviceName, data) {
        return MOCK_RESPONSE;
    }
};

let ids = [];

let admin = {
    username: "ernest",
    firstName: "Ernest",
    lastName: "Badu",
    email: "you@mail.co.uk",
    phoneNumber: "07111111111",
    profilePicture: "apples",
    password: "12345",
};

let members = {
    1: {
        id: 1,
        firstName: "Jane",
        lastName: "Williams",
        phoneNumber: "07111111111",
        prefix: "Ms",
        profilePicture: "apples",
    },
    2: {
        id: 2,
        firstName: "Margaret",
        lastName: "Phillips",
        phoneNumber: "07111111111",
        prefix: "Mrs",
        profilePicture: "hills",
    },
    3: {
        id: 3,
        firstName: "Mark",
        lastName: "Phillips",
        phoneNumber: "07111111111",
        prefix: "Mr",
        profilePicture: "tulip",
    },
    4: {
        id: 4,
        firstName: "George",
        lastName: "Best",
        phoneNumber: "07111111111",
        prefix: "Mr",
        profilePicture: "mountains",
    },
};

export default api;
