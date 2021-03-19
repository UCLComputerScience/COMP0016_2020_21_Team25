import {mount} from "@vue/test-utils";
import Welcome from "../../../src/app/views/welcome/Welcome.vue";
import {store} from "../../../src/store/store.js";
import {mockRouter} from "../util/constants.js";

const loginTitle = "Sign In";
const loginSubtitle = "Enter your username or email and password to sign into your account.";
const loginInfoText = "Don't have an account? Create one now";

const signupTitle = "Sign Up";
const signupSubtitle = "Fill out the details below to register a new account.";
const signupInfoText = "Already have an existing account? Sign in now";

const testRedirect = (description, url, expectedTitle, expectedSubtitle, expectedInfoText) => {
    it(description, async (done) => {
        const wrapper = mount(Welcome, {
            global: {
                plugins: [store],
                stubs: ["FooterLogo"],
                mocks: {
                    $route: {
                        path: "/welcome",
                        redirectedFrom: {
                            path: url
                        }
                    },
                    $router: mockRouter
                }
            },
        });

        const title = await wrapper.find(".form-card > h1");
        const subtitle = await wrapper.find(".form-card > p");

        expect(wrapper.vm.title).toBe(expectedTitle);
        expect(wrapper.vm.title).toEqual(title.element.innerHTML);
        expect(wrapper.vm.subtitle).toBe(expectedSubtitle);
        expect(wrapper.vm.subtitle).toEqual(subtitle.element.innerHTML);
        expect(wrapper.vm.infoText).toBe(expectedInfoText);

        done();
    });
};

describe("Welcome View", () => {

    it("Test tab switching", async (done) => {
        const wrapper = mount(Welcome, {
            global: {
                plugins: [store],
                stubs: ["FooterLogo"],
                mocks: {
                    $route: {
                        path: "/welcome",
                    },
                    $router: mockRouter
                }
            },
        });

        const title = await wrapper.find(".form-card > h1");
        const subtitle = await wrapper.find(".form-card > p");
        const link = await wrapper.find(".inline-link");

        await link.trigger("click");
        expect(wrapper.vm.title).toBe(signupTitle);
        expect(wrapper.vm.title).toEqual(title.element.innerHTML);
        expect(wrapper.vm.subtitle).toBe(signupSubtitle);
        expect(wrapper.vm.subtitle).toEqual(subtitle.element.innerHTML);
        expect(wrapper.vm.infoText).toBe(signupInfoText);

        await link.trigger("click");
        expect(wrapper.vm.title).toBe(loginTitle);
        expect(wrapper.vm.title).toEqual(title.element.innerHTML);
        expect(wrapper.vm.subtitle).toBe(loginSubtitle);
        expect(wrapper.vm.subtitle).toEqual(subtitle.element.innerHTML);
        expect(wrapper.vm.infoText).toBe(loginInfoText);

        done();
    });

    testRedirect("The welcome view should open the sign up " +
        "form first if redirecting from /register", "/register", signupTitle,
        signupSubtitle, signupInfoText);
    testRedirect("The welcome view should open the sign up " +
        "form first if redirecting from /sign-up", "/sign-up", signupTitle,
        signupSubtitle, signupInfoText);
    testRedirect("The welcome view should open the login " +
        "form first by default", "/login", loginTitle,
        loginSubtitle, loginInfoText);
});