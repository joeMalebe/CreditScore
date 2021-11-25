package za.co.app.creditscore.model.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.stub
import retrofit2.Response
import za.co.app.creditscore.ICreditScoreApi
import za.co.app.creditscore.model.CreditScoreModel
import za.co.app.creditscore.ui.domain.CreditScore

@RunWith(MockitoJUnitRunner::class)
class CreditScoreRepositoryTest {

    @Mock
    lateinit var creditApi: ICreditScoreApi

    lateinit var repository: ICreditScoreRepository

    @Before
    fun setup() {
        repository = CreditScoreRepository(creditApi)
    }

    @Test
    fun getCreditScoreAsync_200HttpResponse_returnCreditScore() = runBlocking {
        `when`(creditApi.getCreditScoreDetails()).thenReturn(
            Response.success(
                200,
                CreditScoreModel()
            )
        )
        val response = repository.getCreditScoreAsync()
        val creditScore = response.await()
        Assert.assertEquals(0, creditScore?.score)
    }

    @Test
    fun getCreditScoreAsync_500HttpResponse_returnNull() = runBlocking {
        `when`(creditApi.getCreditScoreDetails()).thenReturn(
            Response.error(500, "Stub".toResponseBody())
        )

        val response = repository.getCreditScoreAsync().await()
        Assert.assertNull(response)

    }

}