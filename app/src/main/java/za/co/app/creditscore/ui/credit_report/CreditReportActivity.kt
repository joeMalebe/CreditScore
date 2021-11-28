package za.co.app.creditscore.ui.credit_report

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import za.co.app.creditscore.ui.models.CreditScore

class CreditReportActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_CREDIT_SCORE = "EXTRA_CREDIT_SCORE"

        fun getStartIntent(context: Context, creditScore: CreditScore): Intent {
            return Intent(context, CreditReportActivity::class.java).apply {
                putExtra(EXTRA_CREDIT_SCORE, creditScore)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent.extras != null && intent.extras!!.get(EXTRA_CREDIT_SCORE) != null) {
            val creditScore = intent!!.extras!!.get(EXTRA_CREDIT_SCORE) as CreditScore
            setContent {
                MainLayout(creditScore = creditScore)
            }
        }
    }
}