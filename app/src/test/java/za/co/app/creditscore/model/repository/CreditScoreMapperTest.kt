package za.co.app.creditscore.model.repository

import org.junit.Assert
import org.junit.Test
import za.co.app.creditscore.model.CreditReportInfo
import za.co.app.creditscore.model.CreditScoreModel

internal class CreditScoreMapperTest {

    @Test
    fun mapCreditScore_validDataInModel_retunValidCreditScore() {
        val reportInfo = CreditReportInfo(score = 577, maxScoreValue = 700)
        val model = CreditScoreModel(creditReportInfo = reportInfo)
        val creditScore = CreditScoreMapper.mapCreditScore(model)
        Assert.assertEquals(577, creditScore.score)
        Assert.assertEquals(700, creditScore.targetScore)
    }

    @Test
    fun mapCreditScore_nullDataInModel_retunCreditScoreWithDefaultValues() {
        val model = CreditScoreModel()
        val creditScore = CreditScoreMapper.mapCreditScore(model)
        Assert.assertEquals(0, creditScore.score)
        Assert.assertEquals(0, creditScore.targetScore)
    }
}