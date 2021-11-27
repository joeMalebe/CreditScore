package za.co.app.creditscore.ui.credit_report

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import za.co.app.creditscore.R
import za.co.app.creditscore.ui.domain.CreditScore

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

            val creditScore = intent!!.extras!!.get(EXTRA_CREDIT_SCORE)

        }
    }


}