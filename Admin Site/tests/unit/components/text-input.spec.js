import {mount} from "@vue/test-utils";
import TextInput from "../../../src/app/components/widgets/text-input/text-input.vue";
import {mockRouter, testUsername} from "../util/constants.js";


const model = {
    username: "",
};
const data = {
    id: "test", type: "text", icon: "person",
    maxlength: 30, placeholder: "placeholder",
    label: "some label", noSpaces: false,
    onEnter: (e) => {
    }, disallowedKeys: [], autocomplete: "off",
    object: model, keyName: "username", required: false
};

describe("Text Input Component", () => {
    const getWrapper = ({
                            id, type, icon, maxlength, placeholder,
                            label, noSpaces, onEnter, disallowedKeys,
                            autocomplete, object, keyName, required
                        }) => {
        const wrapper = mount(TextInput, {
            attachTo: window.document.body,
            props: {
                id, type, icon, maxlength, placeholder,
                label, noSpaces, onEnter, disallowedKeys,
                autocomplete, object, keyName, required
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

        const input = wrapper.find("input");
        const inputAttrs = input.attributes();
        expect(inputAttrs["id"]).toEqual(id);
        expect(inputAttrs["type"]).toEqual(type);
        expect(inputAttrs["maxlength"]).toEqual(maxlength.toString());
        expect(inputAttrs["placeholder"]).toEqual(placeholder);
        expect(inputAttrs["autocomplete"]).toEqual(autocomplete);

        const materialIcon = wrapper.find(".material-icons");
        expect(materialIcon.text()).toEqual(icon);

        if (label !== undefined && label.length > 0) {
            const labelElement = wrapper.find("label");
            expect(labelElement.exists()).toBe(true);
            expect(labelElement.attributes()["for"]).toEqual(id);

            let labelText = label;

            if (required) {
                const boldChar = labelElement.find("b");
                expect(boldChar.exists()).toBe(true);
                expect(boldChar.text()).toEqual("*");
                labelText += "*";
            }

            expect(labelElement.text()).toEqual(labelText);
        }

        return wrapper;
    };

    /**
     * Functionality tests
     */
    it("Focus input when focus method called", async (done) => {
        const copy = { ...data };
        const wrapper = getWrapper(copy);
        await wrapper.vm.focus();
        const input = wrapper.find("input");
        expect(input.element).toBe(document.activeElement);
        wrapper.unmount();
        done();
    });

    it("v-model should match text input", async (done) => {
        const copy = { ...data };
        const wrapper = getWrapper(copy);
        const input = wrapper.find("input");
        input.element.value = "Something";
        await input.setValue("Something");
        const text = input.element.value;
        expect(text).toEqual(wrapper.vm.object[wrapper.vm.keyName]);
        expect(text).toEqual("Something");
        done();
    });

    it("Correctly ignore disallowed keys", async (done) => {
        const copy = { ...data };
        copy.disallowedKeys = ["_"];
        const wrapper = getWrapper(copy);
        const input = wrapper.find("input");
        await input.setValue("Something_");
        expect(input.element.value).toEqual(wrapper.vm.object[wrapper.vm.keyName]);
        expect(input.element.value).toEqual("Something");
        done();
    });

    it("Show delete icon when text entered", async (done) => {
        const copy = { ...data };
        const wrapper = getWrapper(copy);

        const input = wrapper.find("input");
        await input.setValue("something");
        const icon = wrapper.find(".delete-icon");
        expect(icon.classes()).toContain("delete-icon-visible");
        done();
    });

    it("Clear input when clearInput method called", async (done) => {
        const copy = { ...data };
        const wrapper = getWrapper(copy);

        const input = wrapper.find("input");
        await input.setValue("something");
        const icon = wrapper.find(".material-icons");
        await wrapper.vm.toggleDelete();
        expect(icon.isVisible()).toBe(true);
        await wrapper.vm.clearInput();
        expect(input.element.value).toEqual(wrapper.vm.object[wrapper.vm.keyName]);
        expect(input.element.value).toEqual("");
        done();
    });

    it("Delete text when delete icon clicked", async (done) => {
        const copy = { ...data };
        const wrapper = getWrapper(copy);
        const input = wrapper.find("input");
        const icon = wrapper.find(".delete-icon");
        await input.setValue("something");
        expect(input.element.value).toEqual(wrapper.vm.object[wrapper.vm.keyName]);
        expect(input.element.value).toEqual("something");
        await icon.trigger("click");
        expect(input.element.value).toEqual(wrapper.vm.object[wrapper.vm.keyName]);
        expect(input.element.value).toEqual("");
        done();
    });

    it("Test onEnter method", async (done) => {
        const methodSpy = spyOn(data, "onEnter");
        const copy = { ...data };
        const wrapper = getWrapper(copy);

        const input = wrapper.find("input");
        await input.trigger("keyup.enter");
        expect(methodSpy).toBeCalledTimes(1);
        done();
    });
});