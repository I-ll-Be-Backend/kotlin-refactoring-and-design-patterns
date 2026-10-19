fun interface RateTable { fun perKg(fromCountry: String, toCountry: String): Double }
data class Quote(val fromPostal: String, val toPostal: String, val price: Double)
data class Cart(val weightKg: Double, val lengthCm: Double, val widthCm: Double,
                val heightCm: Double, val express: Boolean, val fragile: Boolean)
data class Customer(val postalCode: String, val country: String)
data class Address(val postal: String, val country: String)

class ShippingService(private val rates: RateTable) {
    fun quote(from: Address,
              toPostal: String, toCountry: String, weightKg: Double,
              lengthCm: Double, widthCm: Double, heightCm: Double,
              express: Boolean): Quote {
        val price = price(from, toCountry, weightKg,
            lengthCm, widthCm, heightCm, express)
        return Quote(from.postal, toPostal, price)
    }

    private fun price(from: Address, toCountry: String,
                      weightKg: Double, lengthCm: Double,
                      widthCm: Double, heightCm: Double,
                      express: Boolean): Double {
        val volumetricKg = lengthCm * widthCm * heightCm / 5000
        var cost = rates.perKg(from.country, toCountry) *
            maxOf(weightKg, volumetricKg)
        if (express) cost *= 1.5
        return cost
    }
}

private const val WAREHOUSE_POSTAL = "10115"
private const val WAREHOUSE_COUNTRY = "DE"

class Checkout(private val shipping: ShippingService) {
    fun shippingFor(cart: Cart, customer: Customer): Quote {
        val from = Address(WAREHOUSE_POSTAL, WAREHOUSE_COUNTRY)
        return shipping.quote(from,
            customer.postalCode, customer.country, cart.weightKg,
            cart.lengthCm, cart.widthCm, cart.heightCm,
            cart.express)
    }
}
