import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import * as BootstrapVueNext from 'bootstrap-vue-next';

const app = createApp(App)

app.use(createPinia())
app.use(router)
app.use(BootstrapVueNext)
app.mount('#app')
