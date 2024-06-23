package com.example.testapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.example.testapp.authentication.AuthRepository
import com.example.testapp.authentication.TokenUiSTate
import com.example.testapp.ui.GeneralUI.UserAuthNotValidAlert
import com.example.testapp.ui.ZamovApp
import com.example.testapp.ui.rememberZamovAppState
import com.example.testapp.ui.theme.TestAPPTheme
import dagger.hilt.android.AndroidEntryPoint
import features.authPages.login.AuthScreenRoute
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @Inject
    lateinit var authRepository: AuthRepository

    @Inject
    lateinit var testViewModel: testViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            TestAPPTheme {
                val appState = rememberZamovAppState(context = applicationContext)
                // A surface container using the 'background' color from the theme

                val tokenState = authRepository.tokenAPIUiState.collectAsState(initial = null)

                if (tokenState.value is TokenUiSTate.Empty) {
                    UserAuthNotValidAlert(onClick = testViewModel::setUserAuthValid)
                    //Text("TEST USER NOT AUTH")
                    //appState.forceUserToValidateAuthAgain()
                    Box(Modifier.background(color=Color.Blue)){
                    AuthScreenRoute(
                        showTopBar = { /*TODO*/ },
                        sendAuthenticatedUserTOHomepage = {})
                    }
                } else if (tokenState.value is TokenUiSTate.Success) {
                    ZamovApp(appState = appState)
                }
                else{
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                        Box(Modifier.background(Color.White)){
                        Text("SEARCHING FOR AUTHENTICARTION", color=Color.Black, fontSize = 20.sp)
                        CircularProgressIndicator()
                        }
                    }
                }

            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.println(Log.WARN, "END", "THE APP WAS DESTROYED")
    }


}


