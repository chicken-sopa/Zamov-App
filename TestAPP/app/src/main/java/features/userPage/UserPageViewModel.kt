package features.userPage

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.authentication.AuthRepository
import com.example.testapp.authentication.TokenUiSTate
import com.example.testapp.repository.CitizenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserPageViewModel @Inject constructor(
    val authRepository: AuthRepository,
    val citizenRepository: CitizenRepository,
) : ViewModel() {
    val tokenAPIUiState: Flow<TokenUiSTate> = authRepository.tokenAPIUiState
    val error: MutableState<Boolean> = mutableStateOf(false)

    fun logOut() {

        viewModelScope.launch {

            val result = citizenRepository.logoutUser()

            if (result) {
                error.value = false
                authRepository.logoutSession()
            } else {
                error.value = true
            }
        }

    }

    fun closeError(){
        error.value = false
    }

}