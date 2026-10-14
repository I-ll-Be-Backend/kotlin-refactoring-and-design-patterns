
data class Loan(val daysOut: Int, val f: Boolean)

class Fees {
    fun calc(l: Loan): Double {
        val x = l.daysOut - 14
        if (x <= 0) {
            return 0.0
        }
        val r = if (l.f) 0.10 else 0.25
        return minOf(x * r, 10.0)
    }
}

class ReturnDesk {
    private val fees = Fees()

    fun receipt(l: Loan): String {
        return "Late fine: $" + fees.calc(l)
    }
}
