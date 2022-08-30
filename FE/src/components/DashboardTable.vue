<template>
  <v-row>
    <v-col cols="8">
      <template>
        <v-card outlined tile min-height="20vh">
          <div class="d-flex flex-no-wrap justify-space-between">
            <div>
              <v-card-title class="body-1 text-weight-light">Today deliveries</v-card-title>
            </div>
            <v-spacer/>
            <div>
              <v-btn
                depressed
                color="green"
                class="ma-2 white--text"
                @click="configure.dialog = true"
              >
                <v-icon
                  dark
                >
                  mdi-cog
                </v-icon>
              </v-btn>
            </div>
              <v-avatar size="50" class="mr-2">
                <v-icon color="green" large>mdi-table</v-icon>
              </v-avatar>
          </div>
          <v-divider/>
          <v-data-table
            loading
            :loading="loadingOrders"
            loading-text="Loading today orders..."
            no-data-text="Empty orders"
            :headers="headers"
            :items="orders"
            :items-per-page="9"
            height="50vh"
          >
          <template v-slot:item.totalAmount="{ item }">
            {{ centsToEuro(item.totalAmount) }}€
          </template>
          <template v-slot:item.paid="{ item }">
            <v-chip
              :color="getColor(item.paid)"
              dark
            >
            <v-icon small v-if="item.paid">mdi-check-bold</v-icon>
            <v-icon small v-else>mdi-close-thick</v-icon>
            </v-chip>
          </template>
          <template v-slot:item.delivered="{ item }">
            <v-chip
              :color="getColor(item.delivered)"
              class="ml-2"
              dark
            >
            <v-icon small v-if="item.delivered">mdi-check-bold</v-icon>
            <v-icon small v-else>mdi-close-thick</v-icon>
            </v-chip>
          </template>
          <template v-slot:item.actions="{ item }">
            <v-icon
              class="ml-2"
              @click="expandItem(item.orderId)"
            >
              mdi-magnify
            </v-icon>
          </template>
          </v-data-table>
        </v-card>
      </template>
    </v-col>
      <dashboard-simple-table :expanded-order="orderLine"/>
      <template>
        <v-row justify="center">
          <v-dialog
            v-model="configure.dialog"
            persistent
            max-width="50vw"
          >
              <v-card>
              <v-toolbar
              color="primary"
              dark
              elevation="0"
              >
                <span class="font-weight-regular text-h5">Configure span of time</span>
              </v-toolbar>
              <v-card-text>
                <v-container>
                  <template>
                    <div>
                      <v-row
                        justify="space-around"
                        align="center"
                        class="mt-6"
                      >
                        <v-col style="width: 320px; flex: 0 1 auto;" cols="6">
                          <h2>Opening:</h2>
                          <v-sheet
                          color="white"
                          height="50"
                          width="250"
                          class="text-h5 font-weight-light mt-5"
                          >
                            <div 
                              justify="space-around"
                              align="center"
                            >
                              <v-btn
                                elevation="0"
                                icon
                                small
                              >
                                <v-icon dark
                                @click="plusMinusDate(configure.opening, false)"
                                >
                                  mdi-minus-thick
                                </v-icon>                    
                              </v-btn>
                              {{ configure.opening.d }}/{{ configure.opening.m }}/{{ configure.opening.y }}
                              <v-btn
                                elevation="0"
                                icon
                                small
                              >
                              <v-icon dark
                              @click="plusMinusDate(configure.opening, true)"
                              >
                                  mdi-plus-thick
                                </v-icon>    
                              </v-btn>  
                            </div>             
                          </v-sheet>
                          <v-time-picker
                            scrollable
                            header-color="white"
                            color="green"
                            format="24hr"
                            v-model="configure.opening.time.entire"
                            @change="setHmOnTime(configure.opening)"
                            class="compact-clock mt-n10 text-black"
                          ></v-time-picker>
                        </v-col>
                        <v-col style="width: 320px; flex: 0 1 auto;" cols="6">
                          <h2>Closing:</h2>
                          <v-sheet
                          color="white"
                          height="50"
                          width="250"
                          class="text-h5 font-weight-light mt-5"
                          >
                            <div 
                              justify="space-around"
                              align="center"
                            >
                              <v-btn
                                elevation="0"
                                icon
                                small
                              >
                                <v-icon dark
                                @click="plusMinusDate(configure.closing, false)"
                                >
                                  mdi-minus-thick
                                </v-icon>                    
                              </v-btn>
                              {{ configure.closing.d }}/{{ configure.closing.m }}/{{ configure.closing.y }}
                              <v-btn
                                elevation="0"
                                icon
                                small
                              >
                              <v-icon dark
                              @click="plusMinusDate(configure.closing, true)"
                              >
                                  mdi-plus-thick
                                </v-icon>    
                              </v-btn>  
                            </div>             
                          </v-sheet>
                          <v-time-picker
                            scrollable
                            header-color="white"
                            color="green"
                            format="24hr"
                            v-model="configure.closing.time.entire"
                            @change="setHmOnTime(configure.closing)"
                            class="compact-clock mt-n10"
                          ></v-time-picker>
                        </v-col>
                      </v-row>
                    </div>
                  </template>
                </v-container>
              </v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn
                  color="green"
                  text
                  @click="spanOfTimeSaveOrClose(false)"
                >
                  Close
                </v-btn>
                <v-btn
                  color="green"
                  text
                  @click="spanOfTimeSaveOrClose(true)"
                >
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
import { mixins } from '@/mixins.js';
  export default {
    mixins: [ mixins ],
    components: {
      DashboardSimpleTable
    },
    data () {
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
        orderLine : [],
        SF_localStorage: {},
        configure: {
          opening: {
            entire: null,
            d: null,
            m: null,
            y: null,
            time : {
              entire: null,
              h: null,
              m: null
            },
          },
          closing: {
            entire: null,
            d: null,
            m: null,
            y: null,
            time: {
              entire: null,
              h: null,
              m: null
            },
          },
          daysDiff: 0,
          dialog: false
        }
      }
    },
    created: async function() {
      this.getTodayOrders();
      this.configureTimes();
    },
    methods: {
      async configureTimes(){
        this.axios
        .get(`/api/admin/cfg/getDbOpenClose`)
        .then((res) => {

          this.configure.opening.time.entire = res.data.dayFrom;
          this.configure.closing.time.entire = res.data.dayTo;
          this.configure.daysDiff = res.data.daysDiff;

          const dayToArr = res.data.dayTo.split(":");
          const dayFromArr = res.data.dayFrom.split(":");

          this.configure.opening.time.h = dayFromArr[0];
          this.configure.opening.time.m = dayFromArr[1];
          this.configure.closing.time.h = dayToArr[0];
          this.configure.closing.time.m = dayToArr[1];

          this.configure.opening.entire = new Date();
          this.configure.opening.entire.setHours(this.configure.opening.time.h,this.configure.opening.time.m,0,0);
          this.configure.closing.entire = new Date();
          this.configure.closing.entire.setDate(this.configure.closing.entire.getDate() + this.configure.daysDiff);
          this.configure.closing.entire.setHours(this.configure.closing.time.h,this.configure.closing.time.m,0,0);

          this.setDmyOnDate(this.configure.opening);
          this.setDmyOnDate(this.configure.closing);
        })
        .catch((e) => {
          console.log("Error loading dashboard times cfg");
          console.log(e);
        });
      },
      async spanOfTimeSaveOrClose(save){
        this.configure.dialog = !this.configure.dialog;
        if (save){

          if (this.isAfter(this.configure.closing.entire,this.configure.opening.entire)){

            let newCfg = {
              daysDiff: null,
              dayTo: null,
              dayFrom: null
            };

            newCfg.daysDiff = this.calcDiffDays(this.configure.closing,this.configure.opening);
            newCfg.dayTo = this.configure.opening.time.entire;
            newCfg.dayFrom = this.configure.closing.time.entire;

            this.axios
              .post(`/api/admin/cfg/setDbOpenClose`,newCfg)
              .then((res) => {
                console.log(res);
                this.configureTimes();
                this.getTodayOrders();
              })
              .catch((e) => {
                console.log("Error saving dashboard times cfg");
                console.log(e);
                this.configureTimes();
              });
            
          } else {
            console.log("Invalid values for dashboard times cfg");
            this.configureTimes();
          }

        } else {
          this.configureTimes();
        }
      },
      isAfter(a,b){
        return Date.parse(a) > Date.parse(b);
      },
      calcDiffDays(close, open){

        let diffMillis = Math.abs(close.entire - open.entire) / 1000;
        const diffDays = Math.floor(diffMillis / 86400);
        return diffDays;
      },
      plusMinusDate(date, plus){

        this.setHmOnTime(date);

        if (plus){
          date.entire.setDate(date.entire.getDate()+1);
        } else {
          date.entire.setDate(date.entire.getDate()-1);
        }

        this.setDmyOnDate(date);

      },
      setDmyOnDate(date){

      date.d = date.entire.getDate();
      date.m = date.entire.getMonth();
      date.y = date.entire.getFullYear();

      },
      setHmOnTime(date){

      const hmToSplit = date.time.entire;
      date.time.h = hmToSplit.split(":")[0];
      date.time.m = hmToSplit.split(":")[1];
      date.entire.setHours(date.time.h,date.time.m,0,0);

      },
      async getTodayOrders(){
      this.axios
        .get(`/api/admin/order/getTodayOrders`)
        .then((res) => {
          this.orders = res.data;
          this.loadingOrders = false;
        })
        .catch((e) => {
          console.log("Error loading orders");
          console.log(e);
          this.loadingOrders = false;
        });
      },
      async expandItem(orderId){

        this.axios
        .get(`/api/admin/order/getLinesFromOrder/${orderId}`)
        .then((res) => {
          const linesToSend = res.data;
          let count = 1;
          for (const line in linesToSend){
            linesToSend[line].lineId = "L"+linesToSend[line].lineId;
            linesToSend[line].productId = "P"+linesToSend[line].productId;
            linesToSend[line].lineCount = count;
            linesToSend[line].totalLine = this.centsToEuro(linesToSend[line].totalLine)+"€";
            count++;
          }
          this.orderLine = linesToSend;

        })
        .catch((e) => {
          console.log("Error loading orderLines");
          console.log(e);
        });
      },
       getColor (bool) {
        if (bool) return 'green'
        else return 'red'
      }
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
</style>