import {mount} from "@vue/test-utils";
import People from "../../../src/app/views/people/People.vue";
import {store} from "../../../src/store/store.js";
import {mockRouter, testUsername} from "../util/constants.js";


describe("Welcome Card", () => {
    beforeEach(() => {
        store.commit("account/entered", false);
        store.commit("account/newlyRegistered", false);
    });

    const getWrapper = (newUser) => {
        if (newUser) {
            store.commit("account/newlyRegistered", true);
        }

        const wrapper = mount(People, {
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
                stubs: ["FooterLogo", "Navbar", "router-view"],
            }
        });

        const welcomeCard = wrapper.findComponent(".welcome-container");
        expect(welcomeCard.exists()).toBe(true);
        expect(store.getters["account/entered"]).toBe(true);
        expect(store.getters["account/newlyRegistered"]).toBe(false);
        return welcomeCard;

    };

    /**
     * Testing welcome card as a returning user
     */
    it("Returning user", async (done) => {
        const wrapper = getWrapper(false);
        done();
    });

    it("Close as returning user", async (done) => {
        const wrapper = getWrapper(false);
        const card = wrapper.find(".welcome-card");
        const button = wrapper.find(".flat-button");
        await button.trigger("click");
        expect(card.classes()).toContain("exit");
        done();
    });

    /**
     * Testing welcome card as a newly registered user
     */
    it("Newly registered user", async (done) => {
        const wrapper = getWrapper(true);

        done();
    });

    it("Close as newly registered user", async (done) => {
        const wrapper = getWrapper(true);
        const card = wrapper.find(".welcome-card");
        const button = wrapper.find(".flat-button");
        await button.trigger("click");
        expect(card.classes()).toContain("exit");
        done();
    });
});