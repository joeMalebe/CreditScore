package za.co.app.creditscore.ui.doughnut

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import za.co.app.creditscore.model.repository.ICreditScoreRepository
import javax.inject.Inject

@HiltViewModel
class CreditScoreViewModel @Inject constructor(private val creditScoreRepository: ICreditScoreRepository) :
    ViewModel() {

    val viewState = MutableLiveData<DoughnutViewState>()

    fun loadCreditScore() {
        viewState.value = DoughnutViewState.Loading
        CoroutineScope(IO).launch {
            val response = creditScoreRepository.getCreditScoreAsync().await()
            if (response?.creditInfo != null && response.score != 0 && response.targetScore != 0) {
                viewState.postValue(DoughnutViewState.CreditScoreLoaded(response))
            } else {
                viewState.postValue(DoughnutViewState.Error)
            }
        }
    }
}