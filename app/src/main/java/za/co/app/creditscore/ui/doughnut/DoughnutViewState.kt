package za.co.app.creditscore.ui.doughnut

import za.co.app.creditscore.ui.domain.CreditScore

interface DoughnutViewState {

    object Loading : DoughnutViewState
    object Error : DoughnutViewState
    data class CreditScoreLoaded(val creditScore: CreditScore) : DoughnutViewState
}
