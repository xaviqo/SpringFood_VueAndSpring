<template>
  <v-row>
    <v-col cols="8">
      <template>
        <v-card outlined tile min-height="20vh">
          <div class="d-flex flex-no-wrap justify-space-between">
            <div>
              <v-card-title class="body-1 text-weight-light">Today deliveries</v-card-title>
            </div>
            <v-spacer />
            <div>
              <v-btn depressed color="green" class="ma-2 white--text" @click="configure.dialog = true">
                <v-icon dark>
                  mdi-cog
                </v-icon>
              </v-btn>
            </div>
            <v-avatar size="50" class="mr-2">
              <v-icon color="green" large>mdi-table</v-icon>
            </v-avatar>
          </div>
          <v-divider />
          <v-data-table loading :loading="loadingOrders" loading-text="Loading today orders..."
            no-data-text="Empty orders" :headers="headers" :items="orders" :items-per-page="9" height="50vh">
            <template v-slot:item.totalAmount="{ item }">
              {{ centsToEuro(item.totalAmount) }}€
            </template>
            <template v-slot:item.paid="{ item }">
              <v-chip :color="getColor(item.paid)" dark>
                <v-icon small v-if="item.paid">mdi-check-bold</v-icon>
                <v-icon small v-else>mdi-close-thick</v-icon>
              </v-chip>
            </template>
            <template v-slot:item.delivered="{ item }">
              <v-chip :color="getColor(item.delivered)" class="ml-2" dark>
                <v-icon small v-if="item.delivered">mdi-check-bold</v-icon>
                <v-icon small v-else>mdi-close-thick</v-icon>
              </v-chip>
            </template>
            <template v-slot:item.actions="{ item }">
              <v-icon class="ml-2" @click="expandItem(item.orderId)">
                mdi-magnify
              </v-icon>
            </template>
          </v-data-table>
        </v-card>
      </template>
    </v-col>
    <dashboard-simple-table :expanded-order="orderLine" />
    <template>
      <v-row justify="center">
        <v-dialog v-model="configure.dialog" persistent max-width="50vw">
          <v-card>
            <v-toolbar color="primary" dark elevation="0">
              <span class="font-weight-regular text-h5">Configure span of time</span>
            </v-toolbar>
            <v-card-text>
              <v-container>
                <template>
                  <div>
                    <v-row justify="space-around" align="center" class="mt-6">
                      <v-col style="width: 320px; flex: 0 1 auto;" cols="6">
                        <h2>Opening:</h2>
                        <v-sheet color="white" height="50" width="250" class="text-h5 font-weight-light mt-5">
                          <div justify="space-around" align="center">
                            <v-btn elevation="0" icon small>
                              <v-icon dark @click="plusMinusDate(configure.from, false)">
                                mdi-minus-thick
                              </v-icon>
                            </v-btn>
                            {{ configure.from.date.d }}/{{ configure.from.date.m }}/{{ configure.from.date.y }}
                            <v-btn elevation="0" icon small>
                              <v-icon dark @click="plusMinusDate(configure.from, true)">
                                mdi-plus-thick
                              </v-icon>
                            </v-btn>
                          </div>
                        </v-sheet>
                        <v-time-picker ref="fromPicker" scrollable header-color="white" color="green" format="24hr"
                          v-model="configure.from.localTime" @click:hour="setHourMinutes(configure.from)"
                          class="compact-clock mt-n10 text-black custom-time-picker"></v-time-picker>
                      </v-col>
                      <v-col style="width: 320px; flex: 0 1 auto;" cols="6">
                        <h2>Closing:</h2>
                        <v-sheet color="white" height="50" width="250" class="text-h5 font-weight-light mt-5">
                          <div justify="space-around" align="center">
                            <v-btn elevation="0" icon small>
                              <v-icon dark @click="plusMinusDate(configure.to, false)">
                                mdi-minus-thick
                              </v-icon>
                            </v-btn>
                            {{ configure.to.date.d }}/{{ configure.to.date.m }}/{{ configure.to.date.y }}
                            <v-btn elevation="0" icon small>
                              <v-icon dark @click="plusMinusDate(configure.to, true)">
                                mdi-plus-thick
                              </v-icon>
                            </v-btn>
                          </div>
                        </v-sheet>
                        <v-time-picker ref="toPicker" scrollable header-color="white" color="green" format="24hr"
                          v-model="configure.to.localTime" @click:hour="setHourMinutes(configure.to)"
                          class="compact-clock mt-n10 custom-time-picker"></v-time-picker>
                      </v-col>
                    </v-row>
                  </div>
                </template>
              </v-container>
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="green" text @click="saveSpanOfTime(false)">
                Close
              </v-btn>
              <v-btn color="green" text @click="saveSpanOfTime(true)">
                Save
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-row>
    </template>
  </v-row>
