
data class Loan(val daysOut: Int, val student: Boolean)

class Fees {
    fun calc(loan: Loan): Double {
        val daysLate = loan.daysOut - 14
        if (daysLate <= 0) {
            return 0.0
        }
        val dailyRate = if (loan.student) 0.10 else 0.25
        return minOf(daysLate * dailyRate, 10.0)
    }
}

class ReturnDesk {
    private val fees = Fees()

    fun receipt(loan: Loan): String {
        return "Late fine: $" + fees.calc(loan)
    }
}
