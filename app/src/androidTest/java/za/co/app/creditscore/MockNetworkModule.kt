package za.co.app.creditscore

import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import za.co.app.creditscore.application.CreditScoreModule
import za.co.app.creditscore.model.ICreditScoreApi

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [CreditScoreModule::class]
)
class MockNetworkModule : CreditScoreModule() {

    override fun baseUrl(): HttpUrl {
        return ICreditScoreApi.API_BASE_URL.toHttpUrl()
    }
}