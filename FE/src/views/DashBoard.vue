<template>
  <v-container>
        <dashboard-cards/>
        <!--<dashboard-table/>-->
  </v-container>
</template>
<script>
import DashboardCards from "@/components/DashboardCards.vue"
import DashboardTable from "@/components/DashboardTable.vue"
export default {
  components : {
    DashboardCards,
    DashboardTable
}
}
</script>