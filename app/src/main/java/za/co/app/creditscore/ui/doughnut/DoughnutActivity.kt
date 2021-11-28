package za.co.app.creditscore.ui.doughnut

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import za.co.app.creditscore.ui.credit_report.CreditReportActivity

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