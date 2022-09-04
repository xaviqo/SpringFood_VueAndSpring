import Vue from 'vue'
import App from './App.vue'
import router from './router'
import vuetify from './plugins/vuetify'
import '@babel/polyfill'
import VueAxios from 'vue-axios'
import axios from 'axios'
import store from '@/store';
import Cloudinary, { CldImage, CldVideo, CldTransformation, CldContext } from "cloudinary-vue";
import { StripePlugin } from '@vue-stripe/vue-stripe';
import setAuthHeader from './setAuthHeader';


const ls = JSON.parse(localStorage.getItem("sf_session"));
if (ls) setAuthHeader(ls.token);


export const EventBus = new Vue();

Vue.use(StripePlugin, {
  pk: `${process.env.VUE_APP_STRIPE_PUB}`
});

Vue.use(Cloudinary, {
  configuration: { cloudName: "xaviqo" },
  components: {
    CldContext,
    CldImage, 
    CldVideo, 
    CldTransformation,
  }
});
Vue.use(VueAxios, axios)

const localBEPort = 7331
axios.defaults.baseURL='http://localhost:'+localBEPort

Vue.config.productionTip = false

new Vue({
  store,
  router,
  vuetify,
  render: h => h(App)
}).$mount('#app')

