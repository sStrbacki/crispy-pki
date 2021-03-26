import App from './App.vue'
import router from './router/index'
import store from './store/index'

import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

import Vue from 'vue'
import VueMeta from 'vue-meta'
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'
import Notifications from 'vue-notification'


Vue.config.productionTip = false
Vue.use(VueMeta)
Vue.use(BootstrapVue)
Vue.use(IconsPlugin)
Vue.use(Notifications)

new Vue({
  store,
  router,
  render: h => h(App),
}).$mount('#app')
