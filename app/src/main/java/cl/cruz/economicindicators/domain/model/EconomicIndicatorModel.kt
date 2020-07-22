package cl.cruz.economicindicators.domain.model

data class EconomicIndicatorModel(
    val code: String,
    val name: String,
    val measureUnit: String,
    val date: String,
    val value: Double
)
