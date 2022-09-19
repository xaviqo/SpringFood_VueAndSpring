<template>
  <v-row >
    <v-col cols="10">
      <v-row>
        <v-col v-for="p in menuProducts" :key="p.productId" v-if="p.active == true">
        <v-card
          class="mx-auto mt-5 flex d-flex flex-column elevation-0"
          color="#fdfdf6"
          tile
          max-width="260"
        > 
          <v-row>
          <v-chip
          class="ma-2 mb-n8 ml-6"
          color="green"
          dark
          max-width="50"
          >
          <v-avatar left>
          <v-icon>mdi-leaf-circle-outline</v-icon>
          </v-avatar>
            <span class="text-uppercase">{{p.type}}</span>
          </v-chip>
          </v-row>
          <cld-image v-bind:public-id="p.cloudId" >
            <cld-transformation crop="fill" width="230" height="200" gravity="center"/>
              <cld-transformation fetch-format="auto" />
          </cld-image>
                <v-toolbar color="transparent" class="mt-n7" flat>
                <v-avatar color="green" rounded class="mr-2  mb-3" dark elevation="1">
                  <div>
                    <div>
                      <span class="white--text body-2">
                        <strong>{{ centsToEuro(p.price) }}</strong>
                      </span>
                    </div>
                    <div>
                      <span class="white--text body-2">€</span>
                    </div>
                  </div>
                </v-avatar>
                <v-spacer></v-spacer>
                  <v-avatar color="green" class="mr-2 mb-3" dark>
                  <div class="three">
                    <v-btn
                    color="green"
                    fab
                    dark
                    bottom
                    @click="sendToCart(p)"
                  >
                    <v-icon>mdi-plus</v-icon>
                  </v-btn>
                  </div>
                </v-avatar>
              </v-toolbar>
              <v-card-title class="font-weight-light mt-n4">
                {{p.name}}
              </v-card-title>
              <v-divider light></v-divider>
              <v-card-text class="pDescription">
                {{p.description}}
              </v-card-text>
        </v-card>
        </v-col>
      </v-row>
    </v-col>
    <v-col cols="2">
      <template>
      <v-row>
          <v-col>
          <v-row class="flex d-flex flex-column">
          <v-card
              class="my-4 elevation-0 ml-3 "
              color="#fdfdf6"
              tile
            > 
          <v-navigation-drawer
              floating
              permanent
              dense
              width="100%"
          >   
                <template v-if="isConnected">
                  <template v-if="cartItems.length > 0">
                  <div>
                    <v-sheet color="green" width="100%" height="100%">
                      <v-list-item>
                            <v-list-item-icon>
                            <v-icon dark>mdi-cart-variant</v-icon>
                            </v-list-item-icon>
                            <v-list-item-content>
                            <v-list-item-title  class="white--text font-weight-medium">Your Order</v-list-item-title>
                            </v-list-item-content>
                      </v-list-item>
                    </v-sheet>
                  </div>
                  <v-list>                    
                      <v-list-item
                          v-for="item in cartItems"
                          :key="item.name"
                          class="my-n2"
                          >
                          <v-list-item-icon>
                            <div>
                              <v-col>
                                <v-chip outlined color="green">{{ item.quantity }}</v-chip>
                              </v-col>
                                <v-btn
                                  elevation="0"
                                  icon
                                  x-small
                                  class="ml-2"
                                  color="green"
                                  @click="updateQuantityPriceAndLineTotal(-1,item.price,findIndexOnCart(item.id))"
                                ><v-icon>mdi-minus-circle-outline</v-icon></v-btn>
                                <v-btn
                                  elevation="0"
                                  icon
                                  x-small
                                  color="green"
                                  @click="updateQuantityPriceAndLineTotal(1,item.price,findIndexOnCart(item.id))"
                                  class="ml-1"
                                ><v-icon>mdi-plus-circle-outline</v-icon></v-btn>
                            </div>
                          </v-list-item-icon>
                          <v-list-item-content class="ml-n2">
                              <div class="py-3">
                                <v-list-item-title
                                  class="text-truncate"
                                >
                                  <span
                                  >
                                  {{ item.name }}
                                  </span>
                                  <span
                                    class="text-truncate text-caption"
                                  >
                                    - {{centsToEuro(item.price)}}€
                                  </span>
                                </v-list-item-title>
                              </div>
                              <v-row>
                                <v-col>
                                  <v-chip small label color="green" outlined class="mb-n3">
                                    <span class="font-weight-medium">{{centsToEuro(item.total)}}€</span>
                                  </v-chip>
                                </v-col>
                              </v-row>
                          </v-list-item-content>
                      </v-list-item>
                      </v-list>
                    <div>
                      <v-sheet color="green" width="100%" height="100%">
                        <v-list-item class="white--text font-weight-medium">
                              <v-list-item-icon>
                                <span class="font-weight-medium">TOTAL</span>
                              </v-list-item-icon>
                              <v-list-item-content>
                              <v-list-item-title>
                                <div>
                                  <v-chip color="white" text-color="green" label>
                                    {{centsToEuro(this.orderTotal)}}€
                                  </v-chip>
                                </div>
                              </v-list-item-title>
                              </v-list-item-content>
                        </v-list-item>
                      </v-sheet>
                      <v-divider></v-divider>
                        <v-list-item class="white--text font-weight-medium ma-n2 pa-0">
                            <v-btn
                            color="green"
                            dark
                            block
                            class="mt-1"
                            @click="checkOrder()"
                            >
                            CHECK ORDER
                            <v-icon small right>mdi-arrow-right</v-icon>
                            </v-btn>
                        </v-list-item>
                    </div>
                  </template>
                  <template v-else>
                      <div>
                      <v-sheet class="pa-6 white--text text-h6 font-weight-regular" color="green">
                      Your cart is empty. Please, select a product.
                      <div class="mx-12 my-5">
                        <v-icon dark size="100">mdi-arrow-left-bottom</v-icon>
                      </div>
                      </v-sheet>
                    </div>
                  </template>
                </template>
                <template v-else>
                    <div>
                        <v-sheet class="pa-6 white--text text-h6 font-weight-regular" color="green">
                        Login or Signup to make an order!
                        </v-sheet>
                    </div>
                    <div color="green">
                          <div class="text-center my-3">
                            <v-btn 
                            rounded
                            dark
                            elevation="0"
                            color="green"  
                            @click="goLogin()"
                            >LOG IN
                            </v-btn>
                            <v-btn 
                            rounded
                            dark
                            elevation="0"
                            color="green"
                            class="ml-4"
                            @click="goLogin()"
                            >SIGN UP
                            </v-btn>
                          </div>
                    </div>
                </template>
              </v-navigation-drawer>
            </v-card>
              </v-row>
          </v-col>
      </v-row>
      </template>
    </v-col>
    <v-row justify="space-around">
      <v-col cols="auto">
        <v-dialog
          max-width="850"
          v-model="checkDialog"
        > 
        <StripePayment :cartItems="cartItems" :orderTotal="orderTotal"/>
          </v-dialog>
      </v-col>
    </v-row>
  </v-row>
