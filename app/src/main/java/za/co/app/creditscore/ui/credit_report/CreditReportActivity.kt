package za.co.app.creditscore.ui.credit_report

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import com.google.android.material.snackbar.Snackbar
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

    @Composable
    fun TextWithValue(label: String = "Credit", value: String = "455") {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = dimensionResource(id = R.dimen.gutterSpace))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = label, style = Typography.body1)
                Text(text = value, style = Typography.body1)
            }
        }
    }

    @Composable
    fun CreditScoreSection(creditScore: CreditScore) {
        Column(
        ) {

            Text(text = creditScore.score.toString(), fontSize = 72.sp)
            Row(modifier = Modifier.padding(start = dimensionResource(id = R.dimen.gutterSpaceHalf)
                )) {
                Text(
                    text = "out of", style = Typography.body1, modifier = Modifier.padding(
                        end = dimensionResource(id = R.dimen.off_set_4)
                    )
                )
                Text(
                    text = creditScore.targetScore.toString(),
                    style = Typography.subtitle2,
                    fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = dimensionResource(id = R.dimen.off_set_2))
                )
            }
            if (creditScore.creditInfo?.equifaxScoreBandDescription != null) {
                Text(
                    text = creditScore.creditInfo.equifaxScoreBandDescription,
                    style = Typography.subtitle1,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(
                        top = dimensionResource(
                            id = R.dimen.gutterSpaceHalf
                        ), start = dimensionResource(
                            id = R.dimen.gutterSpaceHalf
                        )
                    )
                )
            }

        }
    }

    @Composable
    fun CreditInfoSection(creditInfo: CreditInfo) {
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.SpaceEvenly) {
            DebtCard(creditInfo.shortTermDebt, "Short Term")
            DebtCard(creditInfo.shortTermDebt, "Long Term")
        }
    }

    @Composable
    private fun DebtCard(debt: Debt, heading: String = "Card title") {
        Column(
            modifier = Modifier.padding(
                dimensionResource(
                    id = R.dimen.gutterSpace
                )
            )
        ) {
            Text(
                text = heading, style = Typography.h6, modifier = Modifier.padding(
                    bottom = dimensionResource(
                        id = R.dimen.gutterSpaceHalf
                    )
                )
            )

            Card() {
                Column(
                    modifier = Modifier.padding(
                        horizontal = dimensionResource(id = R.dimen.gutterSpace)
                    ),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    TextWithValue("Current debt", debt.debt.toString())
                    TextWithValue("Non promotional debt", debt.nonPromotionalDebt.toString())
                    TextWithValue("Credit limit", debt.creditLimit.toString())
                    TextWithValue("Credit utilisation", debt.creditUtilisation.toString())
                }

            }
        }
    }

    @Composable
    fun HeaderSection(creditScore: CreditScore) {
       Row (horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            CreditScoreSection(creditScore = creditScore)
            Doughnut(creditScore = creditScore)
        }

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
            ) {
                ProgressBar(creditScore)
                InnerText(creditScore)
            }
        }
    }

    @Composable
    fun ProgressBar(creditScore: CreditScore) {
        CircularProgressIndicator(
            progress = (creditScore.score.toFloat() / creditScore.targetScore.toFloat()),
            Modifier
                .size(dimensionResource(id = R.dimen.small_progress_bar_size))
                .padding(dimensionResource(id = R.dimen.gutterSpaceHalf)),
            color =  colorResource(
                id = R.color.colorAccent
            )
        )
    }

    @Composable
    fun InnerText(creditScore: CreditScore) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Credit used", style = Typography.subtitle2)
            Text(
                text = "37%",
                style = Typography.subtitle1,
                color = colorResource(
                    id = (R.color.colorAccent)
                ),
                fontWeight = FontWeight.Bold
            )
        }
    }

    @Composable
    fun MainLayout(creditScore: CreditScore) {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    listOf(
                        colorResource(id = R.color.background),
                        colorResource(id = R.color.background2),
                        colorResource(id = R.color.background3),
                        colorResource(id = R.color.background4),
                        colorResource(id = R.color.silver)
                    )
                )
            ), verticalArrangement = Arrangement.Top) {
            HeaderSection(creditScore = creditScore)
            if(creditScore.creditInfo != null) {
                Divider(Modifier.padding(vertical = dimensionResource(id = R.dimen.gutterSpaceHalf)))
                CreditInfoSection(creditInfo = creditScore.creditInfo)
            }
        }
    }

    @Composable
    @Preview
    fun Preview() {
        val creditScore = CreditScore(557, 700, CreditInfo())
        //CreditScoreSection(CreditScore(557, 700, CreditInfo()))
        //CreditInfoSection(creditInfo = CreditInfo())
        //Doughnut(creditScore = CreditScore(557, 700, CreditInfo()))
        //HeaderSection(creditScore)
        Scaffold(topBar = {
            TopAppBar(title = {
                Text(text = stringResource(id = R.string.credit_info))
            })
        }, content = {
            MainLayout(creditScore = creditScore)

        })
    }


}