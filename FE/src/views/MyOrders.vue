<template>
  <v-row
  justify="space-around"
  align="center"
  >
    <v-col cols="6">
      <div v-for="order in orders" :key="order.id" class="mt-5">
        <v-app-bar
          class="elevation-0"
          color="green"
          dense
          dark
        >
          <v-icon>mdi-pin</v-icon>

          <v-toolbar-title class="ml-3">ID {{order.orderId}}</v-toolbar-title>

          <v-spacer></v-spacer>

          <div class="text-button ml-6">
            Paid 
            <v-icon v-if="order.paid" class="mt-n1">mdi-check</v-icon>
            <v-icon v-else class="mt-n1">mdi-cancel</v-icon>
          </div>
          <div class="text-button ml-6">
            Delivered 
            <v-icon v-if="order.delivered" class="mt-n1">mdi-check</v-icon>
            <v-icon v-else class="mt-n1">mdi-cancel</v-icon>
          </div>
          <div class="text-button ml-6">
            Total 
            <v-btn tile outlined small class="text-body-1 mt-n1 ml-1">{{order.totalAmount}}€</v-btn>
          </div>
          <div class="text-button ml-6">
            Lines 
            <v-btn tile outlined small class="text-body-1 mt-n1 ml-1">{{order.orderLines.length}}</v-btn>
          </div>
        </v-app-bar>
        <v-card elevation="0">
          <v-simple-table
            fixed-header
            height="140px"
          >
            <thead>
              <tr>
                <th class="text-left">
                  Product
                </th>
                <th class="text-left">
                  Price
                </th>
                <th class="text-left">
                  Quantity
                </th>
                <th class="text-left">
                  Total Line
                </th>
              </tr>
            </thead>
            <tbody>
              <tr
                v-for="product in order.orderLines"
                :key="product.product"
              >
                <td>{{ product.product }}</td>
                <td>{{ product.price }}</td>
                <td>{{ product.quantity }}</td>
                <td>{{ product.totalLine }}</td>
              </tr>
            </tbody>
          </v-simple-table>
          <v-divider></v-divider>
          <v-card-text>
            <v-row>
              <v-col cols="6">
                <span>
                  <v-icon class="mt-n1 mr-2">mdi-map-marker-outline</v-icon>
                  <strong class="mr-1">Delivery Address: </strong>{{order.deliveryAddress}}
                </span>
              </v-col>
              <v-col cols="6">
                <span>
                  <v-icon class="mt-n1 mr-2">mdi-clock-time-three-outline</v-icon>
                  <strong class="mr-1">Delivery Time: </strong> {{order.deliveryTime}}
                </span>
              </v-col>
            </v-row>
          </v-card-text>
        </v-card>
      </div>
    </v-col>
  </v-row>
</template>
<script>
  import { mixins } from '@/mixins.js';
    export default {
      mixins: [ mixins ],
      data () {
        return {
          orders: [{}]
        }
      },
      created: async function() {
        this.getClientOrders();
      },
      methods: {
        getClientOrders(){

          this.axios
            .get(`/api/client/order/getOrders`)
            .then((res) => {
              this.orders = res.data;
              console.log(this.orders);
            })
            .catch((e) => {
              console.log(e);
              console.log("Error loading client orders");
            });

        }
      }
    }
  </script>
<style>
#custom-disabled.v-btn--disabled {
  background-color: #78c2ad !important;
}
</style>