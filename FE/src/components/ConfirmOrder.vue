<template>
  <v-row justify="space-around">
    <v-col cols="auto">
      <v-dialog
        max-width="850"
        v-model="checkingOrder.show"
      > 
          <v-card elevation="14">
            <v-toolbar
              color="primary"
              dark
              elevation="0"
            >
            <span class="font-weight-regular text-h6">Check your order and select a payment method</span>
            </v-toolbar>
            <v-card-text>
                <template>
                    <v-simple-table
                        fixed-header
                        height="300px"
                    >
                        <template v-slot:default>
                        <thead>
                            <tr>
                            <th class="text-left">
                                Quantity
                            </th>
                            <th class="text-left">
                                Name
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
                            v-for="item in checkingOrder.products"
                            :key="item.name"
                            >
                                <td>{{ item.quantity }}</td>
                                <td>{{ item.name }}</td>
                                <td>{{ item.price }}</td>
                                <td>{{ item.total }}</td>
                            </tr>
                            <tr>
                                <td></td>
                                <td></td>
                                <td class="text-right">TOTAL</td>
                                <td>{{checkingOrder.total}}€</td>
                            </tr>
                        </tbody>
                        </template>
                    </v-simple-table>
                </template>
            </v-card-text>
            <v-card-actions class="justify-end">
              <v-btn
                text
                @click="confirmDialog = false"
              >Cancel</v-btn>
            </v-card-actions>
          </v-card>

      </v-dialog>
    </v-col>
  </v-row>
</template>
<script>
export default{
    props: ['orderObject'],
    data () {
        return {
        }
    }, 
    props: {
      checkingOrder: this.orderObject
    },
    created () {
      this.checkingOrder.show = true;
    }
}
</script>