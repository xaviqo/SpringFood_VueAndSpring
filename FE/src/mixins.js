export const mixins = {
    methods: {
        centsToEuro: function (cents) {
            return (cents / 100).toFixed(2);
        },
        getColor(bool) {
            if (bool) return 'green'
            else return 'red'
        },
        manageNullImages(products) {
            products.forEach(p => {
                if (!p.cloudId) p.cloudId = "null_image";
            });

            return products;
        }
    }
}