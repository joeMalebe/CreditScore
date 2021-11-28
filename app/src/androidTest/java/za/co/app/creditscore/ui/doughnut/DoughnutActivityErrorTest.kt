package za.co.app.creditscore.ui.doughnut

import android.content.Context
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import za.co.app.creditscore.R


@HiltAndroidTest
internal class DoughnutActivityErrorTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    lateinit var mockWebServer: MockWebServer
    val context = ApplicationProvider.getApplicationContext<Context>()

    private lateinit var scenatio: ActivityScenario<DoughnutActivity>

    @get:Rule
    val rule = createEmptyComposeRule()

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start(8080)
        mockWebServer.url("http://localhost")
        mockWebServer.dispatcher = errorDispatcher()

        scenatio = ActivityScenario.launch(DoughnutActivity::class.java)
        scenatio.moveToState(Lifecycle.State.RESUMED)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun appJourney_serviceFailed_DisplayErrorViewStateAndCloseApp() {
        rule.onNodeWithText(context.getString(R.string.clear_score)).assertExists()
        rule.waitForIdle()
        rule.onNodeWithText(context.getString(R.string.error_message)).assertExists()
        rule.onNodeWithText(context.getString(R.string.please_try_again_later)).assertExists()
        val button = rule.onNodeWithText(context.getString(R.string.ok))
        button.assertHasClickAction()
        button.performClick()
    }

    fun errorDispatcher(): Dispatcher {
        return object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when (request.path) {
                    else -> MockResponse().setResponseCode(404)
                }
            }
        }
    }
}


