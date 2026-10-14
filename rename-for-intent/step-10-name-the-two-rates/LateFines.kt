
data class Loan(val daysOut: Int, val student: Boolean)

private const val LOAN_DAYS = 14
private const val STUDENT_RATE = 0.10
private const val STANDARD_RATE = 0.25

class Fees {
    fun calc(loan: Loan): Double {
        val daysLate = loan.daysOut - LOAN_DAYS
        if (daysLate <= 0) {
            return 0.0
        }
        val dailyRate = if (loan.student) STUDENT_RATE else STANDARD_RATE
        return minOf(daysLate * dailyRate, 10.0)
    }
}

class ReturnDesk {
    private val fees = Fees()

    fun receipt(loan: Loan): String {
        return "Late fine: $" + fees.calc(loan)
    }
}
