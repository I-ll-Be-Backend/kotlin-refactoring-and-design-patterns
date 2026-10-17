data class Ticket(val minutes: Int, val electric: Boolean) {
    val hours: Int get() = (minutes + 59) / 60
}
object ParkingFee {
    private const val HOURLY_RATE = 3.0
    private const val DAILY_CAP = 20.0
    private const val GRACE_MINUTES = 15

    fun charge(t: Ticket): Double {
        if (t.minutes <= GRACE_MINUTES) return 0.0
        return minOf(t.hours * HOURLY_RATE, DAILY_CAP)
    }
}
