import {mount} from "@vue/test-utils";
import LoginForm from "../../../src/app/views/welcome/login-form.vue";
import {store} from "../../../src/store/store";
import {app, testLoginEmail, testLoginPassword, testLoginUsername} from "../util/constants.js";

describe("Login Form", () => {
    const test = (description, username, password, success, message) => {
        it(description, async (done) => {
            await store.commit("account/entered", false);
            await store.commit("admin/setAdmin", {});

            const wrapper = mount(LoginForm, {
                attachTo: app,
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
            wrapper.unmount();
            done();
        });
    };

    /**
     * Dispatch to store should not be made if a field is left empty.
     */
    test("no username, no password", "", "", false, null);
    test("valid username, no password", testLoginUsername, "", false, null);
    test("invalid username, no password", "aaa", "", false, null);
    test("no username, some password", "", "aaa", false, null);
    test("valid email, correct password", testLoginEmail, testLoginPassword, true, "Ok");
    test("valid username, correct password", testLoginUsername, testLoginPassword, true, "Ok");
    test("valid username incorrect password", testLoginUsername, "aaa", false, "Your password was incorrect, please try again.");
    test("valid email incorrect password", testLoginEmail, "wrongpassword", false, "Your password was incorrect, please try again.");
    test("invalid username, incorrect password", "aaa", "aaa", false, "The username or email address 'aaa' does not exist, did you mean to sign up?");
});
