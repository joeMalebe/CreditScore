package za.co.app.creditscore.ui.doughnut

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import dagger.hilt.android.AndroidEntryPoint
import za.co.app.creditscore.R
import za.co.app.creditscore.ui.CreditScoreViewModel
import za.co.app.creditscore.ui.credit_report.CreditReportActivity
import za.co.app.creditscore.ui.domain.CreditScore
import za.co.app.creditscore.ui.doughnut.ui.theme.Typography

@AndroidEntryPoint
class DoughnutActivity : AppCompatActivity() {

    private val viewModel: CreditScoreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.viewState.observe(this, { viewState -> renderViewState(viewState) })
        viewModel.loadCreditScore()
    }

    private fun renderViewState(viewState: DoughnutViewState) {
        when (viewState) {
            is DoughnutViewState.Loading -> {
                setContent {
                    Loading()
                }
            }

            is DoughnutViewState.Error -> {
                setContent {
                    Error { finish() }
                }
            }

            is DoughnutViewState.CreditScoreLoaded -> {
                setContent {
                    MainLayout(
                        creditScore = viewState.creditScore
                    ) {
                        startActivity(
                            CreditReportActivity.getStartIntent(
                                this,
                                viewState.creditScore
                            )
                        )
                    }
                }
            }
        }
    }
}