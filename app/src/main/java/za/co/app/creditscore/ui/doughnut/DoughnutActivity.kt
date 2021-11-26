package za.co.app.creditscore.ui.doughnut

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dagger.hilt.android.AndroidEntryPoint
import za.co.app.creditscore.R
import za.co.app.creditscore.ui.CreditScoreViewModel
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
                    id = (R.color.gold)
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
            color = colorResource(
                id = R.color.gold
            )
        )
    }

    @Preview
    @Composable
    fun Preview() {
        Doughnut(CreditScore(511, 700))
    }

    @Composable
    fun Doughnut(creditScore: CreditScore) {
        Box(modifier = Modifier.padding(dimensionResource(id = R.dimen.gutterSpace))) {
            Box(
                contentAlignment = Alignment.Center, modifier = Modifier.border(
                    dimensionResource(id = R.dimen.outer_stroke_width), color = Color.Black,
                    CircleShape
                )
            ) {
                ProgressBar(creditScore)
                InnerText(creditScore)
            }
        }
    }

    private fun renderViewState(viewState: DoughnutViewState) {
        when (viewState) {
            is DoughnutViewState.Loading -> {
                Log.d("#Main: ", "Loading")
            }

            is DoughnutViewState.Error -> {
                Log.d("#Main: ", "Error")
            }

            is DoughnutViewState.CreditScoreLoaded -> {
                Log.d("#Main: ", "Data Loaded $viewState")
                setContent {
                    Doughnut(creditScore = viewState.creditScore)
                }
            }
        }
    }
}