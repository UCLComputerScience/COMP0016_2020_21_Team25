import {mount} from "@vue/test-utils";
import LoginForm from "../../../src/app/views/welcome/login-form.vue";
import {store} from "../../../src/store/store";

const test = (description, username, password, expected) => {
    it(description, async () => {
        const wrapper = mount(LoginForm);
        const usernameInput = await wrapper.find(".text-input input");
        usernameInput.setValue(username);
        const passwordInput = await wrapper.find("[type=password]");
        passwordInput.setValue(password);
        const form = await wrapper.find("form")
        form.trigger("submit.prevent");
    })
}

describe("Login Form", () => {
    let vueStore;
    beforeEach(() => {
        vueStore = store;
    });

    test("no username, no password", "", "", "");
    test("valid username, no password", "ernest", "", "");
    test("invalid username, no password", "aaa", "", "");
    test("no username, some password", "", "aaa", "");
    test("valid username, correct password", "ernest", "12345", "");
    test("invalid username, incorrect password", "aaa", "aaa", "");
});