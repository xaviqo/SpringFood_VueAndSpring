<template>
<v-row>
  <div id="stripe" style="width: 30em">
  </div>
</v-row>
</template>
<script>
import { loadStripe } from "@stripe/stripe-js";
export default {
  name: 'ProductsView',
  components : {},
  cardStyle: {
    base: {
      iconColor: '#c4f0ff',
      color: '#fff',
      fontWeight: '500',
      fontFamily: 'Roboto, Open Sans, Segoe UI, sans-serif',
      fontSize: '16px',
      fontSmoothing: 'antialiased',
      ':-webkit-autofill': {
        color: '#fce883',
      },
      '::placeholder': {
        color: '#87BBFD',
      },
    },
    invalid: {
      iconColor: '#FFC7EE',
      color: '#FFC7EE',
    },
  },
  data () {
    return {
      payMethod: {},
      stripeKey : process.env.VUE_APP_STRIPE_PUB,
      stripeObject: null,
      stripeLoading: true,
    }
  },
  mounted () {
    this.stripeCard();
  },
  methods: {
    async stripeCard(){
      this.stripeObject = await loadStripe(this.stripeKey);
      let elements = this.stripeObject.elements();
      const element = elements.create("card");
      console.log(element);
      element.mount("#stripe");
    }
  }
}
</script>