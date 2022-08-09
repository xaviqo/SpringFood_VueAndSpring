<template>
    <v-footer dar padless app inset>
        <v-card class="flex" flat title :elevation="10" color="green">
        <v-row >
            <v-col cols="4">
            </v-col>
            <v-col cols="4">
                <v-card-text class="py-2 white--text text-center subheading">
                    {{ new Date().getFullYear() }} - SpringFood by XAVI.TECH
                </v-card-text>
            </v-col>
            <v-col cols="4" class="text-right mt-1 ml-n4">
                <v-btn elevation="0" outlined x-small tile dark plain>
                    <span>XAVI.TECH</span>
                    <v-icon right>mdi-exit-to-app</v-icon>
                </v-btn>
            </v-col>
        </v-row>
        </v-card>
    </v-footer>
</template>
