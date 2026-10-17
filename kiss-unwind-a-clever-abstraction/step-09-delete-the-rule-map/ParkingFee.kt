data class Ticket(val minutes: Int, val electric: Boolean) {
    val hours: Int get() = (minutes + 59) / 60
}
object ParkingFee {
    fun fee(t: Ticket): Double {
        var f = 0.0
        f = t.hours * 3.0
        f = minOf(f, 20.0)
        f = if (t.minutes <= 15) 0.0 else f
        return f
    }

    fun charge(t: Ticket): Double {
        return fee(t)
    }
}
