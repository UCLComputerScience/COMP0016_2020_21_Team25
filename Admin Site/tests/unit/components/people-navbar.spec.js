import {mount} from "@vue/test-utils";
import navbar from "../../../src/app/views/people/navbar/navbar.vue";
import {store} from "../../../src/store/store.js";
import {adminWithMembers, app, members, mockRouter, testMembersUsername} from "../util/constants.js";


describe("People Navbar", () => {
    beforeAll(async () => {
        store.commit("admin/setAdmin", adminWithMembers);
        await store.dispatch("admin/profile", adminWithMembers);
        await store.dispatch("member/fetchMembers", testMembersUsername);
    });

    const getWrapper = (path, name, person) => {
        mockRouter.push({
            name, params: { username: testMembersUsername, person: person },
        });
        return mount(navbar, {
            attachTo: app,
            global: {
                plugins: [store],
                mocks: {
                    $route: {
                        path: `/:username/people/:person/${path}`,
                        params: { username: testMembersUsername, person: person },
                        name: name
                    },
                    $router: mockRouter
                },
            }
        });
    };


    it("Click services nav item", async (done) => {
        const user = members[0];
        store.commit("member/setActiveId", user["id"]);
        const person = user["firstName"] + " " + user["lastName"];
        const param = person.replace(/[ ]/g, "-").toLowerCase();
        const wrapper = getWrapper("manage", "user-details", param);
        const element = wrapper.findAllComponents(".people-nav-item")[1];
        const spy = spyOn(element.vm.$router, "push");
        await element.trigger("click");
        expect(spy).toBeCalledWith({ name: "user-services" });
        wrapper.unmount();
        done();
    });
});