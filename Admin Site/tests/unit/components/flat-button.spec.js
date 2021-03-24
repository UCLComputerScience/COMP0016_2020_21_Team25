import {mount} from "@vue/test-utils";
import FlatButton from "../../../src/app/components/widgets/buttons/flat-button.vue";
import {app, mockRouter, testUsername} from "../util/constants.js";


describe("Flat Button Component", () => {
    const getWrapper = (type, text) => {
        const wrapper = mount(FlatButton, {
            attachTo: app,
            props: {
                type,
                text
            },
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
        expect(wrapper.text()).toEqual(text);
        if (type.length > 0) {
            expect(wrapper.attributes()["type"]).toEqual(type);
        }
        return wrapper;
    };

    it("Type and text", async () => {
        const wrapper = getWrapper("button", "TEST BUTTON");
    });

    it("No Type and text", async () => {
        const wrapper = getWrapper("", "TEST BUTTON");
    });

    it("Type and no text", async () => {
        const wrapper = getWrapper("button", "");
    });

    it("No type and no text", async () => {
        const wrapper = getWrapper("", "");
        wrapper.unmount();
    });

    it("Enable and disable test", async () => {
        const wrapper = getWrapper("button", "TEST BUTTON");
        wrapper.vm.disable();
        expect(wrapper.classes()).toContain("disabled");
        expect(wrapper.classes().length).toEqual(4);
        wrapper.vm.enable();
        expect(wrapper.classes().length).toEqual(3);
    });
});