<template>
  <v-row>
    <v-col cols="8">
      <template>
        <v-card outlined tile min-height="20vh">
          <div class="d-flex flex-no-wrap justify-space-between">
            <div>
              <v-card-title class="body-1 text-weight-light">Today deliveries</v-card-title>
            </div>
              <v-avatar size="50">
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
              class="ml-1"
              dark
            >
            <v-icon small v-if="item.delivered">mdi-check-bold</v-icon>
            <v-icon small v-else>mdi-close-thick</v-icon>
            </v-chip>
          </template>
            <template v-slot:item.actions="{ item }">
              <v-icon
                small
                class="mr-2"
                @click="expandItem(item.orderId)"
              >
                mdi-magnify
              </v-icon>
              <v-icon
                small
                @click="expandItem(item.orderId)"
              >
                mdi-pencil-outline
              </v-icon>
            </template>
          </v-data-table>
        </v-card>
      </template>
    </v-col>
      <dashboard-simple-table :expanded-order="orderLine"/>
  </v-row>
</template>
<script>
import DashboardSimpleTable from "@/components/DashboardSimpleTable.vue"
  export default {
    components: {
      DashboardSimpleTable
    },
    data () {
      return {
        text : 'hola',
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
        orders: [
          ],
          loadingOrders: true,
          orderLine : [
          ]
      }
    },
    created: async function() {
      this.getTodayOrders();
    },
    methods: {
      async getTodayOrders(){
      this.axios
        .get(`/api/order/getTodayOrders`)
        .then((res) => {
          this.orders = res.data;
          this.loadingOrders = false;
        })
        .catch((e) => {
          console.log("Error loading orders {getTodayOrders()}");
          console.log(e);
          this.loadingOrders = false;
        });
      },
      async expandItem(orderId){
        this.axios
        .get(`/api/order/getLinesFromOrder/${orderId}`)
        .then((res) => {
          this.orderLine = res.data;
        })
        .catch((e) => {
          console.log("Error loading orderLine {getLinesFromOrder(String orderId)}");
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
</style>