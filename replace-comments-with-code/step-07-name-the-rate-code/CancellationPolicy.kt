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
    private const val LAST_MINUTE_DAYS = 2
    private const val NON_REFUNDABLE = 'N'

    fun fee(b: Booking, today: LocalDate): Double {
        val daysBeforeCheckIn = DAYS.between(today, b.checkIn)
        // free cancellation 14 days or more before check-in
        if (daysBeforeCheckIn >= FREE_CANCELLATION_DAYS) return 0.0
        // non-refundable or under 2 days out: pay in full
        if (b.rate == NON_REFUNDABLE
            || daysBeforeCheckIn < LAST_MINUTE_DAYS) return b.total
        // otherwise half the total
        val f = b.total * 0.5
        // partner contracts require a one-night minimum
        return maxOf(f, b.total / b.nights)
    }
}
