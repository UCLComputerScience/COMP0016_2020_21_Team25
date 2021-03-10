import {mount} from "@vue/test-utils";
import LoginForm from "../../../src/app/views/welcome/login-form.vue";
import {store} from "../../../src/store/store";

describe("Login Form", () => {
    const test = (description, username, password, success, message, furtherAssertions = () => {}) => {
        it(description, async (done) => {
            // Jest test browser does not include window.alert method
            const jsDomAlert = window.alert;  // remember the jsdom alert
            window.alert = () => {};

            await store.commit("account/entered", false);
            await store.commit("admin/setAdmin", {});

            const wrapper = mount(LoginForm, {
                global: {
                    plugins: [store],
                },
            });

            const usernameInput = wrapper.find("#login-username-or-email");
            await usernameInput.setValue(username);
            const passwordInput = wrapper.find("#login-password");
            await passwordInput.setValue(password);

            expect(wrapper.vm.loginData.usernameOrEmail).toEqual(username);
            expect(wrapper.vm.loginData.password).toEqual(password);
            await wrapper.vm.login();
            expect(wrapper.vm.loginData.success).toEqual(success);
            if (!success) {
                expect(wrapper.vm.loginData.response).toEqual(message);
            }
            furtherAssertions();
            window.alert = jsDomAlert;
            done();
        });
    };

    /**
     * Dispatch to store should not be made if a field is left empty.
     */
    test("no username, no password", "", "", false, null);
    test("valid username, no password", "ernest", "", false, null);
    test("invalid username, no password", "aaa", "", false, null);
    test("no username, some password", "", "aaa", false, null);
    test("valid email, correct password", "test@mail.co.uk", "12345", true, "Ok");
    test("valid username, correct password", "ernest", "12345", true, "Ok");
    test("valid username incorrect password", "ernest", "aaa", false, "Your password was incorrect, please try again.");
    test("invalid username, incorrect password", "aaa", "aaa", false, "The username aaa does not exist, did you mean to sign up?");
});
