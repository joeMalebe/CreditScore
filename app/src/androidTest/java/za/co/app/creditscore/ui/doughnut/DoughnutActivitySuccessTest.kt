package za.co.app.creditscore.ui.doughnut

import android.content.Context
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.onNodeWithTag
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
import java.io.InputStreamReader


@HiltAndroidTest
internal class DoughnutActivitySuccessTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val testRule = createEmptyComposeRule()

    lateinit var mockWebServer: MockWebServer

    private lateinit var scenatio: ActivityScenario<DoughnutActivity>

    val context = ApplicationProvider.getApplicationContext<Context>()

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start(8080)
        mockWebServer.url("http://localhost")
        mockWebServer.dispatcher = successDispatcher()

        scenatio = ActivityScenario.launch(DoughnutActivity::class.java)
        scenatio.moveToState(Lifecycle.State.RESUMED)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun appJourney_successCreditReportFlow() {
        testRule.onNodeWithText(context.getString(R.string.clear_score)).assertExists()

        testRule.waitForIdle()

        testRule.onNodeWithText(context.getString(R.string.clear_score)).assertExists()
        testRule.onNodeWithText(context.getString(R.string.your_credit_score_is)).assertExists()
        val doughnut = testRule.onNodeWithTag("doughnut")
        doughnut.assertHasClickAction()
        doughnut.performClick()

        testRule.waitForIdle()

        testRule.onNodeWithText(context.getString(R.string.credit_info)).assertExists()
        testRule.onNodeWithText(context.getString(R.string.short_term)).assertExists()
        testRule.onNodeWithText(context.getString(R.string.long_term)).assertExists()
        testRule.onNodeWithText(context.getString(R.string.credit_used)).assertExists()
    }

    fun successDispatcher(): Dispatcher {
        return object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse().setResponseCode(200).setBody(getJsonContent())
            }
        }
    }

    private fun getJsonContent(): String {
        return InputStreamReader(this.javaClass.classLoader!!.getResourceAsStream("credit_response_success.json")).use { it.readText() }
    }
}