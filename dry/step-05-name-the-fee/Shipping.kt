data class Cart(val subtotal: Double, val member: Boolean)
object ShippingPolicy {
    const val FREE_FROM = 50.0
    const val FEE = 4.99
}

class Checkout {
    fun shippingFee(cart: Cart): Double {
        if (cart.subtotal >= ShippingPolicy.FREE_FROM) {
            return 0.0
        }
        return ShippingPolicy.FEE
    }
}

class CartBanner {
    fun message(cart: Cart): String {
        if (cart.subtotal < ShippingPolicy.FREE_FROM) {
            val missing = ShippingPolicy.FREE_FROM - cart.subtotal
            return "Add \$$missing to ship free"
        }
        return "Your order ships free"
    }
}

class OrderEmail {
    fun shippingLine(cart: Cart): String {
        val free = cart.subtotal >= ShippingPolicy.FREE_FROM
        return "Shipping: " + if (free) "free" else "$" + ShippingPolicy.FEE
    }
}
