package za.co.app.creditscore.model.repository

import za.co.app.creditscore.model.CreditScoreModel
import za.co.app.creditscore.ui.domain.CreditInfo
import za.co.app.creditscore.ui.domain.CreditScore
import za.co.app.creditscore.ui.domain.Debt

object CreditScoreMapper {
    fun mapCreditScore(creditScoreModel: CreditScoreModel): CreditScore {
        val creditScoreDetails = creditScoreModel.creditReportInfo

        val shortTermDebt = Debt(
            creditScoreDetails?.currentShortTermDebt,
            creditScoreDetails?.currentShortTermNonPromotionalDebt,
            creditScoreDetails?.currentShortTermCreditLimit,
            creditScoreDetails?.currentShortTermCreditUtilisation
        )

        val longTermDebt = Debt(
            creditScoreDetails?.currentLongTermDebt,
            creditScoreDetails?.currentLongTermNonPromotionalDebt,
            creditScoreDetails?.currentLongTermCreditLimit,
            creditScoreDetails?.currentLongTermCreditUtilisation
        )

        val creditInfo = CreditInfo(
            shortTermDebt,
            longTermDebt,
            creditScoreDetails?.numPositiveScoreFactors?.toInt() ?: 0,
            creditScoreDetails?.numNegativeScoreFactors?.toInt() ?: 0,
            creditScoreDetails?.equifaxScoreBandDescription ?: "Unknown",
            creditScoreDetails?.percentageCreditUsed ?: 0
        )

        return CreditScore(
            creditScoreDetails?.score ?: 0,
            creditScoreDetails?.maxScoreValue ?: 0, creditInfo
        )
    }
}