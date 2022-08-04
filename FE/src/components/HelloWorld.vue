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
        <template>
          <v-row align-content="center" justify="center">
            <v-carousel class="mt-15"
            delimiter-icon="mdi-minus" 
            hide-delimiter-background
            continuous
            cycle
            interval="5000"
            show-arrows-on-hover
            style="width:66vw;height:33vh;"
            >
              <v-carousel-item
              v-for="(img,i) in carousel"
              :key="i"
              gradient="to top right, rgba(30,220,125,.05), rgba(255,255,255,.05)"
              >
              <v-parallax
              dark
              :src="img.src">
                <v-row
                align="center"
                justify="center">
                  <v-card color="rgb(0, 0, 0, 0.45)" class="white--text" width="26vw">
                    <h1 class="text-h5 font-weight-normal pa-25">
                      {{img.title}}
                    </h1>
                    <h4 class="subheading font-weight-light">
                      {{img.text}}
                    </h4>
                  </v-card>
                </v-row>
              </v-parallax>
            </v-carousel-item>
            </v-carousel>
          </v-row>
        </template>
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
        carousel: 
          [
            {
              src: 'https://images.unsplash.com/photo-1587394214354-a427608f15cc?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1470&q=80',
              title: 'Spaghetti with cheese',
              text: 'Chard cabbage cress gumbo spinach mung bean turnip greens rock melon chicory collard greens bok choy. '
            },
            {
              src: 'https://images.unsplash.com/photo-1584531482079-af3f1a796744?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1470&q=80',
              title: 'Lemon Biscuit',
              text: 'Wattle seed wakame eggplant soybean quandong garlic prairie turnip swiss chard radish okra.'

            },
            {
              src: 'https://images.unsplash.com/photo-1555243896-c709bfa0b564?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1470&q=80',
              title: 'Tzatziki Wakame Salad',
              text: 'Carrot grape soko wakame plantain pea broccoli rabe desert raisin.'
            },
            {
              src: 'https://images.unsplash.com/photo-1455853659719-4b521eebc76d?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1470&q=80',
              title: 'Seasional Fruitcake',
              text: 'Spinach mung bean turnip greens rock melon chicory seed collard greens healthy fruits.'
            }
          ]
      }),
  }
</script>
