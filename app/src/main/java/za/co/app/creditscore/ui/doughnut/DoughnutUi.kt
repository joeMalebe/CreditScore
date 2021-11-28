package za.co.app.creditscore.ui.doughnut

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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import za.co.app.creditscore.R
import za.co.app.creditscore.ui.doughnut.ui.theme.Typography
import za.co.app.creditscore.ui.models.CreditScore

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
        color = colorResource(
            id = R.color.colorAccent
        )
    )
}

@Preview
@Composable
fun Preview() {
    MainLayout(CreditScore(511, 700, null), {})
}

@Composable
fun Doughnut(creditScore: CreditScore, onClickEvent: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier
            .padding(dimensionResource(id = R.dimen.gutterSpace))
    ) {
        Box(
            contentAlignment = Alignment.Center, modifier = Modifier
                .border(
                    dimensionResource(id = R.dimen.outer_stroke_width), color = Color.Black,
                    CircleShape
                )
                .clickable {
                    onClickEvent()
                }.testTag("doughnut")
        ) {
            ProgressBar(creditScore)
            InnerText(creditScore)
        }
    }
}

@Composable
fun MainLayout(creditScore: CreditScore, onClickEvent: () -> Unit) {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = stringResource(id = R.string.clear_score))
        })
    }, content = {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
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
                )
        ) {

            Doughnut(creditScore = creditScore, onClickEvent)
        }
    })
}

@Composable
@Preview
fun Loading() {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = stringResource(id = R.string.clear_score))
        })
    }, content = {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
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
                )
        ) {

            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    color = colorResource(id = R.color.colorPrimary), modifier = Modifier.size(
                        dimensionResource(id = R.dimen.loader_size)
                    )
                )
            }
        }
    })
}

@Composable
fun Error(onClickEvent: () -> Unit) {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = stringResource(id = R.string.clear_score))
        })
    }, content = {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
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
                )
        ) {

            MaterialTheme {
                Column {
                    AlertDialog(title = {
                        Text(
                            text = stringResource(id = R.string.error_message),
                            style = Typography.h6
                        )
                    }, text = {
                        Text(
                            text = stringResource(id = R.string.please_try_again_later),
                            style = Typography.h6
                        )
                    }, onDismissRequest = {}, confirmButton = {
                        Button(onClick = { onClickEvent() }) {
                            Text(stringResource(R.string.ok))
                        }
                    })
                }
            }
        }
    })
}
