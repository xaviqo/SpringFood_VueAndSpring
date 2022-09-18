<template>
  <v-card outlined tile min-height="20vh">
    <div class="d-flex flex-no-wrap justify-space-between">
      <div>
        <v-card-title class="body-1 text-weight-light pt-4">Products management</v-card-title>
      </div>
      <v-spacer></v-spacer>
      <v-divider vertical></v-divider>
      <v-text-field v-model="search" append-icon="mdi-magnify" label="Search" single-line hide-details
        class="mr-4 ml-4"></v-text-field>
      <v-divider vertical></v-divider>
      <div>
        <v-avatar size="50" class="pt-2 mr-2 ml-3">
          <v-icon color="green" large>mdi-leaf</v-icon>
        </v-avatar>
      </div>
    </div>
    <v-divider></v-divider>
    <v-col>
      <div>
        <v-data-table :headers="headers" :items="stockProducts" height="50vh" :search="search">
          <!--HEADERS-->
          <template v-slot:header.price="{ header }">
            <span class="tableHeader">{{ header.text }}</span>
            <v-icon x-small>mdi-pencil</v-icon>
          </template>
          <template v-slot:header.stock="{ header }">
            <span class="tableHeader">{{ header.text }}</span>
            <v-icon x-small>mdi-pencil</v-icon>
          </template>
          <template v-slot:header.useStock="{ header }">
            <span class="tableHeader">{{ header.text }}</span>
            <v-icon x-small>mdi-pencil</v-icon>
          </template>
          <template v-slot:header.active="{ header }">
            <span class="tableHeader">{{ header.text }}</span>
            <v-icon x-small>mdi-pencil</v-icon>
          </template>
          <template v-slot:header.cloudId="{ header }">
            <span class="tableHeader">{{ header.text }}</span>
            <v-icon x-small>mdi-pencil</v-icon>
          </template>
          <!--#######-->
          <template v-slot:item.productId="{ item }">
            <strong>{{ item.productId }}</strong>
          </template>
          <template v-slot:item.name="{ item }">
            <span class="d-inline-block text-truncate" style="max-width: 100px;">{{ item.name }}</span>
          </template>
          <template v-slot:item.description="{ item }">
            <span class="d-inline-block text-truncate" style="max-width: 110px;">{{ item.description }}</span>
          </template>
          <template v-slot:item.price="{ item }">
            <v-edit-dialog :return-value.sync="item.price" @save="editPrice(item)" large persistent>
              <v-btn tile elevation="0" style="width: 50px">{{ centsToEuro(item.price) }}€</v-btn>
              <template v-slot:input>
                <v-text-field v-model="item.price" hint="Value in cents"
                  :rules="[/^\d+$/.test(item.price) || 'Only numbers acepted']" label="Edit" single-line counter>
                </v-text-field>
              </template>
            </v-edit-dialog>
          </template>
          <template v-slot:item.stock="{ item }">
            <v-chip :color="getStockColor(item.stock)" dark style="width: 40px">
              <strong>{{ item.stock }}</strong>
            </v-chip>
          </template>
          <template v-slot:item.cloudId="{ item }">
            <v-chip v-if="item.cloudId == 'null_image'" color="red" dark @click="editCloudImg(item)">
              <v-icon small >mdi-camera-outline</v-icon>
            </v-chip>
            <v-chip v-else color="green" dark @click="editCloudImg(item)">
              <v-icon small >mdi-camera-outline</v-icon>
            </v-chip>
          </template>
          <template v-slot:item.useStock="{ item }">
            <v-chip :color="getColor(item.useStock)" dark>
              <v-icon small v-if="item.useStock">mdi-check-bold</v-icon>
              <v-icon small v-else>mdi-close-thick</v-icon>
            </v-chip>
          </template>
          <template v-slot:item.active="{ item }">
            <v-chip :color="getColor(item.active)" dark>
              <v-icon small v-if="item.active">mdi-check-bold</v-icon>
              <v-icon small v-else>mdi-close-thick</v-icon>
            </v-chip>
          </template>
          <template v-slot:item.type="{ item }">
            <v-chip :color="getTypeColor(item.type.toUpperCase())" dark outlined pill>
              {{ item.type.toUpperCase() }}
            </v-chip>
          </template>
          <template v-slot:item.actions="{ item }">
            <v-btn x-small icon @click="editProduct(item)" class="ml-n5">
              <v-icon small>mdi-pencil-outline</v-icon>
            </v-btn>
          </template>
        </v-data-table>
        <template>
          <div class="text-center">
            <v-dialog v-model="imgDialog.open" width="500">
              <v-card>
                <v-card-title class="text-h5 grey lighten-2">
                  Update {{ imgDialog.product.name }} image
                  <v-btn small rounded fab color="red" dark style="float: right" class="ml-4 elevation-1" @click="deleteCloudImg()">
                    <v-icon>mdi-trash-can-outline</v-icon>
                  </v-btn>
                </v-card-title>
                <div class="d-flex justify-center my-5">
                  <v-card>
                    <cld-image v-bind:public-id="imgDialog.product.cloudId" loading="lazy">
                      <cld-transformation crop="fill" width="430" height="400" gravity="center" />
                      <cld-transformation fetch-format="auto" />
                      <cld-placeholder type="blur" />
                    </cld-image>
                  </v-card>
                </div>
                <v-card class="mx-9 elevation-0">
                  <v-file-input filled show-size accept="image/*" label="Update image" prepend-icon="mdi-camera"
                    v-model="imgDialog.img"></v-file-input>
                </v-card>
                <v-divider></v-divider>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="primary" text @click="imgDialog.open = false">
                    Cancel
                  </v-btn>
                  <v-btn color="primary" text @click="updateCloudImg()">
                    Save
                  </v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
          </div>
        </template>
      </div>
    </v-col>
  </v-card>
