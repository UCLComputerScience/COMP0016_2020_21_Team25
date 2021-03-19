import {mount} from "@vue/test-utils";
import ProfileForm from "../../../src/app/views/profile/profile-form.vue";
import {store} from "../../../src/store/store";
import {admin, app, mockRouter, testPassword, testUsername} from "../util/constants.js";


const defaultData = {
    email: "unusedmail@mail.co.uk",
    firstName: "Updated",
    lastName: "Information",
    phoneNumber: "11111111108",
    currentPassword: testPassword,
    newPassword: "",
    newRepeatPassword: ""
};

describe("Update Profile Form", () => {
    beforeEach(async () => {
        await store.dispatch("admin/profile", admin);
    });

    const getWrapper = () => {
        return mount(ProfileForm, {
            attachTo: app,
            global: {
                plugins: [store],
                mocks: {
                    $route: {
                        path: "/:username/profile",
                        params: { username: testUsername },
                        name: "profile"
                    },
                    $router: mockRouter
                },
            },
        });
    };
    const test = async ({
                            firstName, lastName, email, phoneNumber,
                            currentPassword, newPassword, newRepeatPassword
                        }, success, message) => {
        const wrapper = getWrapper();

        const firstNameInput = wrapper.find("#profile-first-name");
        const lastNameInput = wrapper.find("#profile-last-name");
        const emailInput = wrapper.find("#profile-email");
        const phoneNumberInput = wrapper.find("#profile-phone-number");
        const currentPasswordInput = wrapper.find("#profile-password");
        const newPasswordInput = wrapper.find("#new-password");
        const repeatPasswordInput = wrapper.find("#profile-repeat");

        const testInput = (input, expected) => {
            expect(input.attributes()["placeholder"]).toBe(expected);
        };

        testInput(firstNameInput, admin["first-name"]);
        testInput(lastNameInput, admin["last-name"]);
        testInput(emailInput, admin["email"]);
        testInput(phoneNumberInput, admin["phone-number"]);

        await firstNameInput.setValue(firstName);
        await lastNameInput.setValue(lastName);
        await emailInput.setValue(email);
        await phoneNumberInput.setValue(phoneNumber);
        await currentPasswordInput.setValue(currentPassword);
        await newPasswordInput.setValue(newPassword);
        await repeatPasswordInput.setValue(newRepeatPassword);

        expect(wrapper.vm.profileData.firstName).toEqual(firstName);
        expect(wrapper.vm.profileData.lastName).toEqual(lastName);
        expect(wrapper.vm.profileData.email).toEqual(email);
        expect(wrapper.vm.profileData.phoneNumber).toEqual(phoneNumber);
        expect(wrapper.vm.profileData.repeatPassword).toEqual(newRepeatPassword);
        expect(wrapper.vm.profileData.currentPassword).toEqual(currentPassword);
        expect(wrapper.vm.profileData.newPassword).toEqual(newPassword);

        const alertSpy = spyOn(window, "alert");
        await wrapper.vm.update();
        expect(wrapper.vm.profileData.success).toEqual(success);
        expect(alertSpy).toBeCalledTimes(1);
        expect(wrapper.vm.profileData.response).toEqual(message);
        if (success) {
            const admin = store.getters["admin/admin"];
            const testNewValue = (newValue, keyName) => {
                if (newValue.length > 0) {
                    expect(admin[keyName]).toBe(newValue);
                }
            };
            testNewValue(firstName, "first-name");
            testNewValue(lastName, "last-name");
            testNewValue(email, "email");
            testNewValue(phoneNumber, "phone-number");
            testNewValue(newPassword, "password");
        }
    };

    /**
     * Blank input tests - store should replace the blanks with pre-existing values
     */
    it("Blank all", async (done) => {
        const data = { ...defaultData };
        data.firstName = "";
        data.lastName = "";
        data.phoneNumber = "";
        data.email = "";
        data.currentPassword = "";
        const success = true;
        const message = "Ok";
        await test(data, success, message);
        done();
    });

    it("Blank first name", async (done) => {
        const data = { ...defaultData };
        data.firstName = "";
        data.currentPassword = "";
        const success = true;
        const message = "Ok";
        await test(data, success, message);
        done();
    });

    it("Blank last name", async (done) => {
        const data = { ...defaultData };
        data.lastName = "";
        data.currentPassword = "";
        const success = true;
        const message = "Ok";
        await test(data, success, message);
        done();
    });

    it("Blank email", async (done) => {
        const data = { ...defaultData };
        data.email = "";
        data.currentPassword = "";
        const success = true;
        const message = "Ok";
        await test(data, success, message);
        done();
    });

    it("Blank phone number", async (done) => {
        const data = { ...defaultData };
        data.phoneNumber = "";
        data.currentPassword = "";
        const success = true;
        const message = "Ok";
        await test(data, success, message);
        done();
    });

    /**
     * Invalid tests
     */
    it("Short phone number", async (done) => {
        const data = { ...defaultData };
        data.phoneNumber = "1234";
        data.currentPassword = "";
        const success = false;
        const message = "Your phone number is invalid";
        await test(data, success, message);
        done();
    });

    /**
     * Password tests
     */
    it("Change password to blank password", async (done) => {
        const data = { ...defaultData };
        data.newPassword = "";
        const success = true;
        const message = "Ok";
        await test(data, success, message);
        done();
    });

    it("Change password to short password", async (done) => {
        const data = { ...defaultData };
        data.newPassword = "12";
        data.newRepeatPassword = "12";
        const success = false;
        const message = "Your new password must be at least five characters long.";
        await test(data, success, message);
        done();
    });

    it("Change password without entering current password", async (done) => {
        const data = { ...defaultData };
        data.currentPassword = "";
        data.newPassword = "12345";
        data.newRepeatPassword = "12345";
        const success = false;
        const message = "To change your password, please enter your password" +
            " in the 'Current Password' field.";
        await test(data, success, message);
        done();
    });

    it("Change password with wrong current password", async (done) => {
        const data = { ...defaultData };
        const success = false;
        data.currentPassword = "wrongpassword";
        data.newPassword = "12345";
        data.newRepeatPassword = "12345";
        const message = "Your password is incorrect. To change your password, you must correctly enter your current password.";
        await test(data, success, message);
        done();
    });

    it("Change password with wrong new password repeat", async (done) => {
        const data = { ...defaultData };
        data.currentPassword = "123456";
        data.newPassword = "12345";
        data.newRepeatPassword = "123456";
        const success = false;
        const message = "The two entered passwords do not match.";
        await test(data, success, message);
        done();
    });

    /**
     * Taken data tests
     */
    it("Taken phone number", async (done) => {
        const data = { ...defaultData };
        data.phoneNumber = "01234567899";
        const success = false;
        const message = `The phone number, ${data.phoneNumber} is already associated with another account.`;
        await test(data, success, message);
        done();
    });

    it("Taken email", async (done) => {
        const data = { ...defaultData };
        data.email = "bobsmith2@me";
        const success = false;
        const message = `The email address, ${data.email} is already associated with another account.`;
        await test(data, success, message);
        done();
    });

    /**
     * Valid Tests
     */
    it("Update profile data valid", async (done) => {
        const data = { ...defaultData };
        data.currentPassword = "";
        const success = true;
        const message = "Ok";
        await test(data, success, message);
        done();
    });

    it("Change password valid", async (done) => {
        const data = { ...defaultData };
        data.newPassword = "123456";
        data.newRepeatPassword = "123456";
        const success = true;
        const message = "Ok";
        await test(data, success, message);
        done();
    });

    it("Test clear inputs", async (done) => {
        const wrapper = getWrapper();
        await wrapper.vm.clearInputs();

        const firstNameInput = wrapper.find("#profile-first-name");
        const lastNameInput = wrapper.find("#profile-last-name");
        const emailInput = wrapper.find("#profile-email");
        const phoneNumberInput = wrapper.find("#profile-phone-number");
        const currentPasswordInput = wrapper.find("#profile-password");
        const newPasswordInput = wrapper.find("#new-password");
        const repeatPasswordInput = wrapper.find("#profile-repeat");

        for (const input of [firstNameInput, lastNameInput, emailInput, phoneNumberInput,
            currentPasswordInput, newPasswordInput, repeatPasswordInput]) {
            expect(input.element.value).toBe("");
        }
        expect(repeatPasswordInput.element).toBe(document.activeElement);
        done();
    });

    it("Test activate", async (done) => {
        const wrapper = getWrapper();
        await wrapper.vm.activate();
        const firstNameInput = wrapper.find("#profile-first-name");
        expect(firstNameInput.element).toBe(document.activeElement);
        done();
    });

});
