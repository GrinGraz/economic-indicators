package cl.cruz.economicindicators.data.repository.datasource.remote

import android.util.Log
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
            Log.d("WebAccess", "Creating retrofit client")
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
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