package features.veiculos.vehicleList

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.data.UserVehicles
import com.example.testapp.repository.VehicleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class VehicleListViewModel @Inject constructor(
    vehicleListRepository: VehicleRepository
) : ViewModel() {

    val selectedCarListType = mutableStateOf(VehicleListType.MY_CARS)

    fun changeSelectedCarListType() {
        if (selectedCarListType.value == VehicleListType.MY_CARS)
            selectedCarListType.value = VehicleListType.BORROWED_CARS
        else
            selectedCarListType.value = VehicleListType.MY_CARS
    }

    val vehicleListUiState: StateFlow<VehicleListUiState> =
        vehicleListRepository.getUserVehicles().map {
            if (it == null)
                VehicleListUiState.Error
            else
                VehicleListUiState.Sucess(it)
        }
            .onStart { emit(VehicleListUiState.Loading) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = VehicleListUiState.Loading,
            )

}

sealed class VehicleListUiState {
    data object Loading : VehicleListUiState()
    data class Sucess(val userVehicles: UserVehicles) : VehicleListUiState()
    data object Error : VehicleListUiState()
}

enum class VehicleListType {
    MY_CARS,
    BORROWED_CARS
}