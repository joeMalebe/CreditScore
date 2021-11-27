package za.co.app.creditscore.ui.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CreditInfo(val shortTermDebt: Debt = Debt(null,null,null,null), val longTermDebt: Debt = Debt(null,null,null,null), val positiveScoreFactors : Int = 0, val negativeScoreFactors : Int = 0, val equifaxScoreBandDescription : String = "Unknown") : Parcelable