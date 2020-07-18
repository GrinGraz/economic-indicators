package cl.cruz.economicindicators.data.model.indicators

interface Indicator {
    val codigo: String?
    val nombre: String?
    val unidad_medida: String?
    val fecha: String?
    val valor: Double?
}
