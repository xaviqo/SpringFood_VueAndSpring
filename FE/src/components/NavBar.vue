<template>
  <nav>
    <v-app-bar app elevation="0" dark flat short color="green">
      <v-app-bar-nav-icon @click.stop="drawer = !drawer" />
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
      <v-col cols="2"></v-col>
    </v-app-bar>
    <v-navigation-drawer v-model="drawer" app color="green">
      <v-layout column align-center>
        <v-flex class="mt-5">
          <v-avatar size="180">
            <v-img
              gradient="to top right, rgba(87,161,65,.10), rgba(0,0,0,.33)"
              src="https://i.pravatar.cc/180"
            >
              <template v-slot:placeholder>
                <v-row class="fill-height ma-0" align="center" justify="center">
                  <v-progress-circular indeterminate color="grey lighten-5">
                  </v-progress-circular>
                </v-row>
              </template>
              <div class="fill-height bottom-gradient"></div>
            </v-img>
          </v-avatar>
          <p class="white--text subheading mt-6 text-center overline"> {{ user.name }}</p>
        </v-flex>
      </v-layout>
      <v-list flat two-line
      v-for="l in links"
      :key="l.text"
      v-if="l.show"
      >
        <v-list-item v-if="l.text=='Log Out'"
        @click="sendLogout()"
          dark
          color="white"
          active-class="menu-border"
        >
          <v-list-item-action>
            <v-icon>{{ l.icon }}</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>{{ l.text }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>

        <v-list-item
          v-else
          dark
          :to="l.route"
          color="white"
          active-class="menu-border"
        >
          <v-list-item-action>
            <v-icon>{{ l.icon }}</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>{{ l.text }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>

      </v-list>
    <template v-slot:append>
        <v-btn block small plain raised dark color="green">
        <span class="white--text text-h7">{{ pcTime }}</span>
        </v-btn>
    </template>
    </v-navigation-drawer>
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
  JSON.parse(localStorage.getItem("sf_session")).name ? this.user.name = JSON.parse(localStorage.getItem("sf_session")).name : "Not Connected"
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
      orders: {
        icon: "mdi-order-bool-descending-variant",
        text: "My Orders",
        route: "/myorders",
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
          this.links.orders.show = SF_localStorage.nav_bar.orders;
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