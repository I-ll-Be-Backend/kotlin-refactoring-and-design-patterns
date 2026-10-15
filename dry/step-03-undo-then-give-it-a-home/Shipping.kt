data class Cart(val subtotal: Double, val member: Boolean)
class Checkout {
    fun shippingFee(cart: Cart): Double {
        if (cart.subtotal >= 50.0) {
            return 0.0
        }
        return 4.99
    }
}

class CartBanner {
    fun message(cart: Cart): String {
        if (cart.subtotal < 50.0) {
            val missing = 50.0 - cart.subtotal
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
