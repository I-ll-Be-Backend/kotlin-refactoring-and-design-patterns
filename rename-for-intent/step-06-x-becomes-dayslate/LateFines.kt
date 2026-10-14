
data class Loan(val daysOut: Int, val student: Boolean)

class Fees {
    fun calc(l: Loan): Double {
        val daysLate = l.daysOut - 14
        if (daysLate <= 0) {
            return 0.0
        }
        val r = if (l.student) 0.10 else 0.25
        return minOf(daysLate * r, 10.0)
    }
}

class ReturnDesk {
    private val fees = Fees()

    fun receipt(l: Loan): String {
        return "Late fine: $" + fees.calc(l)
    }
}
