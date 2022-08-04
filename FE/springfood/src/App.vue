<template>
  <v-app>
    <NavBar/>
    <v-content class="ma-4">
      <router-view/>
    </v-content>
    <FooterSpring/>
  </v-app>
</template>
<script>
import NavBar from '@/components/NavBar.vue';
import FooterSpring from '@/components/FooterSpring.vue';
export default {
  name: 'App',
  components : {
    NavBar,
    FooterSpring
}
}
</script>