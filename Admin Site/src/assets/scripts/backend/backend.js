import {getProfileImage, getServiceIcon, profileImages} from "../util";

const backend = {
    admin: {
        username: "ernest",
        firstName: "John",
        lastName: "Doe",
        email: "you@mail.co.uk",
        phoneNumber: "07111111111",
        profilePicture: getProfileImage("daschund"),
    },
    users: {
        1: {
            id: 1,
            firstName: "Jane",
            lastName: "Williams",
            phoneNumber: "07111111111",
            prefix: "Ms",
            profilePicture: getProfileImage("hills"),
        },
        2: {
            id: 2,
            firstName: "Margaret",
            lastName: "Phillips",
            phoneNumber: "07111111111",
            prefix: "Mrs",
            profilePicture: getProfileImage("apples"),
        },
        3: {
            id: 3,
            firstName: "Mark",
            lastName: "Phillips",
            phoneNumber: "07111111111",
            prefix: "Mr",
            profilePicture: getProfileImage("macaw"),
        },
        4: {
            id: 4,
            firstName: "George",
            lastName: "Best",
            phoneNumber: "07111111111",
            prefix: "Mr",
            profilePicture: getProfileImage("mountains"),
        },
    },
    login: function (usernameOrEmail, password) {
        if (usernameOrEmail !== "ernest") {
            return "That username or email address is not recognised, " +
                "did you mean to sign up?";
        }
        if (password !== "12345") {
            return "Your password is incorrect, please try again";
        }
        this.admin.username = usernameOrEmail;
        return "Login success";
    },
    logout: () => {

    },
    register: (username, firstName, lastName, email, phoneNumber, password) => {
        if (username === "john-doe123") {
            return `The username, ${username}, is already taken, please try something else.`;
        }
        if (email === "you@mail.co.uk") {
            return `The email address, ${email} is already associated with another
             account. Did you mean to sign in?`;
        }
        if (phoneNumber === "07111111111") {
            return `The phone number, ${phoneNumber} is already associated with another
             account. Did you mean to sign in?`;
        }
        this.admin.username = username;
        this.admin.firstName = firstName;
        this.admin.lastName = lastName;
        this.admin.email = email;
        this.admin.phoneNumber = phoneNumber;
        return "Registration success";
    },
    fetchAdmin: function (username) {
        return this.admin;
    },
    fetchMembers: function (username) {
        return this.users;
    },
    fetchCategories: () => {
        return ["Utility", "Finance", "Retail", "Health", "Transport",
            "Miscellaneous"];
    },
    fetchServicesInCategory: (category) => {
        let result = [];
        for (let n of [...Array(9).keys()]) {
            const service = {
                id: n,
                title: "Service Name",
                description: "Lorem ipsum dolor sit amet, consectetur adipiscing elit," +
                    " sed do eiusmod tempor incididunt ut labore et dolore magna aliqua." +
                    " Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris" +
                    " nisi ut aliquip ex ea commodo consequat.",
                icon: getServiceIcon("icon"),
            };
            result.push(service)
        }
        return result;
    },
    addServiceToUsers: function (serviceId, users) {
        for (let user of users) {
            if (!this.userHasService(user.id, serviceId)) {
                // Update in database
            }
        }
    },
    userHasService: function (userId, serviceId) {
        return false;
    },
    getPassword: function (username) {
        return "12345";
    },
    updateProfile: function (username, firstName, lastName, email, phoneNumber, password) {

    },
    fetchAllProfilePictures: function () {
        const images = [];
        for (let [name, image] of Object.entries(profileImages)) {
            images.push({name: name, image: image});
        }
        return images;
    },
    updateAdminPicture: function (username, newPic) {
        this.admin.profilePicture = getProfileImage(newPic);
        return this.admin.profilePicture;
    },
    updateMemberPicture: function (username, userID, newPic) {
        this.users[userID].profilePicture = getProfileImage(newPic);
        return this.users[userID];
    },
    addMember: function(firstName, lastName, prefix, phoneNumber, profilePicture) {
        const userId = this.generateUserID();
        const member = {
            id: userId,
            firstName: firstName,
            lastName: lastName,
            prefix: prefix,
            phoneNumber: phoneNumber,
            profilePicture: getProfileImage(profilePicture)
        };
        this.users[userId] = member;
        return userId;
    },
    generateUserID: function() {
        return 5;
    },
    removeMember: function(userId) {
        const copy = this.users;
        delete copy[userId];
        this.users = copy;
    },
    updateMember: function(userId, firstName, lastName, prefix, phoneNumber, profilePicture) {
        this.users[userId] = {
            id: userId,
            firstName: firstName,
            lastName: lastName,
            prefix: prefix,
            phoneNumber: phoneNumber,
            profilePicture: profilePicture,
        }
    }
}

export default backend;