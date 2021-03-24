import api from "../../src/backend/api.js";
import {store} from "../../src/store/store.js";
import {admin, app} from "./util/constants.js";

const windowScroll = window.scrollTo;
const windowAlert = window.alert;
const windowPrompt = window.prompt;
const windowConfirm = window.confirm;

const waitForBackend = async () => {
    let response = await api.ping();
    while (!response.success) {
        response = await api.ping();
    }
};

global.beforeAll(async () => {
    // await waitForBackend();
    jest.setTimeout(45000);
    prompt = global.scrollTo = scrollTo = alert = window.scrollTo = window.alert = window.prompt = jest.fn();
    confirm = window.confirm = () => {
        return true;
    };

    window.document.body.appendChild(app);
    const description = document.createElement("meta");
    description.setAttribute("name", "description");
    document.head.appendChild(description);

    await store.dispatch("service/setServices");
    await store.dispatch("media/setProfileImages");
    store.commit("admin/setAdmin", admin);
    await store.dispatch("admin/profile", admin);
    await store.dispatch("member/fetchMembers", admin["username"]);
});

global.afterAll(async () => {
    window.scrollTo = scrollTo = windowScroll;
    window.alert = alert = windowAlert;
    window.prompt = prompt = windowPrompt;
    window.confirm = confirm = windowConfirm;
    store.commit("admin/setAdmin", admin);
    await store.dispatch("admin/profile", admin);
});

global.beforeEach(() => {
    store.commit("admin/setAdmin", admin);
});

global.afterEach(() => {
});