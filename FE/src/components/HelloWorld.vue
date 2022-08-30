<template> 
  <v-container >
    <v-row class="text-center">
      <v-col cols="12">

      </v-col>

      <v-col class="mb-4">
        <h1 class="display-2 font-weight-light my-3">
          <span v-if="SF_localStorage != null">
            Hi {{hiName}}! Welcome to SpringFood
          </span>
          <span v-else>Welcome to SpringFood</span>
        </h1>

        <p class="subheading font-weight-light">
          Delivery dashboard developed with SpringBoot and Vue.
          <br>Explore my other projects in my
          <a
            href="https://community.vuetifyjs.com"
            target="_blank"
            class="gitLink"
          >GitHub</a>
        </p>
        
      </v-col>
    </v-row>
  </v-container>
</template>
<style scope>
  div .v-application--wrap {
    background-image: url('../assets/bg.jpg');
    background-position: center;
  }
  .gitLink {
    color: black !important;
    background-color: transparent;
    text-decoration: none;
    font-weight: 400;
  }
  a:hover {
    font-weight: 500;
  }
</style>
<script>
  export default {
    name: 'HelloWorld',
    data: () => ({
      SF_localStorage: null,
      hiName: ''
    }),
    created() {
      this.connectedReload()
    },
    methods: {
      connectedReload() {
        if (localStorage.getItem('sf_session') != null) {
          this.SF_localStorage = JSON.parse(localStorage.getItem("sf_session"));
          this.hiName = this.SF_localStorage.name;
        }
      }
    }
  }
</script>
