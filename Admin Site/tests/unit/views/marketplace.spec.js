import {mount} from "@vue/test-utils";
import MarketplaceView from "../../../src/app/views/marketplace/MarketplaceView.vue";
import {store} from "../../../src/store/store.js";
import {mockRouter, testUsername} from "../util/constants.js";


const getWrapper = async (searchTerm) => {
    const wrapper = mount(MarketplaceView, {
        global: {
            plugins: [store],
            mocks: {
                $route: {
                    path: "/:username/marketplace",
                    params: { username: testUsername },
                    name: "marketplace"
                },
                $router: mockRouter
            }
        },
    });

    const searchBar = wrapper.get("input");
    await searchBar.setValue(searchTerm);
    expect(searchBar.element.value).toEqual(searchTerm);
    return wrapper;
};

const searchTest = async (searchTerm, expectedNumberOfResults, visible) => {
    const wrapper = await getWrapper(searchTerm);
    const container = wrapper.find("#search-results");
    expect(container.exists()).toBe(visible);

    if (visible) {
        await wrapper.vm.search();
        await wrapper.vm.$nextTick();
        const searchResults = wrapper.vm.searchResults;
        expect(searchResults.length).toEqual(expectedNumberOfResults);
        const expectingResults = expectedNumberOfResults > 0;
        const noItemsFoundLabel = container.find("span");
        expect(noItemsFoundLabel.isVisible()).toBe(!expectingResults);
    }
    wrapper.unmount();
};

describe("Marketplace View", () => {
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
        const searchTerm = "joke";
        const expectedNumberOfResults = 1;
        const visible = true;
        searchTest(searchTerm, expectedNumberOfResults, visible).then(_ => {
            done();
        });
    });

    it("Valid search term (> 4 chars)", (done) => {
        const searchTerm = "jokes";
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