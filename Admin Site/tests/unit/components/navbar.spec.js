import {mount} from "@vue/test-utils";
import Navbar from "../../../src/app/components/layout/navbar/Navbar.vue";
import {store} from "../../../src/store/store.js";
import {app, mockRouter, testUsername} from "../util/constants.js";


describe("Navbar", () => {
    const getWrapper = (path, name) => {
        mockRouter.push({
            name, params: { username: testUsername },
        });
        return mount(Navbar, {
            attachTo: app,
            data() {
                return {
                    width: 500,
                    scrollTop: 0

                };
            },
            global: {
                plugins: [store],
                mocks: {
                    $route: {
                        path: "/:username/" + path,
                        params: { username: testUsername },
                        name: name
                    },
                    $router: mockRouter
                },
                stubs: ["Collaborators", "Authors"]
            }
        });
    };

    it("Click nav item", async (done) => {
        const wrapper = getWrapper();
        const spy = spyOn(wrapper.vm.$router, "push");
        const element = wrapper.find(".nav-item");
        await element.trigger("click");
        expect(spy).toBeCalledTimes(1);
        wrapper.unmount();
        done();
    });

    it("Click profile button when on profile page", async (done) => {
        const wrapper = getWrapper("profile", "profile");
        const spy = spyOn(wrapper.vm.$router, "push");
        const element = wrapper.find(".profile-toggler");
        await element.trigger("click");
        expect(spy).toBeCalledTimes(1);
        done();
    });

    it("Click profile button when on marketplace page", async (done) => {
        const wrapper = getWrapper("marketplace", "marketplace");
        const spy = spyOn(wrapper.vm.$router, "push");
        const element = wrapper.find(".profile-toggler");
        await element.trigger("click");
        expect(spy).toBeCalledTimes(1);
        done();
    });

    it("Click profile button when on people page", async (done) => {
        const wrapper = getWrapper("people", "people");
        const spy = spyOn(wrapper.vm.$router, "push");
        const element = wrapper.find(".profile-toggler");
        await element.trigger("click");
        expect(spy).toBeCalledTimes(1);
        done();
    });

    it("Scroll and show shadow", async (done) => {
        const wrapper = getWrapper("people", "people");
        wrapper.vm.scrollTop = 100;
        await wrapper.vm.$nextTick();
        const navbar = wrapper.find("header");
        expect(navbar.classes()).toContain("nav-triggered");
        done();
    });

    /**
     * Mobile nav tests
     */
    it("Click toggler", async (done) => {
        const wrapper = getWrapper("people", "people");
        const toggler = wrapper.find(".mobile-nav-toggler");
        await toggler.trigger("click");
        const mobileNav = wrapper.find(".mobile-navbar");
        expect(mobileNav.isVisible()).toBe(true);
        done();
    });

    it("Click toggler twice", async (done) => {
        const wrapper = getWrapper("people", "people");
        const toggler = wrapper.find(".mobile-nav-toggler");
        await toggler.trigger("click");
        const mobileNav = wrapper.find(".mobile-navbar");
        expect(mobileNav.classes()).toContain("mobile-nav-visible");
        expect(mobileNav.isVisible()).toBe(true);
        await toggler.trigger("click");
        expect(mobileNav.classes().includes("mobile-nav-visible")).toBe(false);
        done();
    });

    it("Click mobile nav item", async (done) => {
        const wrapper = getWrapper("people", "people");
        const toggler = wrapper.find(".mobile-nav-toggler");
        await toggler.trigger("click");
        const mobileNav = wrapper.findComponent(".mobile-navbar");
        const element = mobileNav.findComponent(".mobile-nav-item");
        const spy = spyOn(element.vm.$router, "push");
        await element.trigger("click");
        expect(spy).toBeCalledTimes(1);
        done();
    });

    it("Deactivate mobile nav item", async (done) => {
        const wrapper = getWrapper("people", "people");
        const toggler = wrapper.find(".mobile-nav-toggler");
        await toggler.trigger("click");
        const mobileNav = wrapper.findComponent(".mobile-navbar");
        const element = mobileNav.findComponent(".mobile-nav-item");
        await element.vm.deactivate();
        expect(element.classes().includes("active-nav-item")).toBe(false);
        done();
    });
});