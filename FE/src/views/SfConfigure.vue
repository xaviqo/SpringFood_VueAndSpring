<template>
    <!--
        SF: {
            location,
            deliveryTimes,
            dashboardTimes
        },
        STOCK: {

        },
        WORKERS: {
            Permissions:{
                OrderManager,
                Stock,
                AdminCfg
            }
        }
    -->
    <v-container>
        <v-row>
            <v-col cols="6">
                <v-card outlined tile min-height="20vh">
                    <div class="d-flex flex-no-wrap justify-space-between">
                        <div>
                            <v-card-title class="body-1 text-weight-light pt-4">Location</v-card-title>
                        </div>
                        <v-spacer></v-spacer>
                        <v-divider vertical></v-divider>
                        <div>
                            <v-avatar size="50" class="pt-2 mr-2 ml-3">
                                <v-icon color="green" large>mdi-leaf</v-icon>
                            </v-avatar>
                        </div>
                    </div>
                    <v-divider></v-divider>
                    <v-col>

                    </v-col>
                </v-card>
            </v-col>
            <v-col cols="6">
                <v-card outlined tile min-height="20vh">
                    <div class="d-flex flex-no-wrap justify-space-between">
                        <div>
                            <v-card-title class="body-1 text-weight-light pt-4">Delivery times</v-card-title>
                        </div>
                        <v-spacer></v-spacer>
                        <v-divider vertical></v-divider>
                        <div>
                            <v-avatar size="50" class="pt-2 mr-2 ml-3">
                                <v-icon color="green" large>mdi-timer-cog-outline</v-icon>
                            </v-avatar>
                        </div>
                    </div>
                    <v-divider></v-divider>
                    <v-col>
                        <v-form ref="signupRef" v-model="signupValidator" @submit.prevent="sendSignup()">
                            <v-row>
                                <v-col>
                                    <v-text-field v-model="deliveryTimes.first" label="Open" required>
                                    </v-text-field>
                                </v-col>
                                <v-col>
                                    <v-text-field v-model="deliveryTimes.last" label="Close" required>
                                    </v-text-field>
                                </v-col>
                                <v-col>
                                    <v-text-field v-model="deliveryTimes.slot" label="Slot" required>
                                    </v-text-field>
                                </v-col>
                            </v-row>
                            <v-row class="mt-n3">
                                <v-col>
                                    <v-btn block color="grey" outlined elevation="0" tile>
                                        Reset
                                    </v-btn>
                                </v-col>
                                <v-col>
                                    <v-btn block color="success" outlined elevation="0" tile>
                                        Save
                                    </v-btn>
                                </v-col>
                            </v-row>
                        </v-form>
                    </v-col>
                </v-card>
            </v-col>
        </v-row>
        <v-row>
            <v-col cols="6">
                <StockCfg></StockCfg>
            </v-col>
        </v-row>
    </v-container>
</template>
<script>
import StockCfg from '@/components/StockCfg.vue'
export default {
    data: () => ({
        deliveryTimes: {
            first: "20:00",
            last: "24:30",
            slot: "15"
        }
    }),
    components: { StockCfg }
}
</script>