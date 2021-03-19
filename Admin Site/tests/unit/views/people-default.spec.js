import {mount} from "@vue/test-utils";
import PeopleDefault from "../../../src/app/views/people/PeopleDefault.vue";
import router from "../../../src/router/router.js";
import {store} from "../../../src/store/store.js";
import {adminWithMembers, app, loginAdmin, mockRouter} from "../util/constants.js";
import {resize} from "../util/util.js";



describe("People Default View", () => {
    const getWrapper = async (width, admin) => {
        store.commit("admin/setAdmin", admin);
        await store.dispatch("admin/fetchAdmin", admin["username"]);
        const wrapper = mount(PeopleDefault, {
            attachTo: app,
            global: {
                plugins: [store, router],
                mocks: {
                    $route: {
                        path: "/:username/people",
                        params: { username: admin["username"] },
                        name: "people"
                    },
                    $router: mockRouter
                },
                stubs: ["Navbar", "sidebar", "FooterLogo"]
            }
        });

        wrapper.vm.setText();
        resize(width, 1080);
        return wrapper;
    };

    it("Width 1920 with members", async (done) => {
        const width = 1920;
        await getWrapper(width, adminWithMembers);
        done();
    });

    it("Width 1920 no members", async (done) => {
        const width = 1920;
        await getWrapper(width, loginAdmin);
        done();
    });

    it("Width 1024 with members", async (done) => {
        const width = 1024;
        await getWrapper(width, adminWithMembers);
        done();
    });

    it("Width 1024 no members", async (done) => {
        const width = 1920;
        await getWrapper(width, loginAdmin);
        done();
    });

    it("Width 1000 with members", async (done) => {
        const width = 1000;
        await getWrapper(width, adminWithMembers);
        done();
    });

    it("Width 1000 no members", async (done) => {
        const width = 1000;
        const wrapper = await getWrapper(width, loginAdmin);
        wrapper.unmount();
        done();
    });
});