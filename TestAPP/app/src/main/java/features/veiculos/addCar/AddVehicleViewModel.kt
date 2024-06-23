package features.veiculos.addCar

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.network.modules.NewVehicleOutputModel
import com.example.testapp.repository.VehicleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddVehicleViewModel @Inject constructor(
    val vehicleRepository: VehicleRepository
) : ViewModel() {
    val marca = mutableStateOf("")
    val modelo =  mutableStateOf("")
    val matricula=  mutableStateOf("")
    val tipo = mutableStateOf("")

    private val _addingResponseState =
        MutableStateFlow<AddVehicleUIState>(AddVehicleUIState.NotStarted)



    val addingVehicleState: Flow<AddVehicleUIState> = _addingResponseState


    fun createNewVehicle() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _addingResponseState.emit(AddVehicleUIState.Loading)

                val newVehicle = NewVehicleOutputModel(
                    matricula=matricula.value,
                    modelo=modelo.value,
                    marca = marca.value,
                    tipo = tipo.value,
                )


                val result = vehicleRepository.createNewVehicle(newVehicle).collect { result ->
                    if (result == true) {
                        _addingResponseState.emit(AddVehicleUIState.Success)
                    } else {
                        _addingResponseState.emit(AddVehicleUIState.Error)
                    }

                }

            }
        }
    }
}


sealed class AddVehicleUIState() {
    data object Loading : AddVehicleUIState()
    data object Success : AddVehicleUIState()
    data object Error : AddVehicleUIState()
    data object NotStarted : AddVehicleUIState()
}