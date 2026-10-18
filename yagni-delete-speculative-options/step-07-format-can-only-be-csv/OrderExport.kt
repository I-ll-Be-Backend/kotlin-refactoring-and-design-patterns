data class Order(val id: String, val total: Double, val postalCode: String)
interface OrderRepo { fun today(): List<Order> }
interface Warehouse { fun uploadCsv(csv: String) }
/* application.yml
export:
  delimiter: ","
  header: true
*/
data class ExportConfig(
    val delimiter: String,
    val header: Boolean,
)

class OrderExport(cfg: ExportConfig) {
    private val delimiter = cfg.delimiter
    private val header = cfg.header

    fun export(orders: List<Order>): String {
        val out = StringBuilder()
        if (header) {
            out.append("id${delimiter}total\n")
        }
        for (o in orders) {
            val row = "${o.id}$delimiter${o.total}"
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
