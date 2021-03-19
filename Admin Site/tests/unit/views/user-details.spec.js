import {mount} from "@vue/test-utils";
import UserDetails from "../../../src/app/views/people/user-views/user-details/UserDetails.vue";
import {store} from "../../../src/store/store.js";
import {
    adminWithMembers,
    app,
    members,
    mockRouter,
    testLoginPhoneNumber,
    testMembersUsername
} from "../util/constants.js";

const modifiableUser = members[2];
const userId = modifiableUser["id"];

const defaultData = {
    firstName: "Kevin",
    lastName: "Holland",
    prefix: "Mr",
    phoneNumber: "01111111105",
};

describe("User Details View", () => {
    beforeAll(async () => {
        await store.dispatch("admin/profile", adminWithMembers);
        await store.dispatch("member/fetchMembers", testMembersUsername);
    });

    beforeEach(async () => {
        store.commit("member/setActiveId", userId);
        await store.dispatch("member/updateMember", modifiableUser);
    });

    afterAll(async () => {
        await store.dispatch("member/updateMember", modifiableUser);
    });

    const getWrapper = () => {
        return mount(UserDetails, {
            attachTo: app,
            global: {
                plugins: [store],
                mocks: {
                    $route: {
                        path: "/:username/people/:person/details",
                        params: {
                            username: testMembersUsername,
                            person: () => {
                                const allMembers = store.getters["member/members"];
                                const user = allMembers[userId];
                                const person = user["first-name"] + " " + user["last-name"];
                                return person.replace(/[ ]/g, "-").toLowerCase();
                            }
                        },
                        name: "user-details"
                    },
                    $router: mockRouter
                },
                stubs: ["FooterLogo", "Navbar"],
            }
        });
    };

    const test = async ({ firstName, lastName, prefix, phoneNumber }, success, message) => {
        const wrapper = getWrapper();
        const form = wrapper.findComponent(".details-form");
        await form.vm.$nextTick();

        const firstNameInput = wrapper.find("#member-first-name");
        const lastNameInput = wrapper.find("#member-last-name");
        const prefixInput = wrapper.findComponent(".dropdown");
        const phoneNumberInput = wrapper.find("#member-phone-number");

        const testInput = (input, expected) => {
            expect(input.attributes()["placeholder"]).toBe(expected);
        };

        testInput(firstNameInput, modifiableUser["firstName"]);
        testInput(lastNameInput, modifiableUser["lastName"]);
        testInput(phoneNumberInput, modifiableUser["phoneNumber"]);
        expect(prefixInput.vm.activeItem.text).toEqual(modifiableUser["prefix"]);

        await firstNameInput.setValue(firstName);
        await lastNameInput.setValue(lastName);
        await phoneNumberInput.setValue(phoneNumber);

        // prefixInput.vm.toggle();
        // const children = wrapper.findAll(".dropdown-item");
        // const child = children[0];
        // await child.trigger("click");
        await prefixInput.vm.selectByText(prefix);
        // await form.vm.$nextTick();
        // await flushPromises();

        expect(form.vm.form.firstName).toEqual(firstName);
        expect(form.vm.form.lastName).toEqual(lastName);
        expect(form.vm.form.prefix).toEqual(prefix);
        expect(form.vm.form.phoneNumber).toEqual(phoneNumber);

        const alertSpy = spyOn(window, "alert");
        await form.vm.updateMember();
        expect(form.vm.form.success).toEqual(success);
        expect(alertSpy).toBeCalledTimes(1);
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
        }
    };

    it("Update profile picture", async (done) => {
        const wrapper = getWrapper();
        const toggler = wrapper.find(".user-picture-container");
        await toggler.trigger("click");

        const dialog = wrapper.findComponent(".profile-pic-chooser");
        const image = dialog.find(".pic-grid img");
        await image.trigger("click");

        await wrapper.vm.updateProfilePic();
        const member = store.getters["member/activeMember"];
        expect(member["profile-picture"]).toBe(image.attributes()["id"]);
        done();
    });

    // TODO - User id will not be the same if removing and adding a new user
    // it("Remove member", async (done) => {
    //     const wrapper = getWrapper();
    //     const form = wrapper.findComponent(".details-form");
    //     await form.vm.removeMember();
    //     const memberIds = store.getters["member/memberIds"];
    //     expect(memberIds.includes(userId)).toBe(false);
    //     await store.dispatch("member/addMember", modifiableUser);
    //     done();
    // });

    it("Blank first name", async (done) => {
        const data = { ...defaultData };
        data["firstName"] = "";
        const message = "Ok";
        await test(data, true, message);
        done();
    });

    it("Blank last name", async (done) => {
        const data = { ...defaultData };
        data["lastName"] = "";
        const message = "Ok";
        await test(data, true, message);
        done();
    });

    it("Blank phone number", async (done) => {
        const data = { ...defaultData };
        data["phoneNumber"] = "";
        const message = "Ok";
        await test(data, true, message);
        done();
    });

    it("Invalid phone number", async (done) => {
        const data = { ...defaultData };
        data["phoneNumber"] = "123";
        const message = "The entered phone number is invalid.";
        await test(data, false, message);
        done();
    });

    it("Taken phone number", async (done) => {
        const data = { ...defaultData };
        data["phoneNumber"] = testLoginPhoneNumber;
        const message = `The phone number ${data["phoneNumber"]} is already associated with another account.`;
        await test(data, false, message);
        done();
    });

    it("Update valid", async (done) => {
        const data = { ...defaultData };
        const message = "Ok";
        await test(data, true, message);
        done();
    });
});