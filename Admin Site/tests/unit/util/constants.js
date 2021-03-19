export const loginAdmin = {
    username: "logintest",
    email: "test@login.co.uk",
    "first-name": "Test",
    "last-name": "User",
    "password": "12345",
    "profile-picture": 41,
    "phone-number": "07111111123"
};

export const admin = {
    username: "testuser",
    email: "test@user.co.uk",
    "first-name": "Test",
    "last-name": "User",
    "password": "12345",
    "profile-picture": 41,
    "phone-number": "07111111124"
};

export const adminWithMembers = {
    username: "members",
    email: "admin@members.co.uk",
    "firstName": "Admin",
    "lastName": "Members",
    "password": "12345",
    "profilePicture": 41,
    "phoneNumber": "07111111125"
};

export const members = [{
    id: 107,
    "firstName": "John",
    "lastName": "Doe",
    "phoneNumber": "12345678915",
    prefix: "Mr",
    "profilePicture": 41,
}, {
    "firstName": "Lucy",
    "lastName": "Ayling",
    "phoneNumber": "12345678912",
    prefix: "Ms",
    "profilePicture": 41,
}, {
    id: 105,
    "firstName": "Anthony",
    "lastName": "Davis",
    "phoneNumber": "12345678913",
    prefix: "Mx",
    "profilePicture": 41,
}, {
    "firstName": "Candace",
    "lastName": "Jones",
    "phoneNumber": "12345678914",
    prefix: "Mrs",
    "profilePicture": 41,
}];

export const userIdWithServices = members[0]["id"];
export const charityServiceCategory = "Finance";
export const charityServiceId = "32";

export const testLoginUsername = loginAdmin["username"];
export const testLoginPassword = loginAdmin["password"];
export const testLoginEmail = loginAdmin["email"];
export const testLoginPhoneNumber = loginAdmin["phone-number"];

export const testUsername = admin["username"];
export const testPassword = admin["password"];
export const testEmail = admin["email"];
export const testPhoneNumber = admin["phone-number"];

export const testMembersUsername = adminWithMembers["username"];
export const testMembersPassword = adminWithMembers["password"];
export const testMembersEmail = adminWithMembers["email"];
export const testMembersPhoneNumber = adminWithMembers["phone-number"];

export const app = document.createElement("div");
app.id = "app";

export const mockRouter = {
    currentRoute: {
        path: "",
        name: "",
        query: "",
        hash: undefined,
        params: {}
    },
    push({ name, path, params, hash }) {
        this.currentRoute.name = name;
        this.currentRoute.params = params;
        this.currentRoute.path = path;
        this.currentRoute.hash = hash;
    },
    replace({ name, path, params, hash }) {
        this.push({ name, path, params, hash });
    },
    go() {
    }
};
