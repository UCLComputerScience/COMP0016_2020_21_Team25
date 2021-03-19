import {mount} from "@vue/test-utils";
import Dropdown from "../../../src/app/components/widgets/misc/dropdown/dropdown.vue";
import {app, mockRouter, testUsername} from "../util/constants.js";


const methods = {
    "click": () => {
    }
};
const data = {
    label: "Test Label",
    title: "Test Title",
    items: [
        {
            text: "item 1",
            fn: methods["click"],
        },
        {
            text: "item 2",
            fn: methods["click"],
        }
    ]

};

describe("Dropdown Component", () => {
    const getWrapper = ({ label, title, items }) => {
        const wrapper = mount(Dropdown, {
            attachTo: app,
            props: { label, title, items, },
            global: {
                mocks: {
                    $route: {
                        path: "/:username/marketplace",
                        params: { username: testUsername },
                        name: "marketplace"
                    },
                    $router: mockRouter
                },
            }
        });

        const titleEl = wrapper.find("p");
        expect(titleEl.text()).toEqual(title);

        const button = wrapper.find(".dropdown-button");
        expect(button.text()).toEqual(label);

        const children = wrapper.findAll(".dropdown-item");
        expect(children.length).toEqual(items.length);

        return wrapper;
    };

    it("Open Dropdown", async (done) => {
        const wrapper = getWrapper(data);
        const container = wrapper.find(".dropdown-elements");
        const button = wrapper.find(".dropdown-button");
        await button.trigger("click");
        expect(container.classes()).toContain("dropdown-visible");
        done();
    });

    it("Close Dropdown", async (done) => {
        const wrapper = getWrapper(data);
        const container = wrapper.find(".dropdown-elements");
        const button = wrapper.find(".dropdown-button");
        await button.trigger("click");
        expect(container.classes()).toContain("dropdown-visible");
        await button.trigger("click");
        expect(container.classes().includes("dropdown-visible")).toBe(false);
        done();
    });

    it("Toggle Dropdown", async (done) => {
        const wrapper = getWrapper(data);
        const container = wrapper.find(".dropdown-elements");
        const button = wrapper.find(".dropdown-button");
        await button.trigger("click");
        expect(container.classes()).toContain("dropdown-visible");
        done();
    });

    it("Select by item", async (done) => {
        const wrapper = getWrapper(data);
        wrapper.vm.select(data.items[1]);
        expect(wrapper.vm.activeItem.text).toEqual(data.items[1].text);
        done();
    });

    it("Select by text", async (done) => {
        const wrapper = getWrapper(data);
        wrapper.vm.selectByText(data.items[1].text);
        expect(wrapper.vm.activeItem.text).toEqual(data.items[1].text);
        done();
    });

    it("Click dropdown item", async (done) => {
        const wrapper = getWrapper(data);
        const spy = spyOn(wrapper.vm.items[0], "fn");
        wrapper.vm.toggle();
        const children = wrapper.findAll(".dropdown-item");
        const child = children[0];
        await child.trigger("click");
        expect(spy).toBeCalledTimes(1);
        wrapper.unmount();
        done();
    });
});