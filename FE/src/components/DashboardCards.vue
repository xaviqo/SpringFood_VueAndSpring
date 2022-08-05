<template>
  <v-row>
    <template v-for="card in cards">
      <v-col v-if="(card.active) && (card.id)" :key="card.id">
        <v-card class="pa-2" outlined tile min-height="19vh">
          <div class="d-flex flex-no-wrap justify-space-between">
            <div>
              <v-card-title class="body-1 text-weight-light" v-text="card.title"/>
            </div>
              <v-avatar size="50">
                <v-icon color="green" large>{{card.icon}}</v-icon>
              </v-avatar>
          </div>
          <v-divider/>
          <!-- ### DELIVERED ### -->
          <div v-if="card.id === 'delivered'">
            <v-row>
              <v-col align="center"
              justify="center">
              <v-progress-circular
                :rotate="360"
                :size="80"
                :width="12"
                :value="card.percent"
                color="green"
                class="mt-5">
                {{card.percent}}%
              </v-progress-circular>
              </v-col>
              <v-col>
                <div class="mt-5 subtitle-1">
                  <span v-if="card.remain>0">Remaining: {{card.remain}}</span>
                  <span v-else >No remaining orders </span>
                </div>
                <div> </div>
                <div class="mt-5 subtitle-1">
                  <span>Total orders: {{card.total}}</span>
                </div>
              </v-col>
            </v-row>
          </div>
          <!-- ### EARNINGS ### -->
          <div v-else-if="card.id === 'earnings'" class="mt-4 subtitle-1">
          <v-row>
            <v-col cols="6" class="text-center">            
              <span v-if="card.today>0" class="pa-4"><strong>Today: </strong>{{card.today}}€</span>
            </v-col>
            <v-col cols="6" class="text-center">
              <span v-if="card.week>0" class="pa-4"><strong>Week: </strong>{{card.week}}€</span>
            </v-col>
            <v-divider v-if="card.orders>0"/>
            <v-col cols="12" class="text-center">
              <span v-if="(card.orders>0)" class="pa-4">{{card.month}}€ this month from 
              a total of {{card.orders}} orders.</span>
            </v-col>
          </v-row>
          </div>
          <!-- ### LAST_ORDER ### -->
          <div v-else-if="card.id === 'lastorder'" class="mt-4 subtitle-1">
            <v-row>
              <v-col cols="6">
                <div
                align="center"
                justify="center">
                  <v-btn
                  class="ma-3 body-1"
                  color="green"
                  elevation="0"
                  >
                    {{card.client.name}}
                  </v-btn>
                  <v-btn
                  class="ma-3 body-2"
                  color="green"
                  elevation="0"
                  x-small
                  >
                    <v-icon
                    dark
                    left
                    class="mr-1"
                    x-small
                    >
                    mdi-cellphone-text
                    </v-icon>
                    {{card.client.phone}}
                  </v-btn>
                </div>
              </v-col>
              <v-col cols="6">
                <div
                align="left"
                  justify="center">
                  <v-btn
                  class="ma-1 body-2"
                  color="white"
                  elevation="0"
                  x-small
                  >
                    <v-icon
                    dark
                    left
                    class="mr-1">
                    mdi-calendar
                    </v-icon>
                    {{card.order.date}}
                  </v-btn>
                  <v-btn
                  class="ma-1 body-2"
                  color="white"
                  elevation="0"
                  x-small
                  >
                    <v-icon
                    dark
                    left
                    class="mr-1">
                    mdi-checkbox-marked-circle
                    </v-icon>
                    {{card.order.worker.id}} {{card.order.worker.name}}
                  </v-btn>
                  <v-btn
                  class="ma-1 body-2"
                  color="white"
                  elevation="0"
                  x-small
                  >
                    <v-icon
                    dark
                    left
                    class="mr-1">
                    mdi-cash
                    </v-icon>
                    {{card.order.total}}€
                  </v-btn>
                </div>
              </v-col>
            </v-row>
          </div>
        </v-card>
      </v-col>
    </template>
  </v-row>
</template>

<script>
export default {
  data: () => ({
    cards: [
      {
        id: "delivered",
        title: "Delivered orders",
        icon: "mdi-truck-delivery",
        remain: 1,
        total: 6,
        percent: 0,
        active: true,
      },
      {
        id: "earnings",
        title: "Total earnings",
        icon: "mdi-cash-multiple",
        today: 1,
        week: 30,
        month: 343.94,
        orders: 21,
        active: true,
      },
      {
        id: "lastorder",
        title: "Last order",
        icon: "mdi-account-clock",
        client: {
          name: 'Xavier',
          phone: '654776312',
          avatar: null
        },
        order: {
          date: '',
          worker: {
            id: 12,
            name: 'Lorem Ipsum'
          },
          total: 12.49
        },
        active: true,
      },
      {
        id: "products",
        title: "Stock status",
        icon: "mdi-food-fork-drink",
        lowStock: {
          id: 0,
          stock: 0,
        },
        topSelling: {
          id: 0,
          sold: 0,
        },
        active: true,
      },
    ],
  }),
  created: function() {
    //UPDATE DELIVERED ORDERS CARD
    this.cards.forEach(c => {
      if (c.hasOwnProperty('percent')){
        c.percent = 100-(Math.round((c.remain/c.total)*100));
      }
      if (c.hasOwnProperty('order')){
        c.order.date = this.currentTime();
      }
    });
    //CALCULATE LAST ORDER TIME
  },
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
};
</script>