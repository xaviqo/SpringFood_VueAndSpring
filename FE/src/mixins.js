export const mixins = {
    methods: {
        centsToEuro: function(cents) {
            return (cents / 100).toFixed(2);
        }
    }
}