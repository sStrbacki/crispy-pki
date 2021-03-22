import App from './App.vue'
import router from './router/index'

import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

import Vue from 'vue'
import VueMeta from 'vue-meta'
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'


Vue.config.productionTip = false
Vue.use(VueMeta)
Vue.use(BootstrapVue)
Vue.use(IconsPlugin)

new Vue({
  render: h => h(App),
  router
}).$mount('#app')
