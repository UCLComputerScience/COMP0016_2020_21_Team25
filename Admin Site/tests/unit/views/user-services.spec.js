import {mount} from "@vue/test-utils";
import UserServices from "../../../src/app/views/people/user-views/user-services/UserServices.vue";
import {getName} from "../../../src/assets/scripts/util.js";
import {store} from "../../../src/store/store.js";
import {
    adminWithMembers,
    app,
    charityServiceId,
    mockRouter,
    testMembersUsername,
    userIdWithServices
} from "../util/constants.js";


const userIdWithNoServices = '104';


describe("User Services View", () => {
    beforeAll(async () => {
        store.commit("admin/setAdmin", adminWithMembers);
        await store.dispatch("admin/fetchAdmin", testMembersUsername);
        await store.dispatch("admin/profile", adminWithMembers);
    });

    beforeEach(async () => {
        // TODO - Restore member data
    });

    const getWrapper = (userId = userIdWithServices) => {
        store.commit("member/setActiveId", userId);
        return mount(UserServices, {
            attachTo: app,
            global: {
                plugins: [store],
                mocks: {
                    $route: {
                        path: "/:username/people/:person/services",
                        params: {
                            username: testMembersUsername,
                            person: () => {
                                const allMembers = store.getters["member/members"];
                                const user = allMembers[userId];
                                const person = user["first-name"] + " " + user["last-name"];
                                return person.replace(/[ ]/g, "-").toLowerCase();
                            }
                        },
                        name: "user-services"
                    },
                    $router: mockRouter
                },
                stubs: ["FooterLogo", "Navbar", "v-link"],
            }
        });
    };

    const searchTest = async (searchTerm, expectedNumberOfResults, visible) => {
        const wrapper = getWrapper();
        await wrapper.vm.updateServices();
        const searchBar = wrapper.get("input");
        await searchBar.setValue(searchTerm);
        expect(searchBar.element.value).toEqual(searchTerm);
        const container = wrapper.find(".search-results");
        expect(container.isVisible()).toBe(visible);

        if (visible) {
            await wrapper.vm.search();
            await wrapper.vm.$nextTick();
            const searchResults = wrapper.vm.searchResults;
            expect(searchResults.length).toEqual(expectedNumberOfResults);
            const expectingResults = expectedNumberOfResults > 0;
            const noItemsFoundLabel = wrapper.find(".search-results > span");
            expect(noItemsFoundLabel.isVisible()).toBe(!expectingResults);
        }
        wrapper.unmount();
    };

    it("User with no services", async (done) => {
        const wrapper = getWrapper(userIdWithNoServices);
        const text = `No services added. Head over to the    to add services to ${getName()} account.`;
        const noServicesLabel = wrapper.find(".services-content > span");
        expect(wrapper.vm.noServices).toBe(true);
        expect(noServicesLabel.isVisible()).toBe(true);
        expect(noServicesLabel.text()).toEqual(text);
        done();
    });

    it("Expand a Service", async (done) => {
        const wrapper = getWrapper();
        await wrapper.vm.updateServices();
        const service = wrapper.find(".service");
        const expandLabel = service.find(".expand-icon");
        await expandLabel.trigger("click");
        expect(service.classes()).toContain("expanded");
        done();
    });

    it("Expand and then close a service", async (done) => {
        const wrapper = getWrapper();
        await wrapper.vm.updateServices();
        const service = wrapper.find(".service");
        const expandLabel = service.find(".expand-icon");
        await expandLabel.trigger("click");
        await expandLabel.trigger("click");
        expect(service.classes().includes("expanded")).toBe(false);
        done();
    });

    it("Remove service", async (done) => {
        const wrapper = getWrapper();
        await wrapper.vm.updateServices();
        const service = wrapper.findComponent(".service");
        await service.vm.removeService();
        await store.dispatch("member/fetchMemberServices", userIdWithServices);
        const services = store.getters["member/memberServices"];
        expect(services.some(({ service_id }) => service_id === charityServiceId)).toBe(false);

        await store.dispatch("member/addServiceToMembers", {
            serviceId: charityServiceId,
            members: [{ id: userIdWithServices }],
            response: {}
        });
        done();
    });

    it("Open service in marketplace", async (done) => {
        const wrapper = getWrapper();
        await wrapper.vm.updateServices();
        const service = wrapper.findComponent(".service");
        const button = service.find(".button-row > *:first-child");
        await button.trigger("click");
        expect(mockRouter.currentRoute.name).toBe("marketplace");
        expect(mockRouter.currentRoute.hash).toBe(`#${service.vm.id}`);
        done();
    });

    it("Update service data", async (done) => {
        const wrapper = getWrapper();

        done();
    });

    it("Blank search term", (done) => {
        const searchTerm = "";
        const expectedNumberOfResults = 0;
        const visible = false;
        searchTest(searchTerm, expectedNumberOfResults, visible).then(_ => {
            done();
        });
    });

    it("Search term too short", (done) => {
        const searchTerm = "123";
        const expectedNumberOfResults = 0;
        const visible = false;
        searchTest(searchTerm, expectedNumberOfResults, visible).then(_ => {
            done();
        });
    });

    it("Valid search term (4 chars)", (done) => {
        const searchTerm = "char";
        const expectedNumberOfResults = 1;
        const visible = true;
        searchTest(searchTerm, expectedNumberOfResults, visible).then(_ => {
            done();
        });
    });

    it("Valid search term (> 4 chars)", (done) => {
        const searchTerm = "charity";
        const expectedNumberOfResults = 1;
        const visible = true;
        searchTest(searchTerm, expectedNumberOfResults, visible).then(_ => {
            done();
        });
    });

    it("No matching services", (done) => {
        const searchTerm = "noservicematches";
        const expectedNumberOfResults = 0;
        const visible = true;
        searchTest(searchTerm, expectedNumberOfResults, visible).then(_ => {
            done();
        });
    });
});