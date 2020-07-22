package cl.cruz.economicindicators.data.model.remote

data class DolarExchange(
    override val code: String?,
    override val name: String?,
    override val measureUnit: String?,
    override val date: String?,
    override val value: Double?
) : Indicator