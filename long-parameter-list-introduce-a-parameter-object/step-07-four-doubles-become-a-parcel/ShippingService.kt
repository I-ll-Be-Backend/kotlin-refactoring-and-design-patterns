fun interface RateTable { fun perKg(fromCountry: String, toCountry: String): Double }
data class Quote(val fromPostal: String, val toPostal: String, val price: Double)
data class Cart(val weightKg: Double, val lengthCm: Double, val widthCm: Double,
                val heightCm: Double, val express: Boolean, val fragile: Boolean)
data class Customer(val postalCode: String, val country: String)
data class Address(val postal: String, val country: String)

data class Parcel(val weightKg: Double, val lengthCm: Double,
                  val widthCm: Double, val heightCm: Double)

class ShippingService(private val rates: RateTable) {
    fun quote(from: Address, to: Address, parcel: Parcel,
              express: Boolean): Quote {
        val price = price(from, to, parcel, express)
        return Quote(from.postal, to.postal, price)
    }

    private fun price(from: Address, to: Address, parcel: Parcel,
                      express: Boolean): Double {
        val volumetricKg = parcel.lengthCm * parcel.widthCm *
            parcel.heightCm / 5000
        var cost = rates.perKg(from.country, to.country) *
            maxOf(parcel.weightKg, volumetricKg)
        if (express) cost *= 1.5
        return cost
    }
}

private const val WAREHOUSE_POSTAL = "10115"
private const val WAREHOUSE_COUNTRY = "DE"

class Checkout(private val shipping: ShippingService) {
    fun shippingFor(cart: Cart, customer: Customer): Quote {
        val from = Address(WAREHOUSE_POSTAL, WAREHOUSE_COUNTRY)
        val to = Address(customer.postalCode, customer.country)
        val parcel = Parcel(cart.weightKg, cart.lengthCm,
            cart.widthCm, cart.heightCm)
        return shipping.quote(from, to, parcel, cart.express)
    }
}
