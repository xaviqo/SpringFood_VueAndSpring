<template>
    <v-col cols="4">
        <v-card outlined tile min-height="20vh">
          <div class="d-flex flex-no-wrap justify-space-between">
            <div>
              <v-card-title class="body-1 text-weight-light">Expanded order</v-card-title>
            </div>
              <v-avatar size="50">
                <v-icon color="green" large>mdi-magnify</v-icon>
              </v-avatar>
          </div>
          <v-divider/>
          <v-flex style="overflow: auto">
            <v-data-table
              loading
              :loading="loadingLine"
              loading-text="Loading order line..."
              no-data-text="Please, select an order to expand"
              :headers="headers"
              :items="expandedOrder"
              :items-per-page="99"
              hide-default-footer
              disable-pagination
              height="55vh">
            </v-data-table>
        </v-flex>
        </v-card>
    </v-col>
</template>
<script>
  export default {
    props: ['orderLine'],
    data () {
      return {
        headers: [
            { text: 'Line', value: 'idAndDashboardId' },
            { text: 'Product', value: 'productIdAndName' },
            { text: 'Quantity', value: 'productQuantity',},
            { text: 'Total', value: 'lineTotal'}
          ],
        loadingLine: false
      }
    },
    created: async function() {
    },
    methods: {
    },
    props: {
      expandedOrder: []
    }
  }
</script>