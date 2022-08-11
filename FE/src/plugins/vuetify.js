import '@mdi/font/css/materialdesignicons.css' ;
import Vue from 'vue';
import Vuetify from 'vuetify';
import 'vuetify/dist/vuetify.min.css';

Vue.use(Vuetify);

export default new Vuetify({
  icons: {
    iconfont: 'mdi'
  },
  theme: {
      options: {
        customProperties: true,
      },
    themes: {
      light: {
        primary: '#78c2ad',
        secondary: '#ff825e',
        accent: '#82B1FF',
        error: '#ff825e',
        info: '#2196F3',
        success: '#78c2ad',
        warning: '#FFC107',
        green: '#78c2ad',
        red: '#ff825e'
      },
      dark: {
        primary: '#007BFF',
        secondary: '#ff825e',
        accent: '#82B1FF',
        error: '#ff825e',
        info: '#2196F3',
        success: '#4CA78c2adF50',
        warning: '#FFC107',
        green: '#78c2ad',
        red: '#ff825e'
      },
    },
  },
});
