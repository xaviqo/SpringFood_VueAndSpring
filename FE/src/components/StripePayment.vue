<template>
    <v-card>
        <v-toolbar
        color="primary"
        dark
        elevation="0"
        >
        <span class="font-weight-regular text-h6">Select a payment method and your delivery place</span>
        </v-toolbar>
        <v-card-text>
            <template>
            <v-stepper v-model="steps" class="elevation-0">
            <v-stepper-header
            width="100vw"
            class="mx-n6 elevation-0"
            >
                <v-stepper-step
                :complete="steps > 0"
                step="1"
                >
                Check order
                </v-stepper-step>

                <v-divider></v-divider>

                <v-stepper-step
                :complete="steps > 1"
                step="2"
                >
                Delivery place and payment
                </v-stepper-step>

                <v-divider></v-divider>

                <v-stepper-step 
                :complete="steps > 2"
                step="3"
                >
                Bon appetit!
                </v-stepper-step>
            </v-stepper-header>
            <v-stepper-items>
                <v-stepper-content step="1">
                <v-simple-table
                    fixed-header
                    height="350px"
                >
                    <template v-slot:default>
                    <thead>
                        <tr>
                        <th class="text-left">
                            Name
                        </th>
                        <th class="text-left">
                            Quantity
                        </th>
                        <th class="text-left">
                            Price
                        </th>
                        <th class="text-left">
                            Total
                        </th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr
                        v-for="item in $store.state.cartItems"
                        :key="item.id"
                        >
                            <td>{{ item.name }}</td>
                            <td>{{ item.quantity }}</td>
                            <td>{{ centsToEuro(item.price) }}</td>
                            <td>{{ centsToEuro(item.total) }}</td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td class="text-right">TOTAL</td>
                            <td>{{ centsToEuro($store.state.orderTotal) }}€</td>
                        </tr>
                    </tbody>
                    </template>
                </v-simple-table>
                </v-stepper-content>
                <v-stepper-content step="2">
                <v-row>
                    <v-col>
                        <v-card
                        outlined
                        class="elevation-0 mb-5"
                        color="white"
                        tile
                        >
                        <v-sheet
                        class="text-h6"
                        color="green"
                        dark>
                        <v-card-title class="font-weight-light">
                            <v-icon left>mdi-credit-card-multiple-outline</v-icon> Credit Card
                        </v-card-title>
                        </v-sheet>
                        <v-container>

                                <template>
                                    <div class="mt-3">
                                        <stripe-element-card
                                        ref="elementRef"
                                        :hidePostalCode="true"
                                        :pk="publishableKey"
                                        @token="tokenCreated"
                                        />
                                    </div>
                                    
                                </template>

                        </v-container>
                        <v-alert
                        v-model="stripeAlert.show"
                        dismissible
                        dense
                        outlined
                        :type="stripeAlert.type"
                        class="mb-n2"
                        >
                        <strong>{{stripeAlert.code}}</strong> {{stripeAlert.msg}}
                        </v-alert>
                        </v-card>
                    </v-col>
                </v-row>
                    <v-divider/>
                    <v-row class="mt-2">
                    <v-col cols="6">
                        <v-card
                        outlined
                        class="elevation-0 mb-3"
                        color="white"
                        tile
                        >
                        <v-sheet
                        class="text-h6"
                        color="green"
                        dark>
                        <v-card-title class="font-weight-light">
                            <v-icon left>mdi-information-outline</v-icon> Delivery Info
                        </v-card-title>
                        </v-sheet>
                        <div class="mt-2 mb-1 pl-1 font-weight-regular text-subtitle-1">
                            <v-icon small>mdi-account</v-icon> {{ finalOrder.name }}
                        </div>
                        <div class="mt-1 pl-1 font-weight-regular text-subtitle-1">
                            <v-icon small>mdi-cellphone</v-icon> {{ finalOrder.phone }}
                        </div>
                        <div class="mt-1 pl-1 font-weight-regular text-subtitle-1">
                            <v-icon small>mdi-map-marker</v-icon> {{ finalOrder.address.address }}
                        </div>
                        <div class="mt-4 pl-1 font-weight-medium text-subtitle-1">
                            <v-icon x-small>mdi-circle-medium</v-icon> Select a different delivery point
                        </div>
                        <v-radio-group 
                        v-model="finalOrder.address"
                        mandatory
                        >
                            <div>
                            <v-radio
                            v-for="add in clientAddresses"
                            class="ml-3"
                            color="green"
                            :label="add.address"
                            :value="add"
                            :key="add.id"
                            ></v-radio>
                        </div>
                        </v-radio-group>
                    </v-card>
                    </v-col>
                    <v-col cols="6">
                        <v-card
                        outlined
                        class="elevation-0"
                        color="white"
                        tile
                        >
                        <v-sheet
                        class="text-h6"
                        color="green"
                        dark>
                        <v-card-title class="font-weight-light">
                            <v-icon left>mdi-clock-time-nine-outline</v-icon> Delivery Time
                        </v-card-title>
                        </v-sheet>
                        <v-card-text>
                        <div class="pl-1 font-weight-medium text-subtitle-1">
                            <v-icon x-small>mdi-circle-medium</v-icon> Delivery time {{ finalOrder.deliveryTime }}
                        </div>
                        <v-chip-group
                            v-model="finalOrder.deliveryTime"
                            active-class="green"
                            column
                        >
                            <v-chip 
                            v-for="(chip,key) in deliveryChips"
                            class="ma-2"
                            :key="key"
                            :value="chip"
                            outlined>{{chip}}</v-chip>

                        </v-chip-group>
                        </v-card-text>
                        </v-card>
                    </v-col>
                    </v-row>
                </v-stepper-content>
                <v-stepper-content step="3">
                    <v-row>
                        <v-col cols="6">
                            <v-card elevation="4">
                                <v-img
                                contain
                                max-height="250px"
                                src="https://c.tenor.com/DxfwOPp6L0oAAAAC/food-winnie-the-pooh.gif"
                                />
                            </v-card>
                        </v-col>
                        <v-col>
                            BIEN
                        </v-col>
                    </v-row>
                </v-stepper-content>
            </v-stepper-items>
            </v-stepper>
            </template>
        </v-card-text>
    <v-card-actions class="justify-end">
        <v-btn
        text
        @click="closeDialog()"
        >Cancel</v-btn>
        <v-btn
        text
        @click="checkStepper()"
        :disabled="waitTokenCheck"
        >{{nextBtn}}</v-btn>
    </v-card-actions>
    </v-card>
