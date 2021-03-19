import {flushPromises, mount} from "@vue/test-utils";
import UserHistory from "../../../src/app/views/people/user-views/user-history/UserHistory.vue";
import {store} from "../../../src/store/store.js";
import {adminWithMembers, app, mockRouter, testMembersUsername, userIdWithServices} from "../util/constants.js";

describe("User History View", () => {
    beforeAll(async () => {
        await store.dispatch("admin/fetchAdmin", testMembersUsername);
        await store.dispatch("admin/profile", adminWithMembers);
    });

    beforeEach(async () => {
    });

    afterAll(() => {
    });

    const getWrapper = async (userId) => {
        store.commit("member/setActiveId", userId);
        await store.dispatch("member/fetchHistory");
        return mount(UserHistory, {
            attachTo: app,
            global: {
                plugins: [store],
                mocks: {
                    $route: {
                        path: "/:username/people/:person/history",
                        params: {
                            username: testMembersUsername,
                            person: () => {
                                const allMembers = store.getters["member/members"];
                                const user = allMembers[userId];
                                const person = user["first-name"] + " " + user["last-name"];
                                return person.replace(/[ ]/g, "-").toLowerCase();
                            }
                        },
                        name: "user-history"
                    },
                    $router: mockRouter
                },
                stubs: ["FooterLogo", "Navbar"],
            }
        });
    };

    it("User with no history", async (done) => {
        const userId = '104';
        const wrapper = await getWrapper(userId);
        expect(wrapper.vm.noHistory).toBe(true);
        done();
    });

    it("User with history", async (done) => {
        const wrapper = await getWrapper(userIdWithServices);
        await store.dispatch("member/fetchHistory");
        expect(wrapper.vm.noHistory).toBe(false);
        done();
    });

    it("Click history item", async (done) => {
        const wrapper = await getWrapper(userIdWithServices);
        const item = wrapper.findComponent(".history-item");
        await item.trigger("click");
        expect(mockRouter.currentRoute.name).toBe("marketplace");
        expect(mockRouter.currentRoute.hash).toBe(`#${item.vm.id}`);
        done();
    });

});