</template>
<script>
import DashboardSimpleTable from "@/components/DashboardSimpleTable.vue"
import { EventBus } from '@/main.js';
import { mixins } from '@/mixins.js';
export default {
  mixins: [mixins],
  components: {
    DashboardSimpleTable
  },
  data() {
    return {
      headers: [
        { text: 'Order', value: 'orderId' },
        { text: 'Time', value: 'timestamp' },
        { text: 'Client', value: 'clientName' },
        { text: 'Phone', value: 'clientPhone', sortable: false },
        { text: 'Address', value: 'clientAddress', sortable: false },
        { text: 'Total', value: 'totalAmount' },
        { text: 'Paid', value: 'paid' },
        { text: 'Delivered', value: 'delivered' },
        { text: 'Expand', value: 'actions', sortable: false },
      ],
      orders: [],
      loadingOrders: true,
      orderLine: [],
      SF_localStorage: {},
      configure: {
        from: {
          id : 'from',
          date : {
            d: null,
            m: null,
            y: null
          },
          time : {
            h: null,
            m: null
          },
          localDateTime: null,
          localTime: null
        },
        to: {
          id: 'to',
          date : {
            d: null,
            m: null,
            y: null
          },
          time : {
            h: null,
            m: null
          },
          localDateTime: null,
          localTime: null
        },
        hourSpan: 0,
        dialog: false
      }
    }
  },
  created: async function () {
    this.getTodayOrders();
    this.configureTimeSpan();
  },
  methods: {
    async configureTimeSpan() {
      this.axios
      .get(`/api/admin/cfg/getDbOpenClose`)
        .then((res) => {
          this.configure.from.localDateTime = new Date(res.data.dayFrom);
          this.configure.from.localTime = res.data.dayFrom.split("T")[1];
          this.configure.to.localDateTime = new Date(res.data.dayTo);
          this.configure.to.localTime = res.data.dayTo.split("T")[1];

          this.setDayMonthYear(this.configure.from);
          this.setDayMonthYear(this.configure.to);
          this.setHourMinutes(this.configure.from);
          this.setHourMinutes(this.configure.to);
        })
        .catch((e) => {
          this.showAlert({
            color: "red",
            show: true,
            type: "error",
            msg: "Error loading times"
          });
        });
    },
    saveSpanOfTime(save){

      this.configure.dialog = !this.configure.dialog;

      if (save){

        let spanOfTime = this.calcDiffDays(this.configure.from, this.configure.to);

        if (spanOfTime > 1) {

          let time = this.configure.from.localDateTime;

          let newCfg = {
            day: time.getDate(),
            month: time.getMonth(),
            year: time.getFullYear(),
            hour: time.getHours(),
            minutes: time.getMinutes(),
            spanOfTime: spanOfTime
          };

          this.axios
            .post(`/api/admin/cfg/setDbOpenClose`, newCfg)
            .then((res) => {
              this.configureTimeSpan();
              this.getTodayOrders();
              this.showAlert({
                color: "green",
                show: true,
                type: "success",
                msg: "Time successfully changed"
              });
            })
            .catch((e) => {
              console.log(e);
              this.showAlert({
                color: "red",
                show: true,
                type: "error",
                msg: "Error saving time configuration"
              });
              this.configureTimeSpan();
            });

        } else {
          this.showAlert({
            color: "red",
            show: true,
            type: "error",
            msg: "One hour difference is required"
          });
        }

      }

    },
    setDayMonthYear(date){
      date.date.d = date.localDateTime.getDate();
      date.date.m = date.localDateTime.getMonth();
      date.date.y = date.localDateTime.getFullYear();
    },
    setHourMinutes(date){

      this.onlyEnableHour(true,date);

      date.time.h = date.localTime.split(":")[0];
      date.time.m = date.localTime.split(":")[1];
      date.localDateTime.setHours(date.time.h,date.time.m,0,0);
    },
    onlyEnableHour(bool,date){
      if (date.id == 'from'){
        this.$nextTick(() => {
          this.$refs.fromPicker.selectingHour = bool;
        });
      } else if (date.id == 'to') {
        this.$nextTick(() => {
          this.$refs.toPicker.selectingHour = bool;
        });
      }
    },
    plusMinusDate(date,plus){

      if (plus) {
        date.localDateTime.setDate(date.localDateTime.getDate()+1);
      } else {
        date.localDateTime.setDate(date.localDateTime.getDate()-1);
      }

      this.setDayMonthYear(date);
      this.setHourMinutes(date);

    },
    isAfter(from, to) {
      return Date.parse(from.localDateTime) < Date.parse(to.localDateTime);
    },
    calcDiffDays(from, to) {
      if (this.isAfter(from,to)) return Math.abs(to.localDateTime - from.localDateTime) / 36e5;
      else return 0;
    },
    async getTodayOrders() {
      this.axios
        .get(`/api/admin/order/getOrdersByDate`)
        .then((res) => {
          this.orders = res.data;
          this.loadingOrders = false;
        })
        .catch((e) => {
          this.showAlert({
            color: "red",
            show: true,
            type: "error",
            msg: "Error loading orders"
          });
          this.loadingOrders = false;
        });
    },
    async expandItem(orderId) {

      this.axios
        .get(`/api/admin/order/getLinesFromOrder/${orderId}`)
        .then((res) => {
          const linesToSend = res.data;
          let count = 1;
          for (const line in linesToSend) {
            linesToSend[line].lineId = "L" + linesToSend[line].lineId;
            linesToSend[line].productId = "P" + linesToSend[line].productId;
            linesToSend[line].lineCount = count;
            linesToSend[line].totalLine = this.centsToEuro(linesToSend[line].totalLine) + "€";
            count++;
          }
          this.orderLine = linesToSend;

        })
        .catch((e) => {
          this.showAlert({
            color: "red",
            show: true,
            type: "error",
            msg: "Error expanding order"
          });
        });
    },
    showAlert(model) {
      EventBus.$emit('updateAlert',
        model
      );
    },
  }

}
</script>
<style>
.v-data-footer__select {
  visibility: hidden
}

.compact-clock {
  transform: scale(0.85);
  transform-origin: left;
}

.v-time-picker-title__time {
  color: grey !important;
}

.v-time-picker-title__time {
  margin-right: 42px;
}

.custom-time-picker .v-time-picker-title {
  pointer-events: none;
}
</style>