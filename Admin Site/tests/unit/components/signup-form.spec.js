import {mount} from "@vue/test-utils";
import SignupForm from "../../../src/app/views/welcome/signup-form.vue";
import {store} from "../../../src/store/store";

describe("Signup Form", () => {
    const test = (description, username,
                  firstName, lastName,
                  email, phoneNumber,
                  password, repeatPassword,
                  success, message, furtherAssertions = () => {
        }) => {
        it(description, async (done) => {
            // Jest test browser does not include window.alert method
            const jsDomAlert = window.alert;  // remember the jsdom alert
            window.alert = () => {
            };

            await store.commit("account/entered", false);
            await store.commit("admin/setAdmin", {});

            const wrapper = mount(SignupForm, {
                global: {
                    plugins: [store],
                },
            });

            const usernameInput = wrapper.find("#signup-username");
            await usernameInput.setValue(username);

            const firstNameInput = wrapper.find("#signup-first-name");
            await firstNameInput.setValue(firstName);

            const lastNameInput = wrapper.find("#signup-last-name");
            await lastNameInput.setValue(lastName);

            const emailInput = wrapper.find("#signup-email");
            await emailInput.setValue(email);

            const phoneNumberInput = wrapper.find("#signup-phone-number");
            await phoneNumberInput.setValue(phoneNumber);

            const passwordInput = wrapper.find("#signup-password");
            await passwordInput.setValue(password);

            const repeatPasswordInput = wrapper.find("#signup-repeat");
            await repeatPasswordInput.setValue(repeatPassword);

            expect(wrapper.vm.signupData.username).toEqual(username);
            expect(wrapper.vm.signupData.firstName).toEqual(firstName);
            expect(wrapper.vm.signupData.lastName).toEqual(lastName);
            expect(wrapper.vm.signupData.email).toEqual(email);
            expect(wrapper.vm.signupData.phoneNumber).toEqual(phoneNumber);
            expect(wrapper.vm.signupData.repeatPassword).toEqual(repeatPassword);
            expect(wrapper.vm.signupData.password).toEqual(password);

            await wrapper.vm.signup();
            expect(wrapper.vm.signupData.success).toEqual(success);
            if (!success) {
                expect(wrapper.vm.signupData.response).toEqual(message);
            }
            furtherAssertions();
            window.alert = jsDomAlert;
            done();
        });
    };

    /**
     * Blank input field tests - dispatch to store should not be made if a field is left empty.
     */
    test("no data entered", "", "", "",
        "", "", "", "", false, null);

    test("no username entered", "", "ernest", "badu",
        "email@mail.co.uk", "11111111111", "12345", "12345", false, null);

    test("no first name entered", "username", "", "badu",
        "email@mail.co.uk", "11111111111", "12345", "12345", false, null);

    test("no last name entered", "username", "ernest", "",
        "email@mail.co.uk", "11111111111", "12345", "12345", false, null);

    test("no email entered", "username", "ernest", "badu",
        "", "11111111111", "12345", "12345", false, null);

    test("no phone number entered",  "username", "ernest", "badu",
        "email@mail.co.uk", "", "12345", "12345", false, null);

    test("no password entered",  "username", "ernest", "badu",
        "email@mail.co.uk", "11111111111", "", "12345", false, null);

    test("no repeat password entered",  "username", "ernest", "badu",
        "email@mail.co.uk", "11111111111", "12345", "", false, null);

    /**
     * Invalid data
     */
    test("short username (< 2 chars)", "a", "ernest", "badu",
        "email@mail.co.uk", "11111111111", "12345", "12345", false, null);

    test("short phone number (< 11 chars)", "username", "ernest", "badu",
        "email@mail.co.uk", "11", "12345", "12345", false, null);

    test("short password (< 5 chars)", "username", "ernest", "badu",
        "email@mail.co.uk", "11111111111", "1234", "1234", false, null);

    test("non-matching password repeat", "username", "ernest", "badu",
        "email@mail.co.uk", "11111111111", "12345", "123456", false, null);

    /**
     * Taken data
     */

    test("taken username", "ernest", "ernest", "badu",
        "email@mail.co.uk", "11111111111", "12345", "12345", false,
        "The username ernest is already in use. Did you mean to log in?");

    test("taken phone number", "user", "ernest", "badu",
        "unused@mail.co.uk", "07111111112", "12345", "12345", false,
        "The phone number 07111111112 is already associated with another account.");

    test("taken email", "user", "ernest", "badu",
        "test@mail.co.uk", "07111111111", "12345", "12345", false,
        "The email address test@mail.co.uk is already associated with another account. Did you mean to log in?");

    /**
     * Valid data
     */
    // TODO - Will need to remove this user from database after test
    // test("valid signup",  "", "", "", "", "", "", "", true, "Ok");

});
