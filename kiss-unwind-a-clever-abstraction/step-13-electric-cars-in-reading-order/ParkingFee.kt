data class Ticket(val minutes: Int, val electric: Boolean) {
    val hours: Int get() = (minutes + 59) / 60
}
object ParkingFee {
    private const val HOURLY_RATE = 3.0
    private const val DAILY_CAP = 20.0
    private const val GRACE_MINUTES = 15
    private const val EV_FREE_HOURS = 2

    fun charge(t: Ticket): Double {
        if (t.minutes <= GRACE_MINUTES) return 0.0
        val freeHours = if (t.electric) EV_FREE_HOURS else 0
        val hours = maxOf(0, t.hours - freeHours)
        return minOf(hours * HOURLY_RATE, DAILY_CAP)
    }
}
