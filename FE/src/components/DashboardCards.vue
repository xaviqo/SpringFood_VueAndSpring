<template>
  <v-row>
    <template v-for="card in cards">
      <v-col v-if="(card.active) && (card.id)" :key="card.id">
        <v-card class="pa-2" outlined tile min-height="20vh">
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
                class="mt-6">
                {{card.percent}}%
              </v-progress-circular>
              </v-col>
              <v-col>
                <v-simple-table class="mt-4">
                  
                  <thead>
                    <tr>
                      <th class="text-center">
                        <v-sheet
                          color="green"
                          dark>
                        Remaining 
                        </v-sheet>
                      </th>
                      <th class="text-center cardLastOrder">
                        <v-sheet
                          color="green"
                          dark>
                        Total 
                        </v-sheet>
                      </th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <td class="text-center">{{ card.remain }}</td>
                      <td class="text-center">{{ card.total}}</td>
                    </tr>
                  </tbody>
              </v-simple-table>
              </v-col>
            </v-row>
          </div>
          <!-- ### EARNINGS ### -->
          <div v-else-if="card.id === 'earnings'" class="mt-4 subtitle-1">
          <v-row>
              <v-sparkline
                auto-draw
                class="pa-2"
                :labels="card.labels"
                :value="card.values"
                :gradient="['#78c2ad', '#78c2ad', '#fff']"
                line-width="10"
                label-size="8"
                type="bar"
              ></v-sparkline>
          </v-row>
          </div>
          <!-- ### LAST_ORDER ### -->
          <div v-else-if="(card.id === 'lastorder') && (card.client.name)" class="ma-4">
            <v-row>
              <v-simple-table class="mt-4 ml-3">
                  <thead>
                    <tr>
                      <th class="text-center">
                        <v-sheet
                          color="green"
                          dark>
                        Name 
                        </v-sheet>
                      </th>
                      <th class="text-center">
                        <v-sheet
                          color="green"
                          dark>
                        Phone
                        </v-sheet>
                      </th>
                      <th class="text-center">
                        <v-sheet
                          color="green"
                          dark>
                        Staff
                        </v-sheet>
                      </th>
                      <th class="text-center">
                        <v-sheet
                          color="green"
                          dark>
                        Time
                        </v-sheet>
                      </th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <td>{{ card.client.name }}</td>
                      <td>{{ card.client.phone }}</td>
                      <td>{{ card.order.worker.name }}</td>
                      <td>{{ card.order.time }}</td>
                    </tr>
                  </tbody>
              </v-simple-table>
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
        title: "Weekly earnings",
        icon: "mdi-cash-multiple",
        labels : [
          'Mon',
          'Tue',
          'Wed',
          'Thu',
          'Fri',
          'Sat',
          'Sun'
        ],
        values: [
          200,
          675,
          410,
          390,
          310,
          460,
          250
      ],
        active: true,
      },
      {
        id: "lastorder",
        title: "Last ready order",
        icon: "mdi-account-clock",
        client: {
          name: 'Xavier QO',
          phone: '654776312',
          avatar: null
        },
        order: {
          time: '13:37 AM',
          worker: {
            name: 'Pedro Ipsum'
          }
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
<style>
</style>