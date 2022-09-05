<template>
  <v-container>
      <v-row>
        <v-col cols="4">
          <v-col cols="12">
            <div>
              <v-app-bar
                class="elevation-0"
                color="green"
                dense
                dark
              >
              <v-icon>mdi-account</v-icon>
                <v-toolbar-title class="ml-3">Account info</v-toolbar-title>
                <v-spacer></v-spacer>
                <v-spacer></v-spacer>
                <div class="text-button ml-6"> 
                  <v-btn
                    color="white"
                    class="white--text"
                    outlined
                    small
                    tile
                    @click="prepareEditAccount()"
                    width="145px"
                  >
                  <v-icon
                      left
                      dark
                    >
                      mdi-pencil
                  </v-icon>  
                    Edit info
                  </v-btn>
                </div>
              </v-app-bar>
            </div>
            <div>
              <v-card elevation="0" outlined>
                <v-card-text>
                  <v-row>
                    <v-col cols="6">
                      <span>
                        <v-icon small class="mt-n1 mr-2">mdi-card-account-details-outline</v-icon>
                        <strong class="mr-1">Id: </strong> {{account.id}}
                      </span>
                    </v-col>
                    <v-col cols="6">
                      <span>
                        <v-icon small class="mt-n1 mr-2">mdi-format-text-variant</v-icon>
                        <strong class="mr-1">Name: </strong>{{account.name}}
                      </span>
                    </v-col>
                    <v-col cols="6">
                      <span>
                        <v-icon small class="mt-n1 mr-2">mdi-at</v-icon>
                        <strong class="mr-1">Email: </strong> {{account.email}}
                      </span>
                    </v-col>
                    <v-col cols="6">
                      <span>
                        <v-icon small class="mt-n1 mr-2">mdi-phone</v-icon>
                        <strong class="mr-1">Phone: </strong> {{account.phone}}
                      </span>
                    </v-col>
                  </v-row>
                </v-card-text>
              </v-card>
            </div>
          </v-col>
          <v-col cols="12"
          >
            <div
            >
              <v-app-bar
                class="elevation-0"
                color="green"
                dense
                dark
              >
              <v-icon class="mr-3">mdi-map</v-icon>
                <v-toolbar-title v-if="account.addresses == null">Address list is empty</v-toolbar-title>
                <v-toolbar-title v-else>Addresses</v-toolbar-title>
                <v-spacer></v-spacer>
                <div class="text-button ml-6"> 
                  <v-btn
                    color="white"
                    class="white--text"
                    outlined
                    small
                    tile
                    width="145px"
                    @click="newAddress.addressDialog = true"
                  >
                  <v-icon
                      left
                      dark
                    >
                      mdi-plus
                  </v-icon>  
                    Add Address
                  </v-btn>
                </div>
              </v-app-bar>
            </div>
            <div>
              <v-card elevation="0" outlined>
                <v-card-text v-for="add in account.addresses" :key="add.id" >
                  <v-row v-if="add.main" class="pa-3">
                      <span>
                        <v-icon small class="mt-n1 mr-2">mdi-map-marker-outline</v-icon>
                        {{add.address}}
                      </span>
                      <v-spacer></v-spacer>
                        <v-btn x-small icon
                          @click="editAddress(add.id,add.address)"
                        >
                          <v-icon small>mdi-pencil-outline</v-icon>
                        </v-btn>
                        <v-btn x-small icon class="ml-1"
                          @click="deleteAddress(add)"
                        >
                          <v-icon small>mdi-trash-can-outline</v-icon>
                        </v-btn>
                        <v-btn x-small icon 
                        >
                          <v-icon small v-if="add.main">mdi-pin</v-icon>
                          <v-icon small v-else>mdi-pin-outline</v-icon>
                        </v-btn>
                  </v-row>
                  <v-row v-else-if="!add.main" class="pa-3">
                      <span>
                        <v-icon small class="mt-n1 mr-2">mdi-map-marker-outline</v-icon>
                        {{add.address}}
                      </span>
                      <v-spacer></v-spacer>
                        <v-btn x-small icon
                          @click="editAddress(add.id,add.address)"
                        >
                          <v-icon small>mdi-pencil-outline</v-icon>
                        </v-btn>
                        <v-btn x-small icon class="ml-1"
                          @click="deleteAddress(add)"
                        >
                          <v-icon small>mdi-trash-can-outline</v-icon>
                        </v-btn>
                        <v-btn x-small icon 
                          @click="updateToMain(add)"
                        >
                          <v-icon small v-if="add.main">mdi-pin</v-icon>
                          <v-icon small v-else>mdi-pin-outline</v-icon>
                        </v-btn>
                  </v-row>
                </v-card-text>
              </v-card>
            </div>
            <v-row justify="center" class="mt-10" v-if="account.addresses == null">
              <v-btn
                class="white--text"
                color="green"
                elevation="1"
                tile
                x-large
                @click="newAddress.addressDialog = true"
              >
              Where do you want your deliveres                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
                <v-icon class="ml-2">
                  mdi-plus
                </v-icon>
              </v-btn>
            </v-row>
          </v-col>
        </v-col>
        <v-col cols="8" justify="end">
          <v-col v-if="orders == null">
            <v-app-bar
              class="elevation-0"
              color="green"
              dense
              dark
            >
              <v-icon>mdi-pin</v-icon>
              <v-toolbar-title class="ml-3">
              Order list is empty
              </v-toolbar-title>
              <v-spacer></v-spacer>
            </v-app-bar>
            <v-row justify="center" class="mt-10">
              <v-btn
                class="white--text"
                color="green"
                elevation="1"
                tile
                x-large
                @click="$router.push('/menu')"
              >
              place your first order ( ✿ ◠‿◠)
              </v-btn>
            </v-row>
          </v-col>
          <v-col v-for="order in orders" :key="order.id" v-else>
            <div class="mb-4">
            <v-app-bar
              class="elevation-0"
              color="green"
              dense
              dark
            >
              <v-icon>mdi-pin</v-icon>
              <v-toolbar-title class="ml-3" v-if="order">ID {{order.orderId}}</v-toolbar-title>
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
                <v-btn tile outlined small class="text-body-1 mt-n1 ml-1">{{centsToEuro(order.totalAmount)}}€</v-btn>
              </div>
              <div class="text-button ml-6">
                Lines 
                <v-btn tile outlined small class="text-body-1 mt-n1 ml-1">{{order.lines}}</v-btn>
              </div>
            </v-app-bar>
            <v-card elevation="0" outlined>
              <v-simple-table
                fixed-header
                min-height="100px"
                max-height="144px"
              >
                <thead>
                  <tr>
                    <th class="text-left">
                      Product
                    </th>
                    <th class="text-left">
                      Quantity
                    </th>
                    <th class="text-left">
                      Price
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
                    <td>{{ product.quantity }}</td>
                    <td>{{ centsToEuro(product.price) }}€</td>
                    <td>{{ centsToEuro(product.totalLine) }}€</td>
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
        </v-col>
      </v-row>
      <template>
        <v-row justify="center">
          <v-dialog
            v-model="newAddress.addressDialog"
            max-width="445"
          >
            <v-card elevation="0" tile>
              <v-app-bar
                class="elevation-0"
                color="green"
                dense
                dark
              >
              <v-icon>mdi-map-legend</v-icon>
                <v-toolbar-title class="ml-3">Add new address</v-toolbar-title>
            </v-app-bar>
            <v-card-text class="mt-6">
                <v-form ref="newAddressRef" v-model="newAddress.validator" @submit.prevent="addNewAddress()">
                  <v-text-field
                    label="City"
                    name="City"
                    prepend-icon="mdi-map-marker-outline"
                    type="text"
                    color="green"
                    v-model="newAddress.payload.city"
                    :rules="rules.city"
                    :counter="25"
                  />

                  <v-text-field
                    id="password"
                    label="Address"
                    name="Address"
                    prepend-icon="mdi-home-city-outline"
                    type="text"
                    color="green"
                    v-model="newAddress.payload.address"
                    :rules="rules.address"
                    :counter="40"
                  />
                </v-form>
              </v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn
                  color="green darken-1"
                  text
                  @click="newAddress.addressDialog = false"
                >
                  Cancel
                </v-btn>
                <v-btn
                  color="green darken-1"
                  text
                  @click="addNewAddress(newAddress.payload)"
                >
                  Save
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-row>
      </template>
      <template>
        <v-row justify="center">
          <v-dialog
            v-model="account.edit.dialog"
            max-width="445"
          >
            <v-card elevation="0" tile>
              <v-app-bar
                class="elevation-0"
                color="green"
                dense
                dark
              >
              <v-icon>mdi-map-legend</v-icon>
                <v-toolbar-title class="ml-3">Edit account info</v-toolbar-title>
            </v-app-bar>
              <v-card-text class="mt-6">
                <v-form ref="editAccountInfoRef" v-model="account.validator" @submit.prevent="editAccountInfo()">
                  <v-text-field
                    label="Springfood Id"
                    name="Id"
                    prepend-icon="mdi-card-account-details-outline"
                    type="text"
                    color="green"
                    disabled
                    v-model="account.id"
                  />
                  <v-text-field
                    label="Email"
                    name="Email"
                    prepend-icon="mdi-at"

                    type="text"
                    color="green"
                    v-model="account.edit.email"
                    :rules="account.rules.email"
                    :counter="25"
                  />
                  <v-text-field
                    id="password"
                    label="Name"
                    name="Name"
                    prepend-icon="mdi-format-text-variant"
                    type="text"
                    color="green"
                    v-model="account.edit.name"
                    :rules="account.rules.name"
                    :counter="40"
                  />
                  <v-text-field
                    label="Address"
                    name="Address"
                    prepend-icon="mdi-phone"
                    type="text"
                    color="green"
                    v-model="account.edit.phone"
                    :rules="account.rules.phone"
                    :counter="40"
                  />
                </v-form>
              </v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn
                  color="green darken-1"
                  text
                  @click="account.edit.dialog = false"
                >
                  Cancel
                </v-btn>
                <v-btn
                  color="green darken-1"
                  text
                  @click="editAccountInfo()"
                >
                  Save
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-row>
      </template>
  </v-container>
