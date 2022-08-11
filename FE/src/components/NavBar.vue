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
        <v-tab v-if="l.show" y="l.text" :to="l.route" class="mt-1">
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
          <p class="white--text subheading mt-6 text-center overline">Email</p>
        </v-flex>
      </v-layout>
      <v-list flat two-line>
        <v-list-item
          v-for="l in links"
          :key="l.text"
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
export default {
  name: "navbar",
  data: () => ({
    drawer: false,
    pcTime: "",
    links: [
      {
        icon: "mdi-home",
        text: "Home",
        route: "/",
        show: true
      },
      {
        icon: "mdi-login",
        text: "Log In",
        route: "/login",
        show: true
      },
      {
        icon: "mdi-clipboard-text",
        text: "Orders Board",
        route: "/admin",
        show: true
      },
      {
        icon: "mdi-account-group",
        text: "Team Manager",
        route: "/team",
        show: true
      },
      {
        icon: "mdi-food",
        text: "Product Manager",
        route: "/products",
        show: true

      },
      {
        icon: "mdi-test-tube",
        text: "App Test",
        route: "/generator",
        show: true
      },
    ],
  }),
  methods: {
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
    this.pcTime = this.currentTime();
    setInterval(() => {
      this.pcTime = this.currentTime();
    }, 300);
  },
};
</script>