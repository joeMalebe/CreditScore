package za.co.app.creditscore.ui.doughnut

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import za.co.app.creditscore.R


@HiltAndroidTest
internal class DoughnutActivityTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var testRule = createAndroidComposeRule<DoughnutActivity>()

    @Test
    fun appJourney_successCreditReportFlow() {
        testRule.onNodeWithText(testRule.activity.getString(R.string.clear_score)).assertExists()
        Thread.sleep(3000)
        testRule.onNodeWithText(testRule.activity.getString(R.string.clear_score)).assertExists()

        testRule.onNodeWithText(testRule.activity.getString(R.string.your_credit_score_is)).assertExists()
        val doughnut = testRule.onNodeWithTag("doughnut")
        doughnut.assertHasClickAction()
        doughnut.performClick()
        Thread.sleep(2000)

        testRule.onNodeWithText(testRule.activity.getString(R.string.credit_info)).assertExists()
        testRule.onNodeWithText(testRule.activity.getString(R.string.short_term)).assertExists()
        testRule.onNodeWithText(testRule.activity.getString(R.string.long_term)).assertExists()
        testRule.onNodeWithText(testRule.activity.getString(R.string.credit_used)).assertExists()
    }
}
