package features.authPages

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.authentication.AuthRepository
import com.example.testapp.network.modules.CitizenLoginOutputModel
import com.example.testapp.network.modules.CitizenToken
import com.example.testapp.repository.CitizenRepository
import com.example.testapp.testViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    val authRepository: AuthRepository,
    val citizenRepository: CitizenRepository,
    val testViewModel: testViewModel


) : ViewModel() {
    val isLogin = mutableStateOf(true)

    val tokenAPI = authRepository.tokenAPI.map { token ->
        if (token == null) return@map AuthenticationUIState.NotAuthenticated()
        else return@map AuthenticationUIState.Authenticated(token)
    }
        .onStart { emit(AuthenticationUIState.Loading()) }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            AuthenticationUIState.Loading()
        )

    val isLoginIn = mutableStateOf<Boolean>(false)

    fun updateUserToken(citizenToken: CitizenToken) {
        viewModelScope.launch {
            authRepository.saveUserAuthToken(citizenToken)
        }
    }

    /**
     * Change type of authentication page
     * wither LogIn or SignIn
     * **/
    fun changeAuthPage(){
        isLogin.value = !isLogin.value
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun citizenLogin(userNif: String, userPass: String) {
        viewModelScope.launch {
            if (userNif != "" && userPass != "") {
                val loginRequest = CitizenLoginOutputModel(userNif, userPass)
                val result = citizenRepository.makeCitizenLoginRequest(loginRequest)
                if(result is AuthenticationUIState.Authenticated){
                    updateUserToken(result.citizenToken)
                }
            }
            isLoginIn.value = false
        }
    }


}


sealed class AuthenticationUIState {
    class Loading : AuthenticationUIState()
    class Authenticated(val citizenToken: CitizenToken) : AuthenticationUIState()
    class NotAuthenticated : AuthenticationUIState()
    class LoginIn : AuthenticationUIState()
}