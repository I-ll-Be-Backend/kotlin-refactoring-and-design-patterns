data class Cart(val subtotal: Double, val member: Boolean)
object ShippingPolicy {
    private const val FREE_FROM = 50.0
    private const val MEMBER_FREE_FROM = 25.0
    const val FEE = 4.99

    fun shipsFree(cart: Cart): Boolean {
        return cart.subtotal >= freeFrom(cart)
    }

    fun amountToFree(cart: Cart): Double {
        return freeFrom(cart) - cart.subtotal
    }

    private fun freeFrom(cart: Cart): Double {
        return if (cart.member) MEMBER_FREE_FROM else FREE_FROM
    }
}

class Checkout {
    fun shippingFee(cart: Cart): Double {
        if (ShippingPolicy.shipsFree(cart)) {
            return 0.0
        }
        return ShippingPolicy.FEE
    }
}

class CartBanner {
    fun message(cart: Cart): String {
        if (!ShippingPolicy.shipsFree(cart)) {
            val missing = ShippingPolicy.amountToFree(cart)
            return "Add \$$missing to ship free"
        }
        return "Your order ships free"
    }
}

class OrderEmail {
    fun shippingLine(cart: Cart): String {
        val free = ShippingPolicy.shipsFree(cart)
        return "Shipping: " + if (free) "free" else "$" + ShippingPolicy.FEE
    }
}
