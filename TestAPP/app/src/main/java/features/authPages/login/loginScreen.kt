package features.authPages.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.testapp.ui.GeneralUI.CustomBlueButton
import features.authPages.AuthViewModel
import features.authPages.AuthenticationUIState
import features.authPages.signIn.SignInInputPage
import kotlin.reflect.KFunction0

@Composable
fun AuthScreenRoute(
    viewModel: AuthViewModel = hiltViewModel(),
    showTopBar: () -> Unit,
    sendAuthenticatedUserTOHomepage: () -> Unit,
) {
    showTopBar()

    AuthScreen(
        viewModel.tokenAPI.collectAsState(),
        viewModel::citizenLogin,
        viewModel.isLoginIn.value,
        viewModel.testViewModel.userAuthNotValid,
        sendAuthenticatedUserToHomepage = sendAuthenticatedUserTOHomepage,
        isLogin = viewModel.isLogin.value,
        changeAuthPage = viewModel::changeAuthPage
    )
}


@Composable
fun AuthScreen(
    tokenAPIState: State<AuthenticationUIState>,
    updateUserName: (String, String) -> Unit,
    isLoginIn: Boolean,
    userAuthNotValid: MutableState<Boolean>,
    sendAuthenticatedUserToHomepage: () -> Unit,
    isLogin: Boolean,
    changeAuthPage: () -> Unit,

    ) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        if (tokenAPIState.value is AuthenticationUIState.Loading) {
            Column(Modifier.background(Color.White)) {
                Text(text = "Checking if USER is Authenticated")
                CircularProgressIndicator()
            }
        } else if (tokenAPIState.value is AuthenticationUIState.NotAuthenticated) {
            if (isLogin) {
                LoginInputPage(
                    tokenAPI = "NO API TOKEN",
                    userLogin = updateUserName,
                    changeToSignIn = changeAuthPage
                )
            } else {
                SignInInputPage(changeToLogIn = changeAuthPage)
            }
        } else if (tokenAPIState.value is AuthenticationUIState.Authenticated) {
            sendAuthenticatedUserToHomepage()
        }
        if (isLoginIn) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                Text("TRYING TO LOGIN")
                CircularProgressIndicator()

            }
        }

    }

}


@Composable
fun LoginInputPage(
    tokenAPI: String,
    userLogin: (String, String) -> Unit,
    changeToSignIn: () -> Unit
) {
    val userNIF = remember {
        mutableStateOf("")
    }
    val userPass = remember {
        mutableStateOf("")
    }

    Column(Modifier.fillMaxHeight(0.8f), verticalArrangement = Arrangement.SpaceAround) {
        Text(
            text = "LogIn USER",
            fontWeight = FontWeight.SemiBold,
            fontSize = 30.sp
        )

        TextField(
            value = userNIF.value,
            onValueChange = { userNIF.value = it },
            label = { Text("NIF utilizador") },
            shape = RoundedCornerShape(7.dp),
        )
        TextField(
            value = userPass.value,
            onValueChange = { userPass.value = it },
            label = { Text("Password Utilizador") },
            shape = RoundedCornerShape(7.dp),
        )
        Box(modifier = Modifier.background(Color.Yellow)) {
            Text(text = "CURRENT USER: $tokenAPI")
        }
        CustomBlueButton(
            text = "LOGIN",
            onClick = { userLogin(userNIF.value, userPass.value) },
            paddingValue = 15
        )

        Row() {
            Text("Se ja tem conta  ", color = Color.White, fontSize = 16.sp)
            Text(
                "inicie a sessão",
                Modifier.clickable(onClick = changeToSignIn),
                color = Color(0xFF8A71D1),
                fontSize = 16.sp
            )
        }

    }
}