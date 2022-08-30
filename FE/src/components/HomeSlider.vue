<template>
  <v-row align-content="center" justify="center">
    <v-col></v-col>
    <v-col>
    <v-carousel
      class="mt-15 "
      delimiter-icon="mdi-minus"
      hide-delimiter-background
      continuous
      cycle
      interval="5000"
      show-arrows-on-hover
      width="50%"
    >
      <v-carousel-item
        v-for="(img, i) in carousel"
        :key="i"
        gradient="to top right, rgba(30,220,125,.05), rgba(255,255,255,.05)"
      >
            <div align="center" justify="center" class="floatDivSlider">
            <v-card color="rgb(0, 0, 0, 0.45)" class="white--text pa-10"  tile>
              <h1 class="text-h5 font-weight-normal pb-3">
                {{ img.name }}
              </h1>
              <h4 class="subheading font-weight-light pb-3">
                {{ img.description }}
              </h4>
            </v-card>
        </div>
      <cld-image v-bind:public-id="img.cloudId" >
        <cld-transformation crop="fill" width="900" height="200"/>
        <cld-transformation fetch-format="auto" />
      </cld-image>
      </v-carousel-item>
    </v-carousel>
    </v-col>
    <v-col></v-col>
  </v-row>
</template>
<style>
    .floatDivSlider {
       float: left;
       left:50%;
       z-index: 1000;
       padding: 5px;
       color: #FFFFFF;
       font-weight: bold;
    }
</style>
<script>
export default {
    data: () => ({
        carousel: []
      }),
      created () {
        this.axios
            .get(`/api/menu/product/getAll`)
            .then((res) => {
                this.carousel = res.data;
            })
            .catch(() => {
                console.log("Error retrieving slider products");
            });
      }
}
</script>