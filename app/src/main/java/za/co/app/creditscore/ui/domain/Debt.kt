package za.co.app.creditscore.ui.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Debt(val debt: Long?, val nonPromotionalDebt : Long?, val creditLimit : Long?, val creditUtilisation : Long?) : Parcelable
