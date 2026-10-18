data class Order(val id: String, val total: Double, val postalCode: String)
interface OrderRepo { fun today(): List<Order> }
interface Warehouse { fun uploadCsv(csv: String) }
/* application.yml
export:
*/
class ExportConfig

class OrderExport(cfg: ExportConfig) {
    fun export(orders: List<Order>): String {
        val out = StringBuilder()
        out.append("id,total\n")
        for (o in orders) {
            val row = "${o.id},${o.total}"
            out.append(row).append("\n")
        }
        return out.toString()
    }
}

class NightlyJob(
    private val config: ExportConfig,
    private val orders: OrderRepo,
    private val warehouse: Warehouse,
) {
    fun run() {
        val csv = OrderExport(config).export(orders.today())
        warehouse.uploadCsv(csv)
    }
}
