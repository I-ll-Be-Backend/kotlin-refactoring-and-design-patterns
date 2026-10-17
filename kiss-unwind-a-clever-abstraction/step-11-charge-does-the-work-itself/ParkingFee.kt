data class Ticket(val minutes: Int, val electric: Boolean) {
    val hours: Int get() = (minutes + 59) / 60
}
object ParkingFee {
    fun charge(t: Ticket): Double {
        if (t.minutes <= 15) return 0.0
        return minOf(t.hours * 3.0, 20.0)
    }
}
