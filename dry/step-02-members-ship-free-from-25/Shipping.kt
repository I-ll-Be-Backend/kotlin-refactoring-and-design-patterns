data class Cart(val subtotal: Double, val member: Boolean)
class Checkout {
    fun shippingFee(cart: Cart): Double {
        val freeFrom = if (cart.member) 25.0 else 50.0
        if (cart.subtotal >= freeFrom) {
            return 0.0
        }
        return 4.99
    }
}

class CartBanner {
    fun message(cart: Cart): String {
        val limit = if (cart.member) 25.0 else 50.0
        if (cart.subtotal < limit) {
            val missing = limit - cart.subtotal
            return "Add \$$missing to ship free"
        }
        return "Your order ships free"
    }
}

class OrderEmail {
    fun shippingLine(cart: Cart): String {
        val free = cart.subtotal >= 50.0
        return "Shipping: " + if (free) "free" else "$4.99"
    }
}
