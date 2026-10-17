data class Ticket(val minutes: Int, val electric: Boolean) {
    val hours: Int get() = (minutes + 59) / 60
}
typealias Rule = (Ticket, Double) -> Double

object ParkingFee {
    private val RULES: Map<String, Rule> = mapOf(
        "base" to { t, _ -> t.hours * 3.0 },
        "ev" to { t, f -> if (t.electric) maxOf(0.0, f - 6.0) else f },
        "cap" to { _, f -> minOf(f, 20.0) },
        "grace" to { t, f -> if (t.minutes <= 15) 0.0 else f },
    )

    fun fee(t: Ticket, plan: List<String>): Double {
        var f = 0.0
        for (name in plan) f = RULES.getValue(name)(t, f)
        return f
    }

    fun charge(t: Ticket): Double {
        return fee(t, listOf("base", "ev", "cap", "grace"))
    }
}
