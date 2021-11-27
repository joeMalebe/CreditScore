package za.co.app.creditscore.ui.domain

data class CreditInfo(val shortTermDebt: Debt, val longTermDebt: Debt, val positiveScoreFactors : Int, val negativeScoreFactors : Int, val equifaxScoreBandDescription : String)
