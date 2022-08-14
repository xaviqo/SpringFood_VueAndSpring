<template>
  <v-app>
    <NavBar ref="navBarRef" />
    <v-content class="ma-4">
      <router-view/>
    </v-content>
    <FooterSpring/>
  </v-app>
</template>
<script>
import LogIn from '@/views/LogIn.vue'
import NavBar from '@/components/NavBar.vue';
import FooterSpring from '@/components/FooterSpring.vue';
export default {
  name: 'App',
  components : {
    NavBar,
    FooterSpring,
    LogIn
  }
}
</script>