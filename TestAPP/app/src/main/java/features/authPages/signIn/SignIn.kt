package features.authPages.signIn

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testapp.ui.GeneralUI.CustomBlueButton
import com.example.testapp.ui.theme.UserInfoGrey


@Composable
fun SignInInputPage(
    userSignIn: (String, String) -> Unit = { i: String, x: String -> },
    changeToLogIn: () -> Unit
) {
    val userNIF = remember {
        mutableStateOf("")
    }
    val userPass = remember {
        mutableStateOf("")
    }
    val confirmUserPass = remember {
        mutableStateOf("")
    }
    Box(
        Modifier
            .fillMaxSize()
            .background(color = Color(0xFF1D2E88)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            Modifier
                .fillMaxHeight(0.8f)
                .background(color = UserInfoGrey, shape = RoundedCornerShape(10))
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = "Criar Utilizador",
                fontWeight = FontWeight.SemiBold,
                fontSize = 30.sp,
                color = Color.White
            )

            TextField(
                value = userNIF.value,
                onValueChange = { userNIF.value = it },
                label = { Text("Email utilizador") },
                shape = RoundedCornerShape(7.dp),
            )

            TextField(
                value = userPass.value,
                onValueChange = { userPass.value = it },
                label = { Text("Password Utilizador") },
                shape = RoundedCornerShape(7.dp),
            )

            TextField(
                value = confirmUserPass.value,
                onValueChange = { confirmUserPass.value = it },
                label = { Text("Confirmar Password Utilizador") },
                shape = RoundedCornerShape(7.dp),
                isError = userPass.value != confirmUserPass.value
            )

            CustomBlueButton(
                text = "Inscrever",
                onClick = { userSignIn(userNIF.value, userPass.value) },
                paddingValue = 15
            )
            Row() {
                Text("Se ja tem conta  ", color = Color.White, fontSize = 16.sp)
                Text(
                    "inicie a sessão",
                    Modifier.clickable(onClick= changeToLogIn),
                    color = Color(0xFF8A71D1),
                    fontSize = 16.sp
                )
            }
        }
    }

}