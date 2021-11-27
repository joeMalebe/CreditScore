package za.co.app.creditscore.ui.credit_report

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import za.co.app.creditscore.R
import za.co.app.creditscore.ui.domain.CreditInfo
import za.co.app.creditscore.ui.domain.CreditScore
import za.co.app.creditscore.ui.domain.Debt
import za.co.app.creditscore.ui.doughnut.ui.theme.Typography

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