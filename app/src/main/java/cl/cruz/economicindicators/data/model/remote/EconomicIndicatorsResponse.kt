package cl.cruz.economicindicators.data.model.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EconomicIndicatorsResponse(
    @Json(name = "version") val version: String?,
    @Json(name = "autor") val autor: String?,
    @Json(name = "fecha") val fecha: String?,
    @Json(name = "uf") val uf: EconomicIndicatorResponse?,
    @Json(name = "ivp") val ivp: EconomicIndicatorResponse?,
    @Json(name = "dolar") val dolar: EconomicIndicatorResponse?,
    @Json(name = "dolar_intercambio") val dolarExchange: EconomicIndicatorResponse?,
    @Json(name = "euro") val euro: EconomicIndicatorResponse?,
    @Json(name = "ipc") val ipc: EconomicIndicatorResponse?,
    @Json(name = "utm") val utm: EconomicIndicatorResponse?,
    @Json(name = "imacec") val imacec: EconomicIndicatorResponse?,
    @Json(name = "tpm") val tpm: EconomicIndicatorResponse?,
    @Json(name = "libra_cobre") val copperPound: EconomicIndicatorResponse?,
    @Json(name = "tasa_desempleo") val unemploymentRate: EconomicIndicatorResponse?,
    @Json(name = "bitcoin") val bitcoin: EconomicIndicatorResponse?
)