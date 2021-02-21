import {createStore} from "vuex";
import createPersistedState from "vuex-persistedstate";
import member from "./modules/member";
import admin from "./modules/admin";
import account from "./modules/account";
import service from "./modules/service";
import media from "./modules/media";

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