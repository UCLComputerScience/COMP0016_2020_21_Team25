import {createApp} from "vue";
import router from "./router/router";
import App from "./app/App.vue";

import "./assets/main.css";
import {store} from "./store/store";

const app = createApp(App);
app.use(router);
app.use(store);
app.mount("#app");
