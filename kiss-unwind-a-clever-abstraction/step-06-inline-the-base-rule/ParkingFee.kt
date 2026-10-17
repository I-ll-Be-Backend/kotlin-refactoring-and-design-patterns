data class Ticket(val minutes: Int, val electric: Boolean) {
    val hours: Int get() = (minutes + 59) / 60
}
typealias Rule = (Ticket, Double) -> Double

object ParkingFee {
    private val RULES: Map<String, Rule> = mapOf(
        "base" to { t, _ -> t.hours * 3.0 },
        "cap" to { _, f -> minOf(f, 20.0) },
        "grace" to { t, f -> if (t.minutes <= 15) 0.0 else f },
    )

    fun fee(t: Ticket): Double {
        var f = 0.0
        f = t.hours * 3.0
        f = RULES.getValue("cap")(t, f)
        f = RULES.getValue("grace")(t, f)
        return f
    }

    fun charge(t: Ticket): Double {
        return fee(t)
    }
}
