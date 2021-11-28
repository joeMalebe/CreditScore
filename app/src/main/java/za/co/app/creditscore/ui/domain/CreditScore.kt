package za.co.app.creditscore.ui.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CreditScore(val score: Int = 0, val targetScore: Int = 0, val creditInfo: CreditInfo?) :
    Parcelable
