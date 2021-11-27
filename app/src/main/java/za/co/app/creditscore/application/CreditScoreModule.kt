package za.co.app.creditscore.application

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import za.co.app.creditscore.model.ICreditScoreApi
import za.co.app.creditscore.model.ICreditScoreApi.Companion.API_BASE_URL
import za.co.app.creditscore.model.repository.CreditScoreRepository
import za.co.app.creditscore.model.repository.ICreditScoreRepository
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CreditScoreModule {

    @Provides
    @Singleton
    fun userApiService(): ICreditScoreApi {
        return Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient())
            .build().create(ICreditScoreApi::class.java)
    }

    private fun okHttpClient() = OkHttpClient().newBuilder()
        .callTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun creditScoreRepository(creditScoreApi: ICreditScoreApi): ICreditScoreRepository {
        return CreditScoreRepository(creditScoreApi)
    }
}

