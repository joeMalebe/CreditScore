package za.co.app.creditscore.model

import com.squareup.moshi.Json

data class CreditReportInfo (
    val score: Int? = null,
    val scoreBand: Long? = null,
    val clientRef: String? = null,
    val status: String? = null,
    val maxScoreValue: Int? = null,
    val minScoreValue: Long? = null,
    val monthsSinceLastDefaulted: Long? = null,
    val hasEverDefaulted: Boolean? = null,
    val monthsSinceLastDelinquent: Long? = null,
    val hasEverBeenDelinquent: Boolean? = null,
    val percentageCreditUsed: Int? = null,
    val percentageCreditUsedDirectionFlag: Long? = null,
    val changedScore: Long? = null,
    val currentShortTermDebt: Long? = null,
    val currentShortTermNonPromotionalDebt: Long? = null,
    val currentShortTermCreditLimit: Long? = null,
    val currentShortTermCreditUtilisation: Long? = null,
    val changeInShortTermDebt: Long? = null,
    val currentLongTermDebt: Long? = null,
    val currentLongTermNonPromotionalDebt: Long? = null,
    val currentLongTermCreditLimit: Long? = null,
    val currentLongTermCreditUtilisation: Long? = null,
    val changeInLongTermDebt: Long? = null,
    val numPositiveScoreFactors: Long? = null,
    val numNegativeScoreFactors: Long? = null,
    val equifaxScoreBand: Long? = null,
    val equifaxScoreBandDescription: String? = null,
    val daysUntilNextReport: Long? = null
)