</template>
<style scope>
.pDescription{
  height: 115px;
  overflow-y: hidden;
}
.v-navigation-drawer__content {
    overflow-y: hidden !important;
}
.stopOverflow{
    overflow-y: hidden !important;
}
</style>
<script>
import { mixins } from '@/mixins.js';
import { EventBus } from '@/main.js';
import StripePayment from '@/components/StripePayment.vue';
  export default {
    name: "MenuView",
    components: {
      StripePayment
    },
    mixins: [mixins],
    data: () => ({
        isConnected: false,
        menuProducts: [],
        cartItems: [],
        auxProduct: {},
        orderTotal: 0,
        checkDialog: false
    }),
    created() {
        this.isConnected = (localStorage.getItem("sf_session") != null);
        this.retrieveMenu();
        EventBus.$on("closeStripeDialog", () => {
          this.checkOrder();
        });
    },
    methods: {
        async retrieveMenu() {
            this.axios
                .get(`/api/menu/product/getAll`)
                .then((res) => {
                this.menuProducts = res.data;
                this.menuProducts = this.manageNullImages(this.menuProducts);
                console.log(this.menuProducts);
            })
                .catch(() => {
                console.log("Error retrieving products");
            });
        },
        checkOrder() {
            this.steps = 1;
            this.checkDialog = !this.checkDialog;
            this.$store.state.cartItems = this.cartItems;
            this.$store.state.orderTotal = this.orderTotal;
        },
        sendToCart(product) {
            let index;
            this.auxProduct = {};
            this.auxProduct.id = product.productId;
            this.auxProduct.name = product.name;
            this.auxProduct.quantity = 1;
            this.auxProduct.price = product.price;
            this.auxProduct.total = product.price;
            if (this.cartItems.some(p => p.id == this.auxProduct.id)) {
                //find index by id to update quantity and total
                index = this.findIndexOnCart(this.auxProduct.id);
                this.updateQuantityPriceAndLineTotal(1, product.price, index);
            }
            else {
                this.cartItems.push(this.auxProduct);
                this.updateGlobalTotal(true, product.price);
            }
        },
        findIndexOnCart(prodId) {
            return this.cartItems.findIndex(p => p.id == prodId);
        },
        updateGlobalTotal(sum, price) {
            if (sum) {
                this.orderTotal += price;
            }
            else {
                this.orderTotal -= price;
            }
        },
        updateQuantityPriceAndLineTotal(quantity, price, index) {
            this.cartItems[index].quantity += quantity;
            if (quantity > 0) {
                this.cartItems[index].total += price;
                this.updateGlobalTotal(true, price);
            }
            else {
                this.cartItems[index].total -= price;
                this.updateGlobalTotal(false, price);
            }
            if (this.cartItems[index].quantity < 1) {
                this.cartItems.splice(index, 1);
            }
            //fix to refresh cart
            this.cartItems.push();
        },
        goLogin() {
            this.$router.push("/login");
        }
    },
    components: { StripePayment }
}
</script>