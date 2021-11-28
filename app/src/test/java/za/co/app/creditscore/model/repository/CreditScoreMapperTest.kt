package za.co.app.creditscore.model.repository

import org.junit.Assert
import org.junit.Test
import za.co.app.creditscore.model.CreditReportInfo
import za.co.app.creditscore.model.CreditScoreModel
import za.co.app.creditscore.ui.models.CreditInfo
import za.co.app.creditscore.ui.models.Debt

internal class CreditScoreMapperTest {

    @Test
    fun mapCreditScore_validDataInModel_returnValidCreditScore() {
        val reportInfo = CreditReportInfo(
            score = 577,
            maxScoreValue = 700,
            currentLongTermCreditLimit = 1232L,
            currentLongTermCreditUtilisation = 300,
            currentLongTermDebt = 6677,
            currentLongTermNonPromotionalDebt = 900,
            currentShortTermCreditLimit = 23333,
            currentShortTermCreditUtilisation = 61111,
            currentShortTermDebt = 866,
            currentShortTermNonPromotionalDebt = 12,
            equifaxScoreBandDescription = "Good",
            numNegativeScoreFactors = 3,
            numPositiveScoreFactors = 1,
            percentageCreditUsed = 76
        )

        val model = CreditScoreModel(creditReportInfo = reportInfo)
        val creditScore = CreditScoreMapper.mapCreditScore(model)
        Assert.assertEquals(577, creditScore.score)
        Assert.assertEquals(700, creditScore.targetScore)

        val info = creditScore.creditInfo
        Assert.assertNotNull(info)
        assertCreditInfo(info!!)
    }

    private fun assertCreditInfo(creditInfo: CreditInfo) {
        Assert.assertEquals("Good", creditInfo.equifaxScoreBandDescription)
        Assert.assertEquals(3, creditInfo.negativeScoreFactors)
        Assert.assertEquals(1, creditInfo.positiveScoreFactors)
        Assert.assertEquals(76, creditInfo.creditPercentageUsed)

        assertDebt(creditInfo.longTermDebt, 1232L, 300, 6677, 900)
        assertDebt(creditInfo.shortTermDebt, 23333, 61111, 866, 12)
    }

    private fun assertDebt(
        debt: Debt, currentTermCreditLimit: Long?,
        currentTermCreditUtilisation: Long?,
        currentTermDebt: Long?,
        currentTermNonPromotionalDebt: Long?
    ) {
        Assert.assertEquals(currentTermCreditLimit, debt.creditLimit)
        Assert.assertEquals(currentTermCreditUtilisation, debt.creditUtilisation)
        Assert.assertEquals(currentTermDebt, debt.debt)
        Assert.assertEquals(currentTermNonPromotionalDebt, debt.nonPromotionalDebt)
    }

    @Test
    fun mapCreditScore_nullDataInModel_returnCreditScoreWithDefaultValues() {
        val model = CreditScoreModel()
        val creditScore = CreditScoreMapper.mapCreditScore(model)
        Assert.assertEquals(0, creditScore.score)
        Assert.assertEquals(0, creditScore.targetScore)

        val info = creditScore.creditInfo
        Assert.assertEquals(0, info?.positiveScoreFactors)
        Assert.assertEquals(0, info?.negativeScoreFactors)
        Assert.assertEquals(0, info?.creditPercentageUsed)
        Assert.assertEquals("Unknown", info?.equifaxScoreBandDescription)
    }
}