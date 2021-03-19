import {flushPromises, mount} from "@vue/test-utils";
import sidebar from "../../../src/app/views/people/sidebar/sidebar.vue";
import {store} from "../../../src/store/store.js";
import {app, mockRouter, testUsername} from "../util/constants.js";
import {resize} from "../util/util.js";


describe("People Sidebar", () => {
    beforeEach(() => {
        window.localStorage.setItem("sidebar", "true");
    });

    const getWrapper = (width) => {
        const wrapper = mount(sidebar, {
            attachTo: app,
            global: {
                plugins: [store],
                mocks: {
                    $route: {
                        path: "/:username/people",
                        params: { username: testUsername },
                        name: "people"
                    },
                    $router: mockRouter
                },
            }
        });

        resize(width, 1080);
        return wrapper;
    };

    it("Desktop sidebar (width 1920)", async (done) => {
        const width = 1920;
        const wrapper = getWrapper(width);
        wrapper.unmount();
        done();
    });

    it("Desktop sidebar (width 1200)", async (done) => {
        const width = 1200;
        const wrapper = getWrapper(width);
        wrapper.unmount();
        done();
    });

    it("Mobile sidebar (width 1024)", async (done) => {
        const width = 1024;
        const wrapper = getWrapper(width);
        wrapper.unmount();
        done();
    });

    it("Toggle sidebar once", async (done) => {
        const width = 1920;
        const wrapper = getWrapper(width);
        const toggler = wrapper.find(".sidebar-toggler");
        const sidebar = wrapper.find(".sidebar");
        await toggler.trigger("click");
        expect(sidebar.classes().includes("minimised-sidebar")).toBe(true);
        expect(toggler.classes().includes("open")).toBe(true);
        expect(wrapper.vm.minimised).toBe(true);
        const minimised = window.localStorage.getItem("sidebar");
        expect(minimised).toBe("true");
        done();
    });

    it("Toggle sidebar twice", async (done) => {
        window.localStorage.setItem("sidebar", "false");
        const width = 1920;
        const wrapper = getWrapper(width);
        const toggler = wrapper.find(".sidebar-toggler");
        const sidebar = wrapper.find(".sidebar");
        await toggler.trigger("click");
        await toggler.trigger("click");
        expect(sidebar.classes().includes("minimised-sidebar")).toBe(false);
        expect(toggler.classes().includes("open")).toBe(false);
        expect(wrapper.vm.minimised).toBe(false);
        const minimised = window.localStorage.getItem("sidebar");
        expect(minimised).toBe("false");
        done();
    });

    it("Click user", async (done) => {
        const width = 1920;
        const wrapper = getWrapper(width);
        const container = wrapper.findComponent(".sidebar-content > *");
        const userEl = wrapper.find("li");
        await userEl.trigger("click");
        await container.vm.$nextTick();
        await wrapper.vm.$nextTick();
        await flushPromises();
        expect(mockRouter.currentRoute.name).toBe("user-details");
        done();
    });

    // TODO - Implement
    it("Add user - valid data", async (done) => {
        const width = 1920;
        const wrapper = getWrapper(width);

        done();
    });

    // TODO - Implement
    it("Add user - blank data", async (done) => {
        const width = 1920;
        const wrapper = getWrapper(width);

        done();
    });

    // TODO - Implement
    it("Add user - no profile picture", async (done) => {
        const width = 1920;
        const wrapper = getWrapper(width);

        done();
    });

    // TODO - Implement
    it("Add user - no first name", async (done) => {
        const width = 1920;
        const wrapper = getWrapper(width);

        done();
    });

    // TODO - Implement
    it("Add user - no last name", async (done) => {
        const width = 1920;
        const wrapper = getWrapper(width);

        done();
    });

    // TODO - Implement
    it("Add user - no phone number", async (done) => {
        const width = 1920;
        const wrapper = getWrapper(width);

        done();
    });

    // TODO - Implement
    it("Add user - no prefix", async (done) => {
        const width = 1920;
        const wrapper = getWrapper(width);

        done();
    });

    // TODO - Implement
    it("Add user - taken phone number", async (done) => {
        const width = 1920;
        const wrapper = getWrapper(width);

        done();
    });
});