package za.co.app.creditscore.ui.doughnut

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import za.co.app.creditscore.R
import za.co.app.creditscore.ui.CreditScoreViewModel

@AndroidEntryPoint
class DoughnutActivity : AppCompatActivity() {

    val viewModel: CreditScoreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.viewState.observe(this, { viewState -> renderViewState(viewState) })
        viewModel.loadCreditScore()
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
            }
        }
    }
}