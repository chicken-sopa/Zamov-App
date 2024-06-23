package features.veiculos.vehicleDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.testapp.data.Vehicle
import com.example.testapp.data.VehicleDetails
import com.example.testapp.network.modules.AnswerLoanInviteOutputModel
import com.example.testapp.repository.VehicleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VehicleDetailsViewModel @Inject constructor(
    private val vehicleListRepository: VehicleRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _vehicle = MutableStateFlow(savedStateHandle.toRoute<Vehicle>())
    val vehicle = _vehicle.asStateFlow()

    val _vehicleDetailsState: MutableStateFlow<VehicleDetailsUiState> =
        MutableStateFlow(VehicleDetailsUiState.Loading)

    val vehicleDetailsState: StateFlow<VehicleDetailsUiState> = _vehicleDetailsState

    val _actionResultUiState: MutableStateFlow<ActionResultUiState> =
        MutableStateFlow(ActionResultUiState.NotStarted)

    val actionResultUiState: StateFlow<ActionResultUiState> = _actionResultUiState

    init {
        viewModelScope.launch {
            vehicleListRepository.getVehicleDetails(vehicle.value.id)
                .collect { vehicleDetails ->
                    _vehicleDetailsState.value = vehicleDetails
                }
        }
    }




    fun updateVehicleDetails() {
        viewModelScope.launch {
            _vehicleDetailsState.emit(VehicleDetailsUiState.Loading)
            vehicleListRepository.getVehicleDetails(vehicle.value.id)
                .collect { vehicleDetails ->
                    _vehicleDetailsState.value = vehicleDetails
                }
        }
    }

    fun answerInvite(answer: Boolean, loanId: Int) {
        viewModelScope.launch {
            _actionResultUiState.value = ActionResultUiState.Loading
            val answerModel = AnswerLoanInviteOutputModel(inviteAnswer = answer, loanId = loanId)
            val result = vehicleListRepository.answerLoanInvite(answerModel)
            if (result) {
                _actionResultUiState.value = ActionResultUiState.NotStarted
                updateVehicleDetails()

            }
            _actionResultUiState.value = ActionResultUiState.Error

        }


    }

    fun cancelLoan(loanId: Int) {
        viewModelScope.launch {
            _actionResultUiState.value = ActionResultUiState.Loading
            val result = vehicleListRepository.cancelLoan(loanId)
            if (result) {
                _actionResultUiState.value = ActionResultUiState.NotStarted
                updateVehicleDetails()

            }
            _actionResultUiState.value = ActionResultUiState.Error

        }
    }

}

sealed class VehicleDetailsUiState {
    data object Loading : VehicleDetailsUiState()
    data class Success(val vehicleDetails: VehicleDetails) : VehicleDetailsUiState()
    data object Error : VehicleDetailsUiState()
}

sealed class ActionResultUiState {
    data object Loading : ActionResultUiState()
    data object Success : ActionResultUiState()
    data object Error : ActionResultUiState()
    data object NotStarted : ActionResultUiState()
}