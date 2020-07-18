package cl.cruz.economicindicators.data.model

data class EconomicIndicatorsResponse(
    val version: String?,
    val autor: String?,
    val fecha: String?,
    val uf: Uf?,
    val ivp: Ivp?,
    val dolar: Dolar?,
    val dolarExchange: DolarExchange?,
    val euro: Euro?,
    val ipc: Ipc?,
    val utm: Utm?,
    val imacec: Imacec?,
    val tpm: Tpm?,
    val copperPound: CopperPound?,
    val unemploymentRate: UnemploymentRate?,
    val bitcoin: Bitcoin?
)