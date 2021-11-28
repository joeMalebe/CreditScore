package za.co.app.creditscore.ui.doughnut

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
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
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class CreditScoreViewModelTest {

    private lateinit var stateList: ArrayList<DoughnutViewState>

    @Mock
    lateinit var credRepository: ICreditScoreRepository
    lateinit var viewModel: CreditScoreViewModel

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        viewModel = CreditScoreViewModel(credRepository)
        stateList = arrayListOf()
    }

    @Test
    fun loadCreditScore_SuccesFullLoad_ReturnCreditScoreLoadedViewState() = runBlocking {
        val score = CreditScore(33, 700, CreditInfo())
        `when`(credRepository.getCreditScoreAsync()).thenReturn(CompletableDeferred((score)))
        viewModel.loadCreditScore()

        viewModel.viewState.observeForever {
            stateList.add(it)
        }
        Thread.sleep(3000)
        Assert.assertEquals(DoughnutViewState.Loading, stateList[0])
        Assert.assertEquals(DoughnutViewState.CreditScoreLoaded(score), stateList[1])
    }

    @Test
    fun loadCreditScore_ErrorFromRepo_ReturnErrorViewState() = runBlocking {
        `when`(credRepository.getCreditScoreAsync()).thenReturn(
            CompletableDeferred(
                CreditScore(
                    creditInfo = null
                )
            )
        )
        viewModel.loadCreditScore()
        viewModel.viewState.observeForever {
            stateList.add(it)
        }

        Thread.sleep(3000)
        Assert.assertEquals(DoughnutViewState.Loading, stateList[0])
        Assert.assertEquals(DoughnutViewState.Error, stateList[1])
    }
}