import {mount} from "@vue/test-utils";
import ServiceView from "../../../src/app/views/marketplace/ServiceView.vue";
import {store} from "../../../src/store/store.js";
import {
    adminWithMembers,
    charityServiceCategory,
    charityServiceId,
    mockRouter,
    testMembersUsername,
    userIdWithServices
} from "../util/constants.js";


const reset = async () => {
    const allMembers = store.getters["member/members"];
    for (const userId of Object.keys(allMembers)) {
        if (userId !== userIdWithServices.toString()) {
            store.commit("member/setActiveId", userId);
            await store.dispatch("member/removeServiceFromMember", charityServiceId);
        }
    }
    store.commit("member/setActiveId", null);
};

describe("Service View", () => {
    beforeAll(async () => {
        store.commit("admin/setAdmin", adminWithMembers);
        await store.dispatch("admin/fetchAdmin", testMembersUsername);
        await store.dispatch("admin/profile", adminWithMembers);
        const allServices = store.getters["service/allServices"];
        for (const service of allServices[charityServiceCategory]) {
            if (service["service_id"] === charityServiceId) {
                store.commit("service/setService", service);
                break;
            }
        }
    });

    beforeEach(async () => {
        await reset();
    });

    afterAll(async () => {
        await reset();
    });

    const getWrapper = () => {
        return mount(ServiceView, {
            global: {
                plugins: [store],
                mocks: {
                    $route: {
                        path: "/:username/marketplace/service",
                        params: {
                            username: testMembersUsername,
                            "service-id": charityServiceId
                        },
                        name: "service"
                    },
                    $router: mockRouter
                },
                stubs: ["FooterLogo", "Navbar"],
            }
        });
    };

    it("Mount", async (done) => {
        const wrapper = getWrapper();
        done();
    });

    it("Select user", async (done) => {
        const wrapper = getWrapper();

        const user = wrapper.find("li");
        await user.trigger("click");
        expect(user.classes()).toContain("active-user");

        const confirmButton = wrapper.find(".flat-button");
        expect(confirmButton.classes().includes("disabled")).toBe(false);
        done();
    });

    it("Select multiple users", async (done) => {
        const wrapper = getWrapper();

        const users = wrapper.findAllComponents("li");
        for (const user of users) {
            await user.vm.setHasService();
            await user.trigger("click");
            expect(user.classes()).toContain("active-user");
        }
        expect(users.length).toEqual(wrapper.vm.users.length);

        const confirmButton = wrapper.find(".flat-button");
        expect(confirmButton.classes().includes("disabled")).toBe(false);
        done();
    });

    it("Select and then deselect user", async (done) => {
        const wrapper = getWrapper();
        const user = wrapper.find("li");
        await user.trigger("click");
        expect(user.classes()).toContain("active-user");
        await user.trigger("click");
        expect(user.classes().includes("active-user")).toBe(false);
        const confirmButton = wrapper.find(".flat-button");
        expect(confirmButton.classes().includes("disabled")).toBe(true);
        done();
    });

    it("Select user and confirm", async (done) => {
        const wrapper = getWrapper();
        const user = wrapper.findComponent("li");
        await user.trigger("click");
        await wrapper.vm.confirm();

        await store.dispatch("member/fetchMemberServices", user.vm.userId);
        const services = store.getters["member/memberServices"];
        expect(services.some(({ service_id }) => service_id === charityServiceId)).toBe(true);
        done();
    });

    it("Select multiple users and confirm", async (done) => {
        const wrapper = getWrapper();

        const users = wrapper.findAllComponents("li");
        for (const user of users) {
            await user.vm.setHasService();
            await user.trigger("click");
            expect(user.classes()).toContain("active-user");
        }
        expect(users.length).toEqual(wrapper.vm.users.length);

        await wrapper.vm.confirm();

        for (const user of users) {
            await store.dispatch("member/fetchMemberServices", user.vm.userId);
            const services = store.getters["member/memberServices"];
            expect(services.some(({ service_id }) => service_id === charityServiceId)).toBe(true);
        }
        done();
    });

    it("Select and confirm user who already has service", async (done) => {
        const wrapper = getWrapper();

        const allMembers = store.getters["member/members"];
        const memberWhoAlreadyHasService = allMembers[userIdWithServices];
        const firstName = memberWhoAlreadyHasService["first-name"].toLowerCase();
        const lastName = memberWhoAlreadyHasService["last-name"].toLowerCase();
        const user = wrapper.findComponent(`#${firstName}-${lastName}-${userIdWithServices}`);
        await user.trigger("click");

        await wrapper.vm.confirm();

        await store.dispatch("member/fetchMemberServices", user.vm.userId);
        const services = store.getters["member/memberServices"];
        expect(services.some(({ service_id }) => service_id === charityServiceId)).toBe(true);
        done();
    });
});