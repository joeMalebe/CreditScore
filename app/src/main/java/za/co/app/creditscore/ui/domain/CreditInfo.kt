package za.co.app.creditscore.ui.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CreditInfo(val shortTermDebt: Debt, val longTermDebt: Debt, val positiveScoreFactors : Int, val negativeScoreFactors : Int, val equifaxScoreBandDescription : String) : Parcelable
