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
            no-data-text="Loading today orders..."
            :headers="headers"
            :items="desserts"
            :items-per-page="10"
            dense
          >
          </v-data-table>
        </v-card>
      </template>
    </v-col>
      <dashboard-simple-table/>
  </v-row>
</template>
<script>
import DashboardSimpleTable from "@/components/DashboardSimpleTable.vue"
  export default {
    components: {
      DashboardSimpleTable
    },
    data: () => ({
      headers: [
          { text: 'Order', value: 'id' },
          { text: 'Time', value: 'time' },
          { text: 'Client', value: 'client' },
          { text: 'Phone', value: 'phone', sortable: false },
          { text: 'Address', value: 'address', sortable: false },
          { text: 'Total', value: 'total' },
          { text: 'Actions', value: 'actions', sortable: false },
        ],
        desserts: [
          {
            name: 'Frozen Yogurt',
            calories: 159,
            fat: 6.0,
            carbs: 24,
            protein: 4.0,
            iron: '1%',
          },
          {
            name: 'Ice cream sandwich',
            calories: 237,
            fat: 9.0,
            carbs: 37,
            protein: 4.3,
            iron: '1%',
          },
          {
            name: 'Eclair',
            calories: 262,
            fat: 16.0,
            carbs: 23,
            protein: 6.0,
            iron: '7%',
          },
          {
            name: 'Cupcake',
            calories: 305,
            fat: 3.7,
            carbs: 67,
            protein: 4.3,
            iron: '8%',
          },
          {
            name: 'Gingerbread',
            calories: 356,
            fat: 16.0,
            carbs: 49,
            protein: 3.9,
            iron: '16%',
          },
          {
            name: 'Jelly bean',
            calories: 375,
            fat: 0.0,
            carbs: 94,
            protein: 0.0,
            iron: '0%',
          },
          {
            name: 'Lollipop',
            calories: 392,
            fat: 0.2,
            carbs: 98,
            protein: 0,
            iron: '2%',
          },
          {
            name: 'Honeycomb',
            calories: 408,
            fat: 3.2,
            carbs: 87,
            protein: 6.5,
            iron: '45%',
          },
          {
            name: 'Donut',
            calories: 452,
            fat: 25.0,
            carbs: 51,
            protein: 4.9,
            iron: '22%',
          },
          {
            name: 'KitKat',
            calories: 518,
            fat: 26.0,
            carbs: 65,
            protein: 7,
            iron: '6%',
          },
        ]
    })
  }
</script>