</template>
<script>
import { mixins } from '@/mixins.js';
import { EventBus } from '@/main.js';
export default {
  mixins: [mixins],
  data() {
    return {
      stockAlert: {
        high: 0,
        highColor: 'green',
        low: 0,
        lowColor: 'red',
        midColor: '#B0BEC5',
      },
      imgDialog: {
        open: false,
        product: {},
        img: {},
      },
      search: '',
      pagination: {},
      headers: [
        { text: 'ID', value: 'productId', width: '100%' },
        { text: 'Name', value: 'name', width: '100%' },
        { text: 'Description', value: 'description', sortable: false, width: '100%' },
        { text: 'Price', value: 'price', width: '100%' },
        { text: 'Stock', value: 'stock', width: '100%' },
        { text: 'Use Stock', value: 'useStock', width: '100%' },
        { text: 'Show', value: 'active', width: '100%' },
        { text: 'Image', value: 'cloudId', width: '100%' },
        { text: 'Category', value: 'type', align: 'start', width: '100%' },
        { text: null, value: 'actions', width: '100%' },
      ],
      stockProducts: [
      ],
    }
  },
  created() {
    this.loadTable();
  },
  methods: {
    editProduct(product) {
      //TODO
    },
    deleteProduct(product) {
      //TODO
    },
    editCloudImg(product) {
      this.imgDialog.open = true;
      this.imgDialog.product = product;
    },
    deleteCloudImg(){
      this.axios
          .delete(`/api/admin/product/deleteImg/${this.imgDialog.product.cloudId}`)
          .then((res) => {
            console.log(res);
            this.showAlert({
              color: "green",
              show: true,
              type: "success",
              msg: "Image successfully deleted"
            });
            this.loadTable();
          })
          .catch((e) => {
            console.log(e);
            this.showAlert({
              color: "red",
              show: true,
              type: "error",
              msg: e.response.data.message
            });
          });
    },
    updateCloudImg() {
      if (this.imgDialog.img.size < 2000000 && this.imgDialog.img.size > 0) {

        let id = this.imgDialog.product.productId;
        let formData = new FormData();

        formData.append("img", this.imgDialog.img, "newImage");

        this.axios
          .post(`/api/admin/product/editImg/${id}`, formData)
          .then((res) => {
            if (res.data == 1) {
              this.showAlert({
                color: "green",
                show: true,
                type: "success",
                msg: "Image successfully updated"
              });
              this.loadTable();
            }
          })
          .catch((e) => {
            this.showAlert({
              color: "red",
              show: true,
              type: "error",
              msg: e.response.data.message
            });
          });
      } else {
        this.showAlert({
          color: "red",
          show: true,
          type: "error",
          msg: "Invalid image size"
        });
      }
      this.imgDialog.open = false;
      this.imgDialog.img = 0;
    },
    editPrice(product) {
      if (/^\d+$/.test(product.price)) {
        let newProduct = {};
        newProduct.price = product.price;
        newProduct.id = product.productId;
        this.axios
          .post(`/api/admin/product/editPrice`, newProduct)
          .then((res) => {
            if (res.data == 1) {
              this.showAlert({
                color: "green",
                show: true,
                type: "success",
                msg: "Price successfully modified"
              });
            }
          })
          .catch((e) => {
            this.showAlert({
              color: "red",
              show: true,
              type: "error",
              msg: e.response.data.message
            });
          });
      } else {
        this.showAlert({
          color: "red",
          show: true,
          type: "error",
          msg: "Invalid price value"
        });
        this.loadTable();
      }
    },
    loadTable() {
      this.axios
        .get(`/api/admin/product/getAllProducts`)
        .then((res) => {
          this.stockProducts = res.data;
          this.configureStockAlert(res.data);
          this.stockProducts = this.manageNullImages(this.stockProducts);
          console.log(this.stockProducts);
        })
        .catch(() => {
          this.showAlert({
            color: "red",
            show: true,
            type: "error",
            msg: "Error retrieving stock products"
          });
        });
    },
    getStockColor(stock) {
      let color = '';

      if (stock >= this.stockAlert.high) {
        color = this.stockAlert.highColor;
      } else if (stock <= this.stockAlert.low) {
        color = this.stockAlert.lowColor;
      } else {
        color = this.stockAlert.midColor;
      }

      return color;
    },
    configureStockAlert(allProducts) {

      let sumStock = 0;
      let media = 0;
      allProducts.forEach(p => {
        sumStock += p.stock;
      });

      media = sumStock / allProducts.length
      this.stockAlert.high = Math.ceil(media * 2);
      this.stockAlert.low = Math.ceil(media / 2);

    },
    getTypeColor(type) {
      let color = '';
      switch (type) {
        case 'BURGER':
          color = '#094F29';
          break;
        case 'PIZZA':
          color = '#0A6921';
          break;
        case 'DRINK':
          color = '#1A8828';
          break;
        case 'SALAD':
          color = '#429B46';
          break;
        case 'PASTA':
          color = '#64AD62';
          break;
        case 'EXTRA':
          color = '#94C58C';
          break;
        default:
          color = 'green';
          break;
      }
      return color;
    },
    showAlert(model) {
      EventBus.$emit('updateAlert',
        model
      );
    }
  }
}
</script>
<style>
.v-data-footer__select {
  visibility: hidden
}

.v-data-table__wrapper {
  overflow-x: hidden !important;
}

div:nth-child(2)>div>div>div.col>div>div>div.v-data-table__wrapper>table>thead>tr>th {
  padding: 0px 15px;
  margin: 0px;
}

#app>div.v-dialog__content.v-dialog__content--active>div>div>div.d-flex.justify-center.my-5>div>img {
  display: block;
}
</style>