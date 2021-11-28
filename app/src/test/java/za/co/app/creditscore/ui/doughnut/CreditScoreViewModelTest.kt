package za.co.app.creditscore.ui.doughnut

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import za.co.app.creditscore.model.repository.ICreditScoreRepository
import za.co.app.creditscore.ui.models.CreditInfo
import za.co.app.creditscore.ui.models.CreditScore

@RunWith(MockitoJUnitRunner::class)
class CreditScoreViewModelTest {

    @Mock
    lateinit var credRepository: ICreditScoreRepository

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun loadCreditScore_SuccesFullLoad_ReturnCreditScoreLoadedViewState() = runBlocking {
        val stateList = arrayListOf<DoughnutViewState>()
        val score = CreditScore(33, 700, CreditInfo())
        val viewModel = CreditScoreViewModel(credRepository)
        `when`(credRepository.getCreditScoreAsync()).thenReturn(CompletableDeferred((score)))

        viewModel.viewState.observeForever {
            stateList.add(it)
        }
        viewModel.loadCreditScore()

        Thread.sleep(1000)
        Assert.assertEquals(DoughnutViewState.Loading, stateList[0])
        Assert.assertEquals(DoughnutViewState.CreditScoreLoaded(score), stateList[1])
    }

    @Test
    fun loadCreditScore_ErrorFromRepo_ReturnErrorViewState() = runBlocking {
        val viewModel = CreditScoreViewModel(credRepository)
        val stateList = arrayListOf<DoughnutViewState>()
        `when`(credRepository.getCreditScoreAsync()).thenReturn(
            CompletableDeferred(
                CreditScore(
                    creditInfo = null
                )
            )
        )

        viewModel.viewState.observeForever {
            stateList.add(it)
        }
        viewModel.loadCreditScore()

        Thread.sleep(1000)
        Assert.assertEquals(DoughnutViewState.Loading, stateList[0])
        Assert.assertEquals(DoughnutViewState.Error, stateList[1])
    }
}