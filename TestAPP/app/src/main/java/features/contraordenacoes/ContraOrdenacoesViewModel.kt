package features.contraordenacoes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.network.modules.SimpleContraordenacao
import com.example.testapp.repository.ContraodenacaoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class ContraOrdenacoesViewModel @Inject constructor(
    contraodenacaoRepository: ContraodenacaoRepository
) : ViewModel() {

    /*v+al testUser = User(age = 22, name = "José", licenseNumb = 1234567)
    val testVehicle = Vehicle("23-AA-56", "grey", "Rapi", "jaja", owner = testUser)
    val testContraOrdenacao = ContraOrdenacao(
        date = "1/1/2024",
        location = "Lisboa",
        fine = 500,
        severity = "Grave",
        carro = testVehicle,
        user = testUser,
        ID = "testID",
    )

    val _user = MutableStateFlow(testUser)
    val user = _user.asStateFlow(

    val _contraOrdenacao: MutableStateFlow<List<ContraOrdenacao>?> =
        MutableStateFlow(listOf(testContraOrdenacao))
    val contraOrdenacao = _contraOrdenacao.asStateFlow()

    val _carros: MutableStateFlow<List<Vehicle>?> = MutableStateFlow(null)
    val carros = _carros.asStateFlow()

    var x by mutableStateOf<List<ContraOrdenacao>>(listOf(testContraOrdenacao))
        private set


    fun getUser() {
        this._user.value = testUser
    }

    fun getContraOrdenacao() {
        viewModelScope.launch {
            _contraOrdenacao.value = _contraOrdenacao.value?.plus(testContraOrdenacao)
            x = x + testContraOrdenacao.copy(fine = 999)
        }
    }*/

    val ContraordenacaoUiState: StateFlow<UserContrordUiState> =
        contraodenacaoRepository.getUserContraodenacoes()
            .map<List<SimpleContraordenacao>?, UserContrordUiState> {
                if (it == null) return@map UserContrordUiState.Error
                else
                    return@map UserContrordUiState.Sucess(it)
            }
            .onStart { emit(UserContrordUiState.Loading) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = UserContrordUiState.Loading,
            )

}

sealed class UserContrordUiState {
    data object Loading : UserContrordUiState()
    data class Sucess(val contraordenacoes: List<SimpleContraordenacao>) : UserContrordUiState()
    data object Error : UserContrordUiState()
}