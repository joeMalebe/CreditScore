package za.co.app.creditscore.ui.doughnut

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.DimenRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dagger.hilt.android.AndroidEntryPoint
import za.co.app.creditscore.R
import za.co.app.creditscore.ui.CreditScoreViewModel
import za.co.app.creditscore.ui.credit_report.CreditReportActivity
import za.co.app.creditscore.ui.domain.CreditScore

@AndroidEntryPoint
class DoughnutActivity : AppCompatActivity() {

    val viewModel: CreditScoreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.viewState.observe(this, { viewState -> renderViewState(viewState) })
        viewModel.loadCreditScore()
    }

    @Composable
    fun InnerText(creditScore: CreditScore) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Your credit score is", fontSize = 16.sp, textAlign = TextAlign.Center)
            Text(
                text = creditScore.score.toString(),
                fontSize = 72.sp,
                textAlign = TextAlign.Center,
                color = colorResource(
                    id = (R.color.colorAccent)
                ),
                fontWeight = FontWeight.Thin
            )
            Text(
                text = "out of ${creditScore.targetScore}",
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }
    }

    @Composable
    fun ProgressBar(creditScore: CreditScore) {
        CircularProgressIndicator(
            progress = (creditScore.score.toFloat() / creditScore.targetScore.toFloat()),
            Modifier
                .size(dimensionResource(id = R.dimen.progress_bar_size))
                .padding(dimensionResource(id = R.dimen.gutterSpaceHalf)),
            color =  colorResource(
                id = R.color.colorAccent
            )
        )
    }

    @Preview
    @Composable
    fun Preview() {
        MainLayout(CreditScore(511, 700, null))
    }

    @Composable
    fun Doughnut(creditScore: CreditScore) {
        Box(contentAlignment = Alignment.Center , modifier = Modifier
            .padding(dimensionResource(id = R.dimen.gutterSpace))) {
            Box(
                contentAlignment = Alignment.Center, modifier = Modifier
                    .border(
                        dimensionResource(id = R.dimen.outer_stroke_width), color = Color.Black,
                        CircleShape
                    )
                    .clickable {
                        startActivity(
                            CreditReportActivity.getStartIntent(
                                this@DoughnutActivity,
                                creditScore
                            )
                        )
                    }
            ) {
                ProgressBar(creditScore)
                InnerText(creditScore)
            }
        }
    }

    @Composable
    fun MainLayout(creditScore: CreditScore) {
        Scaffold(topBar = {
            TopAppBar(title = {
                Text(text = stringResource(id = R.string.clear_score))
            })
        }, content = {
            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally , modifier = Modifier.fillMaxSize().background(brush = Brush.linearGradient(
                listOf(
                    colorResource(id = R.color.background),
                    colorResource(id = R.color.background2),
                    colorResource(id = R.color.background3),
                    colorResource(id = R.color.background4),
                    colorResource(id = R.color.silver))))){

                Doughnut(creditScore = creditScore)
            }

        })
    }

    @Composable
    @Preview
    fun Loading() {
        Box (modifier = Modifier.fillMaxSize()){
            CircularProgressIndicator(color = colorResource(id = R.color.colorPrimary), modifier = Modifier.size(
                dimensionResource(id = R.dimen.loader_size)))
        }

    }

    private fun renderViewState(viewState: DoughnutViewState) {
        when (viewState) {
            is DoughnutViewState.Loading -> {

            }

            is DoughnutViewState.Error -> {

            }

            is DoughnutViewState.CreditScoreLoaded -> {
                setContent {
                    MainLayout(creditScore = viewState.creditScore)
                }
            }
        }
    }
}