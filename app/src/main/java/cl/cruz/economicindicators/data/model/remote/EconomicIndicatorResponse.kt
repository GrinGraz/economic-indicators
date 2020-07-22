package cl.cruz.economicindicators.data.model.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
open class EconomicIndicatorResponse(
    @Json(name = "codigo") open val code: String? = "",
    @Json(name = "nombre") open val name: String? = "",
    @Json(name = "unidad_medida") open val measureUnit: String? = "",
    @Json(name = "fecha") open val date: String? = "",
    @Json(name = "valor") open val value: Double? = 0.0
)
