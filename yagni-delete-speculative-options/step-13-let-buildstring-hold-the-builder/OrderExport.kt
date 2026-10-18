data class Order(val id: String, val total: Double, val postalCode: String)
interface OrderRepo { fun today(): List<Order> }
interface Warehouse { fun uploadCsv(csv: String) }
/* application.yml
*/

class OrderExport {
    fun export(orders: List<Order>): String {
        return buildString {
            appendLine("id,total,postalCode")
            for (o in orders) {
                appendLine("${o.id},${o.total},${o.postalCode}")
            }
        }
    }
}

class NightlyJob(
    private val orders: OrderRepo,
    private val warehouse: Warehouse,
) {
    fun run() {
        val csv = OrderExport().export(orders.today())
        warehouse.uploadCsv(csv)
    }
}
