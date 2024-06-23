package features.veiculos.borrowCar

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.network.modules.NewLoanInviteOM
import com.example.testapp.repository.VehicleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject


@HiltViewModel
class BorrowVehicleViewModel @Inject constructor(
    val vehicleRepository: VehicleRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val vehicleID: Int = checkNotNull(savedStateHandle["vehicleID"])

    private val _inviteLoanResponseState =
        MutableStateFlow<BorrowVehicleUIState>(BorrowVehicleUIState.NotStarted)

    val inviteLoanVehicleState: Flow<BorrowVehicleUIState> = _inviteLoanResponseState

    fun loanVehicle(invitedUserID: String, startTime:LocalDateTime, endTime: LocalDateTime) {

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _inviteLoanResponseState.emit(BorrowVehicleUIState.Loading)

                val newLoanInvite = NewLoanInviteOM(
                    invitedUserId = invitedUserID.toInt(),
                    vehicleID = vehicleID,
                    startTime =startTime.toString(),
                    endTime =endTime.toString(),
                )


                val result = vehicleRepository.createLoanInviteVehicle(newLoanInvite).collect { result ->
                    if (result == true) {
                        _inviteLoanResponseState.emit(BorrowVehicleUIState.Success)
                    } else {
                        _inviteLoanResponseState.emit(BorrowVehicleUIState.Error)
                    }

                }

            }
        }
    }

}

sealed class BorrowVehicleUIState {
    data object Loading : BorrowVehicleUIState()
    data object Success : BorrowVehicleUIState()
    data object Error : BorrowVehicleUIState()
    data object NotStarted : BorrowVehicleUIState()

}
