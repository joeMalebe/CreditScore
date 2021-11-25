package za.co.app.creditscore.model.repository

import za.co.app.creditscore.model.CreditScoreModel
import za.co.app.creditscore.ui.domain.CreditScore

object CreditScoreMapper {
    fun mapCreditScore(creditScoreModel: CreditScoreModel): CreditScore {
        val creditScoreDetails = creditScoreModel.creditReportInfo
        return CreditScore(
            creditScoreDetails?.score ?: 0,
            creditScoreDetails?.maxScoreValue ?: 0
        )

    }
}