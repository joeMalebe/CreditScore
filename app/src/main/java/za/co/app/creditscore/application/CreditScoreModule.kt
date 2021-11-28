package za.co.app.creditscore.application

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import za.co.app.creditscore.model.ICreditScoreApi
import za.co.app.creditscore.model.repository.CreditScoreRepository
import za.co.app.creditscore.model.repository.ICreditScoreRepository
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
open class CreditScoreModule {

    protected open fun baseUrl(): HttpUrl =
        "https://android-interview.s3.eu-west-2.amazonaws.com/".toHttpUrl()

    @Provides
    @Singleton
    fun userApiService(): ICreditScoreApi {
        return Retrofit.Builder()
            .baseUrl(baseUrl())
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient())
            .build().create(ICreditScoreApi::class.java)
    }

    private fun okHttpClient() = OkHttpClient().newBuilder()
        .callTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .addInterceptor(interceptor())
        .build()

    fun interceptor() = Interceptor() {
        val original = it.request()
        val request = original.newBuilder()
            .header("Connection", "close")
            .header("Accept" ,"application/json")
            .header("Content-Type", "application/json")
            .header("Cache-Control", "no-cache")
            .build();

        it.proceed(request);
    }

    @Provides
    @Singleton
    fun creditScoreRepository(creditScoreApi: ICreditScoreApi): ICreditScoreRepository {
        return CreditScoreRepository(creditScoreApi)
    }
}