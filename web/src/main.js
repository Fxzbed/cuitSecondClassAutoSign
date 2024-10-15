import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ECharts from 'vue-echarts';
import 'echarts';

// import Vue from 'vue'

// Vue.prototype.$axios = axios

// Vue.prototype.productionTip = false

// axios.defaults.withCredentials = true
createApp(App)
    .component('ECharts', ECharts).use(store).use(router).mount('#app')
