package cl.cruz.economicindicators.data.repository.datasource.remote

import cl.cruz.economicindicators.BuildConfig
import cl.cruz.economicindicators.data.model.remote.EconomicIndicatorsResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

const val BASE_URL = "https://www.mindicador.cl"
const val API_PATH = "/api"

interface EconomicIndicatorsService : RemoteDataSource {

    @GET(API_PATH)
    override suspend fun getEconomicIndicators(): EconomicIndicatorsResponse

    companion object {
        val economicIndicatorsApi: EconomicIndicatorsService by lazy {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            else httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

            return@lazy retrofit.create(EconomicIndicatorsService::class.java)
        }
    }
}