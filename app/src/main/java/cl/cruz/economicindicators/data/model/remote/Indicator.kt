package cl.cruz.economicindicators.data.model.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
interface Indicator {
    @Json(name = "codigo") val code: String?
    @Json(name = "nombre") val name: String?
    @Json(name = "unidad_medida") val measureUnit: String?
    @Json(name = "fecha") val date: String?
    @Json(name = "valor") val value: Double?
}
