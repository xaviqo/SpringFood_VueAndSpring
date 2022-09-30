<template>
  <v-row>
    <template v-for="card in cards">
      <v-col v-if="card.active" :key="card.icon">
        <v-card outlined tile min-height="20vh">
          <div class="d-flex flex-no-wrap justify-space-between">
            <div>
              <v-card-title class="body-1 text-weight-light pt-4" v-text="card.title" />
            </div>
            <v-spacer></v-spacer>
            <v-divider vertical></v-divider>
            <div>
              <v-switch class="compact-switch ml-6" v-model="card.switch" dense color="green" @click="switchCard(card)"
                flat inset></v-switch>
            </div>
            <v-divider vertical></v-divider>
            <div>
              <v-avatar size="50" class="pt-2 mr-4 ml-3">
                <v-icon color="green" large>{{card.icon}}</v-icon>
              </v-avatar>
            </div>
          </div>
          <v-divider />
          <!-- ### DELIVERED ### -->
          <div v-if="card.id == 'delivered'">
            <v-row>
              <v-col align="center" justify="center">
                <v-progress-circular :rotate="360" :size="80" :width="12" :value="card.percent" color="green"
                  class="mt-6">
                  {{card.percent}}%
                </v-progress-circular>
              </v-col>
              <v-col>
                <v-simple-table class="mt-4 mr-4">
                  <thead>
                    <tr>
                      <th class="text-center">
                        <v-sheet color="green" dark>
                          Delivered
                        </v-sheet>
                      </th>
                      <th class="text-center">
                        <v-sheet color="green" dark>
                          Remaining
                        </v-sheet>
                      </th>
                      <th class="text-center cardLastOrder">
                        <v-sheet color="green" dark>
                          Total
                        </v-sheet>
                      </th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <td class="text-center">{{ card.delivered }}</td>
                      <td class="text-center">{{ card.remain }}</td>
                      <td class="text-center">{{ card.total }}</td>
                    </tr>
                  </tbody>
                </v-simple-table>
              </v-col>
            </v-row>
          </div>
          <!-- ### LAST_ORDER ### -->
          <div v-else-if="card.id == 'lastOrder'" class="ma-4">
            <v-simple-table>
              <thead>
                <tr>
                  <th class="text-center">
                    <v-sheet color="green" dark>
                      Name
                    </v-sheet>
                  </th>
                  <th class="text-center">
                    <v-sheet color="green" dark>
                      Phone
                    </v-sheet>
                  </th>
                  <th class="text-center">
                    <v-sheet color="green" dark>
                      Staff
                    </v-sheet>
                  </th>
                  <th class="text-center">
                    <v-sheet color="green" dark>
                      Time
                    </v-sheet>
                  </th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td class="text-center">{{ card.info.client }}</td>
                  <td class="text-center">{{ card.info.clientPhone }}</td>
                  <td class="text-center">{{ card.info.responsible }}</td>
                  <td class="text-center">{{ card.info.orderDate }}</td>
                </tr>
              </tbody>
            </v-simple-table>
          </div>
          <!-- ### EARNINGS ### -->
          <div v-else-if="card.id == 'earnings'" class="mt-4 subtitle-1">
            <v-sparkline auto-draw class="px-3" :labels="card.labels" :value="card.values"
              :gradient="['#78c2ad', '#78c2ad', '#fff']" :line-width="card.lw" :label-size="card.ls"
              :type="card.sparkline"></v-sparkline>
          </div>
          <!-- ### PRODUCT DEMAND ### -->
          <div v-else-if="card.id == 'products'">
            <v-card class="elevation-0">
              <div class="d-flex flex-no-wrap justify-space-between">
                <div width="100px">
                  <v-card-title class="text-h5 text-truncate" style="width:230px">{{card.product.name}}</v-card-title>
                  <div class="mt-n5">
                    <v-simple-table>
                      <thead>
                        <tr>
                          <th class="text-center">
                            <v-sheet color="green" dark>
                              Stock
                            </v-sheet>
                          </th>
                          <th class="text-center">
                            <v-sheet color="green" dark>
                              Earned
                            </v-sheet>
                          </th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr>
                          <td class="text-center">{{ card.product.stock }}</td>
                          <td class="text-center">{{ centsToEuro(card.product.sumTotal) }}</td>
                        </tr>
                      </tbody>
                    </v-simple-table>
                  </div>
                </div>
                <v-avatar class="ma-4" size="100" tile>
                  <cld-image v-bind:public-id="card.product.cloud">
                    <cld-transformation crop="fill" width="100" height="100" gravity="center" />
                    <cld-transformation fetch-format="auto" />
                  </cld-image>
                </v-avatar>
              </div>
            </v-card>
          </div>
        </v-card>
      </v-col>
    </template>
  </v-row>
