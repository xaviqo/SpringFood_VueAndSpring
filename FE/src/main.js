import Vue from 'vue'
import App from './App.vue'
import router from './router'
import vuetify from './plugins/vuetify'
import '@babel/polyfill'
import VueAxios from 'vue-axios'
import axios from 'axios'

Vue.use(VueAxios, axios)

let localBEPort = 7331
axios.defaults.baseURL='http://localhost:'+localBEPort

Vue.config.productionTip = false

new Vue({
  router,
  vuetify,
  render: h => h(App)
}).$mount('#app')
