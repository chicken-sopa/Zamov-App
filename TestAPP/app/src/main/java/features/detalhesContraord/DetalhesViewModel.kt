package features.detalhesContraord

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.domain.Contraordenacao
import com.example.testapp.network.ApiResponse
import com.example.testapp.network.modules.TransferRequestOutputModel
import com.example.testapp.network.modules.TransferResponseOutputModel
import com.example.testapp.repository.DetalhesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetalhesViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val detalhesRepository: DetalhesRepository
) : ViewModel() {
    val testImg1 =
        "https://c02.purpledshub.com/uploads/sites/41/2023/08/Uakari-monkey.jpg?webp=1&w=1200"
    val testImg2 =
        "https://c02.purpledshub.com/uploads/sites/41/2021/03/Blobfish_Kerryn-Parkinson-thumb-0293dac.jpg?webp=1&w=1200"
    val testImg3 =
        "https://c02.purpledshub.com/uploads/sites/41/2022/03/Weird-animals-pink-fairy-armadillo-image-by-Alamy-ad4ef72.jpg?webp=1&w=1200"
    val testImg4 =
        "https://c02.purpledshub.com/uploads/sites/41/2022/03/Weird-animals-the-tarsier-0165ccb.jpg?webp=1&w=1200"

    val newOffenderId = mutableStateOf("")
    val transferError = mutableStateOf("HEELLOOO")
    val transferApiResult = mutableStateOf<ApiResponse<Unit>?>(null)


    val userId: String = checkNotNull(savedStateHandle["contraordenacaoID"])

    val imgURLs = listOf<String>(testImg1, testImg2, testImg3, testImg4)

    val detalhesUiState: StateFlow<DetalhesUiState> =
        detalhesRepository.getContraordenacaoDetalhes(userId.toInt())
            .map<Contraordenacao?, DetalhesUiState> {
                if (it == null) return@map DetalhesUiState.Error
                else
                    return@map DetalhesUiState.Success(it)
            }
            .onStart { emit(DetalhesUiState.Loading) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = DetalhesUiState.Loading,
            )


    val requestTransferringUiState =
        mutableStateOf<RequestTransferringUiState>(RequestTransferringUiState.Loading)


    fun makeRequestTransfer() {
        viewModelScope.launch {
            if (detalhesUiState.value is DetalhesUiState.Success) {
                val transferRequest = TransferRequestOutputModel(
                    newOffenderId.value.toInt(),
                    nPrcesso = (detalhesUiState.value as DetalhesUiState.Success).contraordenacoes.nProcesso
                )
                val response = detalhesRepository.makeRequestTransfer(transferRequest)

                response.collectLatest { value ->
                    requestTransferringUiState.value = value
                }
            }
        }
    }

    fun answerTransferRequest(answer: Boolean) {
        viewModelScope.launch {
            if (detalhesUiState.value is DetalhesUiState.Success) {
                val transferResponse = TransferResponseOutputModel(
                    nPrcesso = (detalhesUiState.value as DetalhesUiState.Success).contraordenacoes.nProcesso,
                    answer = answer
                )
                val response = detalhesRepository.answerTransferRequest(transferResponse)

                if (response is ApiResponse.GenericError) {
                    transferError.value = "ERROR"
                }
                if (response is ApiResponse.NetworkError) {
                    transferError.value = "NETWORK ERROR"
                }

                transferApiResult.value = response
            }
        }
    }
}

sealed class DetalhesUiState {
    data object Loading : DetalhesUiState()
    data class Success(val contraordenacoes: Contraordenacao) : DetalhesUiState()
    data object Error : DetalhesUiState()
}

sealed class RequestTransferringUiState {
    data object Loading : RequestTransferringUiState()
    data object Success : RequestTransferringUiState()
    data object Error : RequestTransferringUiState()
}