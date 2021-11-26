package za.co.app.creditscore.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import za.co.app.creditscore.model.repository.CreditScoreRepository
import za.co.app.creditscore.model.repository.ICreditScoreRepository
import za.co.app.creditscore.ui.doughnut.DoughnutViewState
import javax.inject.Inject

@HiltViewModel
class CreditScoreViewModel @Inject constructor(private val creditScoreRepository: ICreditScoreRepository) :
    ViewModel() {

    val viewState = MutableLiveData<DoughnutViewState>()

    fun loadCreditScore() {
        viewState.value = DoughnutViewState.Loading
        CoroutineScope(IO).launch {
            val response = creditScoreRepository.getCreditScoreAsync().await()
            if (response != null) {
                viewState.postValue(DoughnutViewState.CreditScoreLoaded(response))
            } else {
                viewState.postValue(DoughnutViewState.Error)
            }
        }
    }
}