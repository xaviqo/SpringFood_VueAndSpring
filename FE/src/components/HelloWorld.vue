<template> 
  <v-container >
    <v-row class="text-center">
      <v-col cols="12">

      </v-col>

      <v-col class="mb-4">
        <h1 class="display-2 font-weight-light my-3">
          Welcome to SpringFood
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
  }
</script>