</template>
<script>
import { EventBus } from '@/main.js';
import { StripeElementCard } from '@vue-stripe/vue-stripe';
import { mixins } from '@/mixins.js';
export default {
    mixins: [ mixins ],
    components: {
        StripeElementCard,
    },
    data() {
        this.publishableKey = process.env.VUE_APP_STRIPE_PUB;
        return {
        token: null,
        waitTokenCheck: false,
        steps: 1,
        nextBtn: "NEXT",
        clientAddresses: null,
        finalOrder: {
            orderId: "",
            name: JSON.parse(localStorage.getItem("sf_session")).name,
            email: JSON.parse(localStorage.getItem("sf_session")).email,
            phone: "",
            address: {
                address : "",
                id: 0
            },
            deliveryTime: ""
        },
        stripeAlert: {
            show: false,
            msg: "",
            code: ""
        },
        deliveryChips: ["20:00","20:30","21:00","21:30","22:00","22:30","23:00","23:30"]
        }
    },
    created () {
        this.finalOrder.deliveryTime = this.deliveryChips[0];
    },
    methods: {
        tokenCreated (token) {

            this.stripeAlert.show = true;
            this.stripeAlert.type = "info";
            this.stripeAlert.msg = "Processing payment..."
            this.stripeAlert.code = "";
            this.waitTokenCheck = true;
            this.nextBtn = "WAIT...";
            let retrieveToken = Promise.resolve(token);
            let paymentDto = this.prepareJavaCartDTO(this.$store.state.cartItems);
            retrieveToken.then((tkn) => {

                paymentDto.token = tkn.id;

                this.axios
                    .post(`/api/stripe/charge`,paymentDto)
                    .then((res) => {
                        this.waitTokenCheck = false;
                        this.steps++;
                        this.nextBtn = "ENJOY!";
                    })
                    .catch((e) => {
                        console.log(e);
                        this.finalOrder.orderId = e.response.data.extraInfo;
                        this.stripeAlert.show = true;
                        this.stripeAlert.type = "error";
                        this.stripeAlert.code = "["+e.response.data.code+"] ";
                        this.stripeAlert.msg = e.response.data.message ? e.response.data.message : "No response from the API";
                        this.nextBtn = "PAY";
                        this.waitTokenCheck = false;
                    });

            });
        },
        async checkStepper(){

        if (this.steps === 1){
            this.prepareDeliveryChips();
            this.steps++;
            this.nextBtn = "PAY";
            this.waitTokenCheck = false;
            this.retrieveAddress();
        } else if (this.steps === 2){

            this.$refs.elementRef.submit();

        } else if (this.steps === 3){

            //TODO
        }

        },
        closeDialog() {
            EventBus.$emit('closeStripeDialog');
        },
        prepareDeliveryChips(){
            this.axios
                .get(`/api/menu/product/getDeliveryTimes`)
                .then((res) => {
                    this.deliveryChips = res.data;
                })
                .catch((e) => {
                    console.log(e);
                });
        },
        prepareJavaCartDTO(cart){

            let paymentDto = {
            clientTotal: this.$store.state.orderTotal,
            productsAndQuantity: new Map()
            };
            
            cart.forEach(prod => {
            paymentDto.productsAndQuantity[prod.id] = prod.quantity;
            });

            paymentDto.addressId = this.finalOrder.address.id;
            paymentDto.deliveryTime = this.finalOrder.deliveryTime;
            paymentDto.orderId = this.finalOrder.orderId;
            return paymentDto;

        },
        async retrieveAddress(){
            this.axios
            .get(`/api/client/address/getAddress`)
            .then((res) => {
                this.clientAddresses = res.data;
                this.finalOrder.phone = res.data[0].phone;

                //catch main address into payload
                this.clientAddresses.forEach(function(add){
                    if (add.main) this.finalOrder.address = add;
                });

            })
            .catch(() => {
                console.log("Error retrieving address");
            });
        },
    }
}
</script>