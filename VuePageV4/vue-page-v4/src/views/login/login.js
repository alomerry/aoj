// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue/dist/vue.js'
import App from './App'
import store from '../../store/store'// 使用 状态管理
import iView from 'iview'//使用 iview UI库
import 'iview/dist/styles/iview.css' // 使用 CSS

import axios from 'axios'//使用ajax


Vue.prototype.$axios = axios;

Vue.use(iView);

Vue.config.productionTip = false;

/* eslint-disable no-new */
new Vue({
    el: '#logs',
    store, //注册store
    components: {App},
    template: '<App/>',
    created() {

    }
});
