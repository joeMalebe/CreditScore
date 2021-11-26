package za.co.app.creditscore.ui.doughnut

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
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
    fun innerText(creditScore: CreditScore) {
        Text(text = getString(R.string.androidx_startup))
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
                    innerText(creditScore = viewState.creditScore)
                }
            }
        }
    }
}