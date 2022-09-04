<template>
  <nav>
    <v-app-bar app elevation="0" dark flat short color="green">
          <v-col cols="2">

        <v-toolbar-title class="overline text-h6 text-no-wrap" width="100px">
          <v-icon right class="mb-1 mr-4">mdi-leaf</v-icon>
          <span class="font-weight-light">S p r i n g </span>
          <span>F o o d</span>
        </v-toolbar-title>
          </v-col>
      <v-tabs 
      icons-and-text 
      fixed-tabs
      width="30vw"
      ><template v-for="l in links" >
        <v-tab v-if="l.show && l.text!='Log Out'" y="l.text" :to="l.route" class="mt-1">
          <span class="button pt-2">{{ l.text }}</span>
          <v-icon small>{{ l.icon }}</v-icon>
        </v-tab>
        <v-tab v-else-if="l.show && l.text=='Log Out'" y="l.text" @click="sendLogout()" class="mt-1">
          <span class="button pt-2">{{ l.text }}</span>
          <v-icon small>{{ l.icon }}</v-icon>
        </v-tab>
      </template>
      </v-tabs>
      <v-col cols="2">
        <v-alert
          class="mt-3 font-weight-light"
          dense
          width="16vw"
          light
          v-model="globalAlert.show"
          dismissible
          :color="globalAlert.color"
          border="left"
          elevation="0"
          colored-border
          :type="globalAlert.type"
          transition="slide-y-transition"
        >{{globalAlert.msg}}</v-alert>
      </v-col>
    </v-app-bar>
  </nav>
</template>
<style>
.v-toolbar__title {
  width: 250px !important;
}
.menu-border {
  border-left: 5px white solid;
}
</style>
<script>
import App from "@/App.vue";
import { EventBus } from '@/main.js'

export default {
  name: "navbar",
  created() {
  
  EventBus.$on('refresAfterLogin', data => {
    this.loadLocalStorage()
  });
  EventBus.$on('updateAlert', data => {
    this.updateAlert(data);
  });

  },
  data: () => ({
    user: {
      name: ""
    },
    drawer: false,
    pcTime: "",
    links: {
      // TODO: CHANGE 'SHOW' TO FALSE WHEN DEPLOY
      home: {
        icon: "mdi-home",
        text: "Home",
        route: "/",
        show: true
      },
      menu: {
        icon: "mdi-food",
        text: "Menu",
        route: "/menu",
        show: true
      },
      profile: {
        icon: "mdi-account",
        text: "Profile",
        route: "/client",
        show: false
      },
      deliveries: {
        icon: "mdi-truck-delivery",
        text: "Deliveries",
        route: "/admin/deliveries",
        show: false
      },
      dashboard: {
        icon: "mdi-clipboard-text",
        text: "Board",
        route: "/admin/dashboard",
        show: false
      },
      team:       {
        icon: "mdi-account-group",
        text: "Team",
        route: "/admin/team",
        show: false
      },
      stock: {
        icon: "mdi-package-variant",
        text: "Stock",
        route: "/admin/stock",
        show: false
      },
      test: {
        icon: "mdi-test-tube",
        text: "App Test",
        route: "/admin/generator",
        show: false
      },
      login: {
        icon: "mdi-login",
        text: "Log In",
        route: "/login",
        show: true
      },
      logout: {
        icon: "mdi-logout",
        text: "Log Out",
        route: "/logout",
        show: false
      }
    },
    globalAlert: {
        color: "",
        show: false,
        type: "",
        msg: ""
    }
  }),
  methods: {
      loadLocalStorage(){

        const SF_localStorage = JSON.parse(localStorage.getItem("sf_session"));

        if (localStorage.getItem("sf_session") != null){
          this.links.home.show = SF_localStorage.nav_bar.home;
          this.links.menu.show = SF_localStorage.nav_bar.menu;
          this.links.login.show = SF_localStorage.nav_bar.login;
          this.links.deliveries.show = SF_localStorage.nav_bar._deliveries;
          this.links.dashboard.show = SF_localStorage.nav_bar._orders_board;
          this.links.stock.show = SF_localStorage.nav_bar._stock_manager;
          this.links.team.show = SF_localStorage.nav_bar._team_manager;
          this.links.profile.show = SF_localStorage.nav_bar.cprofile;
          this.links.test.show = SF_localStorage.nav_bar._test_app;
          this.links.logout.show = SF_localStorage.nav_bar.logout;

        } else {
          this.anonymousUserDefault();
        }

      },
      sendLogout() {
        localStorage.clear();
        this.anonymousUserDefault();
        this.$router.push('/login');
      },
      anonymousUserDefault() {

          this.links.home.show = true;
          this.links.login.show = true;
          this.links.menu.show = true;
          this.links.deliveries.show = false;
          this.links.dashboard.show = false;
          this.links.stock.show = false;
          this.links.team.show = false;
          this.links.test.show = false;
          this.links.logout.show = false;

      },
      updateAlert(model) {
        this.globalAlert = {
          color: model.color,
          show: model.show,
          type: model.type,
          msg: model.msg
        }
        setTimeout(() => this.globalAlert.show = false, 1900);
      },
      currentTime() {
      let date = new Date();
      let hh = date.getHours();
      let mm = date.getMinutes();
      let ss = date.getSeconds();
      let session = "AM";

      if (hh === 0) {
          hh = 12;
      }
      if (hh > 12) {
          hh = hh - 12;
          session = "PM";
      }

      hh = hh < 10 ? "0" + hh : hh;
      mm = mm < 10 ? "0" + mm : mm;
      ss = ss < 10 ? "0" + ss : ss;

      return hh + ":" + mm + ":" + ss + " " + session;
      }
  },
  components: { App },
  mounted() {
    this.loadLocalStorage();

    this.pcTime = this.currentTime();
    setInterval(() => {
      this.pcTime = this.currentTime();
    }, 300);
  },
};
</script>