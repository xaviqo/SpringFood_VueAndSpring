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
        secondary: '#ff997b',
        accent: '#82B1FF',
        error: '#ff997b',
        info: '#2196F3',
        success: '#78c2ad',
        warning: '#FFC107',
        green: '#78c2ad',
        red: '#ff997b'
      },
      dark: {
        primary: '#007BFF',
        secondary: '#ff997b',
        accent: '#82B1FF',
        error: '#ff997b',
        info: '#2196F3',
        success: '#78c2ad',
        warning: '#FFC107',
        green: '#78c2ad',
        red: '#ff997b'
      },
    },
  },
});
