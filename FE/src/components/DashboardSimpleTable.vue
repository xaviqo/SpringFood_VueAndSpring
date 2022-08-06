<template>
    <v-col cols="4">
        <v-card outlined tile min-height="20vh">
          <div class="d-flex flex-no-wrap justify-space-between">
            <div>
              <v-card-title class="body-1 text-weight-light">Expanded order</v-card-title>
            </div>
              <v-avatar size="50">
                <v-icon color="green" large>mdi-magnify-plus</v-icon>
              </v-avatar>
          </div>
          <v-divider/>
        <v-data-table
        no-data-text="Select an order"
        :headers="headers"
        :items="desserts"
        hide-default-footer
        >
        </v-data-table>
        </v-card>
    </v-col>
</template>