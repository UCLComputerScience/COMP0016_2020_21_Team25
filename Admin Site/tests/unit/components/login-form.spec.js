import { mount } from "@vue/test-utils";
import LoginForm from "../../../src/app/views/welcome/login-form.vue";
import { store } from "../../../src/store/store";

describe("Login Form", () => {
    const test = (description, username, password, expected, furtherAssertions = () => {}) => {
        it(description, async () => {
            const wrapper = mount(LoginForm, {
                global: {
                    plugins: [store],
                },
                stubs: {
                    "v-link": true,
                    VLink: true,
                    "router-link": true,
                    "flat-button": true,
                },
            });
            const usernameInput = wrapper.find(".text-input input");
            await usernameInput.setValue(username);
            const passwordInput = wrapper.find("[type=password]");
            await passwordInput.setValue(password);
            const form = wrapper.find("form");
            await form.trigger("submit.prevent");
            expect(wrapper.vm.loginData.response).toEqual(expected);
            furtherAssertions();
        });
    };

    /**
     * Dispatch to store should not be made if a field is left empty.
    */
    test("no username, no password", "", "", null);
    test("valid username, no password", "ernest", "", null);
    test("invalid username, no password", "aaa", "", null);
    test("no username, some password", "", "aaa", null);
    // test("valid username, correct password", "ernest", "12345", "");
    // test("invalid username, incorrect password", "aaa", "aaa", "");
});
