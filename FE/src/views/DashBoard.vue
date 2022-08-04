<template>
  <v-container>
    <v-row>
      <v-col v-for="(card, n) in cards" :key="n">
        <v-card class="pa-2" outlined tile> Column {{card}} </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>
<script>
export default {
  data: () => ({
    cards: [
      {
        title: "delivered",
        percent: 0,
        active: true

      },
        {
        title: "earnings",
        today: 0,
        week: 0,
        month: 0,
        active: true

      },
      {
        title: "remaining",
        pending: 0,
        delivered: 0,
        active: true
      },
      {
        title: "products",
            lowStock: {
                id: 0,
                stock: 0
            },
            topSelling: {
                id: 0,
                sold: 0
            },
        active: true
      }
    ]
  }),
  created: async function () {
    console.log(this.currentTime());
  },
  methods: {
  },
};
</script>