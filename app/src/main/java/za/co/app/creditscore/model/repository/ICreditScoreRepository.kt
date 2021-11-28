package za.co.app.creditscore.model.repository

import kotlinx.coroutines.Deferred
import za.co.app.creditscore.ui.models.CreditScore

interface ICreditScoreRepository {
    suspend fun getCreditScoreAsync() : Deferred<CreditScore?>
}