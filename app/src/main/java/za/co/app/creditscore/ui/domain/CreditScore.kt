package za.co.app.creditscore.ui.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CreditScore(val score: Int, val targetScore: Int, val creditInfo: CreditInfo?) :
    Parcelable