</template>
<script>
import { EventBus } from '@/main.js';
import { mixins } from '@/mixins.js';
export default {
  mixins: [mixins],
  data: () => ({
    cards: {
      delivered: {
        id: "delivered",
        title: "Delivered today",
        icon: "mdi-truck-delivery",
        remain: 0,
        delivered: 0,
        total: 0,
        percent: 0,
        active: true,
        chgOption: 'Delviered today and yesterday',
        switch: true
      },
      lastOrder: {
        id: "lastOrder",
        title: "Last order",
        chgOption: 'Last delivered order',
        icon: "mdi-account-clock",
        info: {
          client: "",
          clientPĥone: "",
          responsible: "",
          orderDate: null
        },
        active: true,
        switch: true
      },
      earnings: {
        id: "earnings",
        title: "Weekly earnings bars",
        chgOption: "Weekly earnings line",
        icon: "mdi-cash-multiple",
        labels: [],
        values: [],
        active: true,
        switch: true,
        sparkline: "bar",
        lw: 10,
        ls: 8
      },
      products: {
        id: "products",
        title: "Highest demand",
        chgOption: "Lowest demand",
        icon: "mdi-food-fork-drink",
        product: {
          name: "",
          sumTotal: 0,
          countId: 0,
          stock: 0,
          cloud: ""
        },
        active: true,
        switch: true
      },
    },
  }),
  created: function () {
    this.deliveredPercentCard(true);
    this.lastReadyOrder(true);
    this.earningsBars(true);
    this.productDemand(true);
  },
  methods: {
    switchCard(card) {

      let auxOption = "";

      auxOption = card.title;
      card.title = card.chgOption;
      card.chgOption = auxOption;

      switch (card.id) {
        case 'earnings':
          if (card.switch) {
            card.sparkline = "bar";
            card.lw = 10;
          } else {
            card.lw = 2;
            card.sparkline = "trend";
          }
          this.earningsBars();
          break;
        case 'delivered':
          this.deliveredPercentCard(card.switch);
          break;
        case 'lastOrder':
          this.lastReadyOrder(card.switch);
          break;
        case 'products':
          this.productDemand(card.switch);
          break;
        default:
          this.showAlert({
            color: "red",
            show: true,
            type: "error",
            msg: "Error, invalid card ID"
          });
          break;
      }
    },
    productDemand(higher) {

      this.axios
        .get(`/api/admin/card/getDemandProduct/${higher}`)
        .then((res) => {
          this.cards.products.product = res.data;
        })
        .catch((e) => {
          this.showAlert({
            color: "red",
            show: true,
            type: "error",
            msg: "Error loading demand card"
          });
        });

    },
    earningsBars() {

      this.cards.earnings.labels.length = 0;
      this.cards.earnings.values.length = 0;

      this.axios
        .get(`/api/admin/card/getEarningBars/`)
        .then((res) => {
          const totals = res.data;

          for (const dow in totals) {

            this.cards.earnings.labels.push(dow.substring(0, 3));
            if (totals[dow] == 0) totals[dow] = 300
            this.cards.earnings.values.push(totals[dow]);

          }
        })
        .catch((e) => {
          this.showAlert({
            color: "red",
            show: true,
            type: "error",
            msg: "Error loading sparklines card"
          });
        });
    },
    lastReadyOrder(delivered) {

      this.axios
        .get(`/api/admin/card/getLastReadyOrder/${delivered}`)
        .then((res) => {
          this.cards.lastOrder.info = res.data;
          this.cards.lastOrder.info.orderDate = res.data.orderDate.substr(11, 5);
        })
        .catch((e) => {
          this.showAlert({
            color: "red",
            show: true,
            type: "error",
            msg: "Error loading last order card"
          });
        });
    },
    deliveredPercentCard(today) {

      this.axios
        .get(`/api/admin/card/getDeliveredPercent/${today}`)
        .then((res) => {
          this.cards.delivered.remain = res.data.remain;
          this.cards.delivered.delivered = res.data.delivered;

          this.cards.delivered.total = this.cards.delivered.remain + this.cards.delivered.delivered;
          this.cards.delivered.percent = 100 - (Math.round((this.cards.delivered.remain / this.cards.delivered.total) * 100));
        })
        .catch((e) => {
          this.showAlert({
            color: "red",
            show: true,
            type: "error",
            msg: "Error loading percent card"
          });
        });
    },
    showAlert(model) {
      EventBus.$emit('updateAlert',
        model
      );
    },
  },
};
</script>
<style>
.compact-switch {
  transform: scale(0.85);
  transform-origin: left;
}
</style>