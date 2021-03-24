import {flushPromises, mount} from "@vue/test-utils";
import sidebar from "../../../src/app/views/people/sidebar/sidebar.vue";
import {store} from "../../../src/store/store.js";
import {adminWithMembers, app, mockRouter, testLoginPhoneNumber, testMembersUsername} from "../util/constants.js";
import {resize} from "../util/util.js";

const defaultData = {
    firstName: "Kevin",
    lastName: "Holland",
    prefix: "Mr",
    phoneNumber: "00100131101",
    profilePicture: 37,
};


describe("People Sidebar", () => {
    beforeAll(async () => {
        store.commit("admin/setAdmin", adminWithMembers);
        await store.dispatch("admin/profile", adminWithMembers);
        await store.dispatch("member/fetchMembers", testMembersUsername);
    });

    beforeEach(() => {
        store.commit("admin/setAdmin", adminWithMembers);
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
                        params: { username: testMembersUsername },
                        name: "people"
                    },
                    $router: mockRouter
                },
            }
        });

        resize(width, 1080);
        return wrapper;
    };

    const testAddUser = async ({ firstName, lastName, prefix, phoneNumber, profilePicture },
                               success, message) => {
        const wrapper = getWrapper(1920);
        const addUserButton = wrapper.find(".add-icon");
        await addUserButton.trigger("click");
        const dialog = wrapper.vm.$refs["dialog"];
        const form = wrapper.findComponent(".add-user-form");
        await form.vm.$nextTick();

        const firstNameInput = wrapper.find("#member-first-name");
        const lastNameInput = wrapper.find("#member-last-name");
        const prefixInput = wrapper.findComponent(".dropdown");
        const phoneNumberInput = wrapper.find("#member-phone-number");

        if (profilePicture !== "") {
            const toggler = wrapper.find(".profile-pic-button");
            await toggler.trigger("click");
            const profilePicChooser = wrapper.findComponent(".profile-pic-chooser");
            const image = profilePicChooser.find(".pic-grid img");
            await image.trigger("click");
            await dialog.setProfilePicture();
            expect(form.vm.form.profilePicture).toBe(image.attributes()["id"]);
        }

        await firstNameInput.setValue(firstName);
        await lastNameInput.setValue(lastName);
        await phoneNumberInput.setValue(phoneNumber);

        if (prefix !== "") {
            await prefixInput.vm.selectByText(prefix);
            expect(form.vm.form.prefix).toEqual(prefix);
        }

        expect(form.vm.form.firstName).toEqual(firstName);
        expect(form.vm.form.lastName).toEqual(lastName);
        expect(form.vm.form.phoneNumber).toEqual(phoneNumber);

        const alertSpy = spyOn(window, "alert");
        await dialog.confirm();
        expect(form.vm.form.success).toEqual(success);
        expect(form.vm.form.response).toEqual(message);

        if (success) {
            const member = store.getters["member/activeMember"];
            const testNewValue = (newValue, keyName) => {
                if (newValue.length > 0) {
                    expect(member[keyName]).toBe(newValue);
                }
            };
            testNewValue(firstName, "first-name");
            testNewValue(lastName, "last-name");
            testNewValue(prefix, "prefix");
            testNewValue(phoneNumber, "phone-number");
            await store.dispatch("member/removeMember", store.getters["member/activeId"]);
        } else {
            expect(alertSpy).toBeCalledTimes(1);
        }
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

    it("Add user - valid data", async (done) => {
        const data = { ...defaultData };
        const success = true;
        const message = "Ok";
        await testAddUser(data, success, message);
        done();
    });

    it("Add user - blank data", async (done) => {
        const data = { ...defaultData };
        data["firstName"] = "";
        data["lastName"] = "";
        data["prefix"] = "";
        data["phoneNumber"] = "";
        data["profilePicture"] = "";
        const success = false;
        const message = "Please select a profile picture.";
        await testAddUser(data, success, message);
        done();
    });

    it("Add user - no profile picture", async (done) => {
        const data = { ...defaultData };
        data["profilePicture"] = "";
        const success = false;
        const message = "Please select a profile picture.";
        await testAddUser(data, success, message);
        done();
    });

    it("Add user - no first name", async (done) => {
        const data = { ...defaultData };
        data["firstName"] = "";
        const success = false;
        const message = "Please enter a first name.";
        await testAddUser(data, success, message);
        done();
    });

    it("Add user - no last name", async (done) => {
        const data = { ...defaultData };
        data["lastName"] = "";
        const success = false;
        const message = "Please enter a last name.";
        await testAddUser(data, success, message);
        done();
    });

    it("Add user - no phone number", async (done) => {
        const data = { ...defaultData };
        data["phoneNumber"] = "";
        const success = false;
        const message = "Please enter a phone number.";
        await testAddUser(data, success, message);
        done();
    });

    it("Add user - invalid phone number", async (done) => {
        const data = { ...defaultData };
        data["phoneNumber"] = "123";
        const success = false;
        const message = "The entered phone number is invalid.";
        await testAddUser(data, success, message);
        done();
    });

    it("Add user - no prefix", async (done) => {
        const data = { ...defaultData };
        data["prefix"] = "";
        const success = false;
        const message = "Please select a prefix.";
        await testAddUser(data, success, message);
        done();
    });

    it("Add user - taken phone number", async (done) => {
        const data = { ...defaultData };
        data["phoneNumber"] = testLoginPhoneNumber;
        const success = false;
        const message = `The phone number ${data["phoneNumber"]} is already associated with another account.`;
        await testAddUser(data, success, message);
        done();
    });

    it("Add user - close dialog", async (done) => {
        const wrapper = getWrapper(1920);
        const addUserButton = wrapper.find(".add-icon");
        await addUserButton.trigger("click");
        const dialog = wrapper.find(".add-user-dialog > *");
        const cancelButton = dialog.find(".cancel");
        await cancelButton.trigger("click");
        expect(dialog.isVisible()).toBe(false);
        done();
    });
});