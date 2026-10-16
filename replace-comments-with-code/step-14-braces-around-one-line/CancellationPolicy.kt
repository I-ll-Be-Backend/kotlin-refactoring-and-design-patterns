import java.time.LocalDate
import java.time.temporal.ChronoUnit.DAYS

data class Booking(
    val checkIn: LocalDate,
    val total: Double,
    val nights: Int,
    val rate: Char,
    val loyal: Boolean,
)
object CancellationPolicy {
    private const val FREE_CANCELLATION_DAYS = 14
    private const val LOYALTY_FREE_DAYS = 7
    private const val LAST_MINUTE_DAYS = 2
    private const val NON_REFUNDABLE = 'N'

    fun fee(b: Booking, today: LocalDate): Double {
        val daysBeforeCheckIn = DAYS.between(today, b.checkIn)
        if (isFreeCancellation(b, daysBeforeCheckIn)) return 0.0
        if (paysInFull(b, daysBeforeCheckIn)) return b.total
        val halfTotal = b.total * 0.5
        // partner contracts require a one-night minimum
        return maxOf(halfTotal, nightlyPrice(b))
    }

    private fun isFreeCancellation(b: Booking, days: Long): Boolean {
        if (b.loyal) return days >= LOYALTY_FREE_DAYS
        return days >= FREE_CANCELLATION_DAYS
    }

    private fun paysInFull(b: Booking, days: Long): Boolean {
        return b.rate == NON_REFUNDABLE || days < LAST_MINUTE_DAYS
    }

    private fun nightlyPrice(b: Booking): Double {
        return b.total / b.nights
    }
}
