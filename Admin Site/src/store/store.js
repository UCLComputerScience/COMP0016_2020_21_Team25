import {createStore} from "vuex";
import createPersistedState from "vuex-persistedstate";
import account from "./modules/account";
import admin from "./modules/admin";
import media from "./modules/media";
import member from "./modules/member";
import service from "./modules/service";

export const store = createStore({
    strict: process.env.NODE_ENV !== 'production',
    plugins: [createPersistedState({
        storage: window.localStorage,
    })],
    modules: {
        member,
        admin,
        account,
        service,
        media,
    },
});