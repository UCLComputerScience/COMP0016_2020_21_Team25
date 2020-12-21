import {createApp} from 'vue';
import router from "./router/router";
import App from "./app/App.vue";

import './assets/main.css';
import {store} from "./app/store/store";

const app = createApp(App);
app.use(router);
app.use(store);

router.isReady().then(() => {
    app.mount('#app');
});