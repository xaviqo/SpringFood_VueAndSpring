<template>
    <v-footer dar padless app inset>
        <v-card class="flex" flat title :elevation="10" color="green">
            <v-card-text class="py-2 white--text text-center subheading">
                {{ new Date().getFullYear() }} - SpringFood by XAVI.TECH
            </v-card-text>
            <v-spacer/>
        </v-card>
    </v-footer>
</template>
