import {mount} from "@vue/test-utils";
import Profile from "../../../src/app/views/profile/Profile.vue";
import {store} from "../../../src/store/store.js";
import {admin, app, mockRouter, testUsername} from "../util/constants.js";


let confirmSpy;

describe("Profile View", () => {
    beforeAll(() => {
        confirmSpy = jest.spyOn(window, 'confirm');
        confirmSpy.mockImplementation(jest.fn(() => true));
    });

    beforeEach(async () => {
        await store.dispatch("admin/profile", admin);
    });

    afterAll(async () => {
        confirmSpy.mockRestore();
    });

    const getWrapper = () => {
        return mount(Profile, {
            attachTo: app,
            global: {
                plugins: [store],
                mocks: {
                    $route: {
                        path: "/:username/profile",
                        params: { username: testUsername },
                        name: "profile"
                    },
                    $router: mockRouter,
                },
                stubs: ["Navbar", "FooterLogo", "welcome-card"]
            },
        });
    };

    it("Update profile picture", async (done) => {
        const wrapper = getWrapper();
        const toggler = wrapper.find(".profile-picture-container");
        await toggler.trigger("click");

        const dialog = wrapper.findComponent(".profile-pic-chooser");
        const image = dialog.find(".pic-grid img");
        await image.trigger("click");

        await wrapper.vm.updateProfilePic();
        const admin = store.getters["admin/admin"];
        expect(admin["profile-picture"]).toBe(image.attributes()["id"]);
        done();
    });

    it("Open profile picture dialog", async (done) => {
        const wrapper = getWrapper();
        const toggler = wrapper.find(".profile-picture-container");
        await toggler.trigger("click");
        const dialogEl = wrapper.find(".profile-pic-chooser");
        expect(dialogEl.isVisible()).toBe(true);
        done();
    });

    it("Close profile picture dialog", async (done) => {
        const wrapper = getWrapper();
        const toggler = wrapper.find(".profile-picture-container");
        await toggler.trigger("click");
        const dialog = wrapper.findComponent(".profile-pic-chooser");
        const dialogEl = wrapper.find(".profile-pic-chooser");
        await dialog.vm.close();
        expect(dialogEl.isVisible()).toBe(false);
        done();
    });

    it("Logout", async (done) => {
        const wrapper = getWrapper();
        const logoutButton = wrapper.find(".logout-button");
        await logoutButton.trigger("click");
        expect(confirmSpy).toBeCalledTimes(1);
        done();
    });

    it("Open profile picture dialog and select item", async (done) => {
        const wrapper = getWrapper();
        const toggler = wrapper.find(".profile-picture-container");
        await toggler.trigger("click");
        const dialog = wrapper.findComponent(".profile-pic-chooser");
        const image = dialog.find(".pic-grid .image img");
        await image.trigger("click");
        expect(dialog.vm.data.selected).toEqual(image.attributes()["id"]);
        done();
    });

    it("Cancel profile picture update with button", async (done) => {
        const wrapper = getWrapper();
        const toggler = wrapper.find(".profile-picture-container");
        await toggler.trigger("click");
        const dialog = wrapper.findComponent(".profile-pic-chooser");
        const dialogEl = wrapper.find(".profile-pic-chooser");
        const cancelButton = dialog.find(".cancel");
        await cancelButton.trigger("click");
        expect(dialogEl.isVisible()).toBe(false);
        done();
    });

    it("Cancel profile picture update with icon", async (done) => {
        const wrapper = getWrapper();
        const toggler = wrapper.find(".profile-picture-container");
        await toggler.trigger("click");
        const dialogEl = wrapper.find(".profile-pic-chooser");
        const cancelButton = dialogEl.find(".material-icons");
        await cancelButton.trigger("click", { name: 0 });
        expect(dialogEl.isVisible()).toBe(false);
        done();
    });
});