</template>
<script>
  import { mixins } from '@/mixins.js';
  import { EventBus } from '@/main.js';
    export default {
      mixins: [ mixins ],
      data () {
        return {
          orders: null,
          account: {
            addresses: null,
            edit: {
              email: "",
              name: "",
              phone: "",
              dialog: false,
              validator: true
            },
            rules: {
              name: [
                v => !!v || 'Name is required',
                v => (v && v.length <= 40) || 'Name must be less than 40 characters',
                v => (v && v.length >= 3) || 'Name must be at least 3 characters',
              ],
              email: [
                v => !!v || 'Email is required',
                v => (v && v.length <= 40) || 'Email must be less than 40 characters',
                v => (v && v.length >= 6) || 'Email must be at least 6 characters',
              ],
              phone: [
                v => !!v || 'Phone is required',
                v => (v && v.length <= 40) || 'Phone must be less than 12 characters',
                v => (v && v.length >= 6) || 'Phone must be at least 6 characters',
              ],
            }
          },
          newAddress: {
            payload: {
              addressId: null,
              address: null,
              city: null
            },
            addressDialog: false,
            validator: true
          },
          rules : {
            address: [
              v => !!v || 'Address is required',
              v => (v && v.length >= 3) || 'Address must be at least 3 characters',
            ],
            city: [
              v => !!v || 'City is required',
              v => (v && v.length >= 6) || 'City must be at least 6 characters',
            ]
          },
          isEdit: false
        }
      },
      created: async function() {
        this.retrieveAddresses();
        this.getClientOrders();
        this.loadLocalStorageAndId();
      },
      methods: {
        prepareEditAccount(){
          this.account.edit.dialog = true;
          this.account.edit.email = this.account.email;
          this.account.edit.name = this.account.name;
          this.account.edit.phone = this.account.phone;
        },
        editAccountInfo() {
          let editAcc = {
            email: this.account.edit.email,
            name: this.account.edit.name,
            phone: this.account.edit.phone
          }
          this.axios
            .post(`/api/account/editAccount`,editAcc)
            .then((res) => {
              console.log(res);
              if (res.data == 1) {
                this.account.email = this.account.edit.email;
                this.account.name = this.account.edit.name;
                this.account.phone = this.account.edit.phone;
                this.showAlert({
                  color: "green",
                  show: true,
                  type: "succed",
                  msg: "Account info edited"
                });
                localStorage.setItem("sf_session",JSON.stringify(res.data));
                this.axios.defaults.headers.common['Authorization'] = "Bearer " + res.data.token;     
              }
            })
            .catch((e) => {
              console.log(e);
              this.showAlert({
                color: "red",
                show: true,
                type: "error",
                msg: "Error editing account"
              });
            });
            this.account.edit.dialog = false;
        },
        updateToMain(address){

          if(!address.main){
            this.axios
            .post(`/api/client/address/updateToMain/${address.id}`)
            .then((res) => {
              if (res.data == 1){
                this.showAlert({
                  color: "green",
                  show: true,
                  type: "success",
                  msg: "Main successfully updated"
                });
              } else {
                this.showAlert({
                  color: "green",
                  show: true,
                  type: "success",
                  msg: "Invalid address ID"
                });
              }
              this.retrieveAddresses();
            })
            .catch((e) => {
              console.log(e);
              console.log(e);
              this.showAlert({
                color: "red",
                show: true,
                type: "error",
                msg: e.response.data.extraInfo
              });
            });
          } else {
            this.showAlert({
              color: "red",
              show: true,
              type: "error",
              msg: "This address is already main"
            });
          }
        },
        deleteAddress(address){

          if(!address.main){
            this.axios
            .delete(`/api/client/address/deleteAddress/${address.id}`)
            .then((res) => {
              if (res.data == 1){
                this.showAlert({
                  color: "green",
                  show: true,
                  type: "success",
                  msg: "Address successfully deleted"
                });
              } else {
                this.showAlert({
                  color: "green",
                  show: true,
                  type: "success",
                  msg: "Invalid address ID"
                });
              }
              this.retrieveAddresses();
            })
            .catch((e) => {
              console.log(e);
              this.showAlert({
                color: "red",
                show: true,
                type: "error",
                msg: e.response.data.extraInfo
              });
            });

          } else {
            this.showAlert({
              color: "red",
              show: true,
              type: "error",
              msg: "Cannot delete main address"
            });
          }

        },
        editAddress(id,addressStr){

          let array = addressStr.split(")");

          if(array.length == 2) {

            array[0] = array[0].replace("(","");
            array[1] = array[1].substring(1,array[1].length);
            this.newAddress.payload.addressId = id;
            this.newAddress.payload.city = array[0];
            this.newAddress.payload.address = array[1];

            this.newAddress.addressDialog = true;
            this.isEdit = true
          }

        },
        addNewAddress(address){

          let msgAlert = "added";

          if (this.isEdit){
            msgAlert = "edited";
            this.isEdit = false;
          } 

          if (this.$refs.newAddressRef.validate()){
            this.axios
            .post(`/api/client/address/newAddress`,address)
            .then((res) => {
              this.newAddress.addressDialog = false;
              this.newAddress.payload = {
                  addressId: null,
                  address: null,
                  city: null
                }
              this.$refs.newAddressRef.reset();
              this.retrieveAddresses();
              this.showAlert({
                color: "green",
                show: true,
                type: "success",
                msg: "Address "+msgAlert+" successfully"
              });
            })
            .catch((e) => {
                console.log(e);
            });
            
          } else {
            this.showAlert({
                color: "red",
                show: true,
                type: "error",
                msg: "Address not valid"
              });
          }


        },
        retrieveAddresses(){

          this.axios
            .get(`/api/client/address/getAddress`)
            .then((res) => {
              if (res.status == 200 && res.data.length > 0){
                this.account.addresses = res.data;
                this.account.phone = res.data[0].phone;
              } else {
                this.account.addresses = null;
                this.account.phone = "No phone added"
              }
            })
            .catch((e) => {
              this.showAlert({
                color: "red",
                show: true,
                type: "error",
                msg: "Error retrieving addresses"
              });
            });

        },
        getClientOrders(){

          this.axios
            .get(`/api/client/order/getOrders`)
            .then((res) => {
              if (res.status == 200 && res.data.length > 0){
                this.orders = res.data;
                this.orders.forEach(order => {
                  order.lines = order.orderLines.length
                })
              } else {
                this.orders = null;
              }
            }).catch((e) => {
              this.showAlert({
                color: "red",
                show: true,
                type: "error",
                msg: "Error loading orders"
              });
            });

        },
        showAlert(model){
          EventBus.$emit('updateAlert',
            model
          );
        },
        loadLocalStorageAndId(){

          this.axios
            .get(`/api/account/getId`)
            .then((res) => {
              this.account.id = res.data;
            }).catch((e) => {
              this.showAlert({
                color: "red",
                show: true,
                type: "error",
                msg: "Error retrieving user id"
              });
            });

          const SF_localStorage = JSON.parse(localStorage.getItem("sf_session"));

          if (localStorage.getItem("sf_session") != null){

            this.account.name = SF_localStorage.name;
            this.account.email = SF_localStorage.email;
            this.account.phone = ""

          }

        },
      }
    }
</script>