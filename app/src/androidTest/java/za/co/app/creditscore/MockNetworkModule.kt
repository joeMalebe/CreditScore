package za.co.app.creditscore

import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import za.co.app.creditscore.application.CreditScoreModule

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [CreditScoreModule::class]
)
class MockNetworkModule : CreditScoreModule() {

    override fun baseUrl(): HttpUrl {
        return "http://localhost:8080/".toHttpUrl()
    }
}