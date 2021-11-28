package za.co.app.creditscore.model.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import za.co.app.creditscore.model.ICreditScoreApi
import za.co.app.creditscore.ui.models.CreditScore
import javax.inject.Inject

class CreditScoreRepository @Inject constructor(private val creditScoreApi: ICreditScoreApi) :
    ICreditScoreRepository {
    override suspend fun getCreditScoreAsync(): Deferred<CreditScore?> {
        return CoroutineScope(IO).async {
            val response = creditScoreApi.getCreditScoreDetails()
            if (response.isSuccessful && response.body() != null) {
                CreditScoreMapper.mapCreditScore(response.body()!!)
            } else {
                CreditScore(creditInfo = null)
            }
        }
    }
}