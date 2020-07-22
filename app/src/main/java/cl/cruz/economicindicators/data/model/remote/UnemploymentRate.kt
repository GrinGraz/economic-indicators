package cl.cruz.economicindicators.data.model.remote

import cl.cruz.economicindicators.data.model.remote.Indicator

data class UnemploymentRate(
    override val code: String?,
    override val name: String?,
    override val measureUnit: String?,
    override val date: String?,
    override val value: Double?
) : Indicator