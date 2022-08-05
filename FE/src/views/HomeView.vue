<template>
<v-row>
  <hello-world />
  <home-slider></home-slider>
</v-row>
</template>

<script>
import HomeSlider from '@/components/HomeSlider.vue'
import HelloWorld from '@/components/HelloWorld'
  export default {
    name: 'HomeView',
    components: {
      HelloWorld,
      HomeSlider,
    },
  }
</script>
