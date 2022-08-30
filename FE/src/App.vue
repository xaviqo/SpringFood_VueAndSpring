<template>
  <v-app>
    <NavBar ref="navBarRef" />
    <v-content class="ma-4">
      <router-view/>
    </v-content>
    <FooterSpring/>
  </v-app>
</template>
<style>
  ::-webkit-scrollbar {
    width: 6px;
    height: 6px;
  }
  ::-webkit-scrollbar-track {
    border-radius: 10px;
    background: rgba(0,0,0,0.1);
  }
  ::-webkit-scrollbar-thumb{
    border-radius: 10px;
    background: rgba(0,0,0,0.2);
  }
  ::-webkit-scrollbar-thumb:hover{
  	background: rgba(0,0,0,0.4);
  }
  ::-webkit-scrollbar-thumb:active{
  	background: rgba(0,0,0,.9);
  }
</style>
<script>
import LogIn from '@/views/LogIn.vue';
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