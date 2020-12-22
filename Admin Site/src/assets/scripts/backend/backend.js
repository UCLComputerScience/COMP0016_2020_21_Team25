import {getProfileImage} from "../util";

const backend = {
    login: (usernameOrEmail, password) => {
        if (usernameOrEmail !== "ernest") {
            return "That username or email address is not recognised, " +
                "did you mean to sign up?";
        }
        if (password !== "12345") {
            return "Your password is incorrect, please try again";
        }
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
        return "Registration success";
    },
    fetchAdmin: (username) => {
        return {
            username: username,
            firstName: "John",
            lastName: "Doe",
            email: "you@mail.co.uk",
            phoneNumber: "07111111111",
            profilePicture: getProfileImage("daschund"),
        }
    },
    fetchUsers: (username) => {
        const user1 = {
            firstName: "Jane",
            lastName: "Williams",
            phoneNumber: "07111111111",
            prefix: "Ms",
            profilePicture: getProfileImage("hills"),
        }
        const user2 = {
            firstName: "Margaret",
            lastName: "Phillips",
            phoneNumber: "07111111111",
            prefix: "Mrs",
            profilePicture: getProfileImage("apples"),
        }
        const user3 = {
            firstName: "Mark",
            lastName: "Phillips",
            phoneNumber: "07111111111",
            prefix: "Mr",
            profilePicture: getProfileImage("macaw"),
        }
        const user4 = {
            firstName: "George",
            lastName: "Best",
            phoneNumber: "07111111111",
            prefix: "Mr",
            profilePicture: getProfileImage("mountains"),
        }
        return {1: user1, 2: user2, 3: user3, 4: user4};
    }
}

export default backend;