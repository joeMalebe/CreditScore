package za.co.app.creditscore

import retrofit2.Response
import retrofit2.http.GET
import za.co.app.creditscore.model.CreditScoreModel

interface ICreditScoreApi {

    companion object {
        const val API_BASE_URL = "https://android-interview.s3.eu-west-2.amazonaws.com/"
    }

    @GET("endpoint.json")
    suspend fun getCreditScoreDetails() : Response<CreditScoreModel>
}