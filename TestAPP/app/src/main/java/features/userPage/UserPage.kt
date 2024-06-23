package features.userPage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.testapp.authentication.TokenUiSTate
import com.example.testapp.data.Citizen
import com.example.testapp.ui.theme.UserInfoGrey
import kotlin.reflect.KFunction0


@Composable
fun UserPageRoute(
    viewModel: UserPageViewModel = hiltViewModel(),
    showTopBar: () -> Unit,
) {
    showTopBar()
    UserPage(
        viewModel.tokenAPIUiState.collectAsState(initial = TokenUiSTate.Loading).value,
        error = viewModel.error.value,
        closeError = viewModel::closeError,
        logout = viewModel::logOut
    )
}


@Composable
fun UserPage(
    tokenState: TokenUiSTate,
    error: Boolean,
    closeError: () -> Unit,
    logout: KFunction0<Unit>
) {
    if (tokenState is TokenUiSTate.Loading) {
        Column(Modifier.background(Color.White)) {
            Text(text = "Checking if USER is Authenticated")
            CircularProgressIndicator()
        }
    } else if (tokenState is TokenUiSTate.Success) {
        UserDetailsPage(tokenState.citizenToken.citizen, error = error, closeError = closeError, logout=logout)
    } else {
        Box() {
            Text(text = "ERRO NOS DETALHES DO UTILIZADOR POR FAVOR TENTE AUTENTICAR OUTRA VEZ")
        }
    }


}

@Composable
fun UserDetailsPage(
    citizen: Citizen,
    error: Boolean,
    closeError: () -> Unit,
    logout: KFunction0<Unit>
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        //.background(color = Color(0xFF2D45C6)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            Modifier
                .padding(vertical = 20.dp)
                .background(color = UserInfoGrey, shape = RoundedCornerShape(10))
                .padding(20.dp)
                .fillMaxWidth(0.9f)
        ) {
            Text(text = "Informações Utilizador", fontSize = 20.sp)
            Text("User Zamov ID: ${citizen.id}", color = Color.White)
            Text("Nome: ${citizen.nome}", color = Color.White)
            Text("CC: ${citizen.cc}", color = Color.White)
            Text("NIF: ${citizen.nif}", color = Color.White)
            Text("Data Nascimento: ${citizen.dataNascimento}", color = Color.White)
            Text("Email: ${citizen.email}", color = Color.White)
            Text("Telemovel: ${citizen.telemovel}", color = Color.White)
        }

        Column(
            Modifier
                .padding(vertical = 20.dp)
                .background(color = UserInfoGrey, shape = RoundedCornerShape(10))
                .padding(20.dp)
                .fillMaxWidth(0.9f)
        ) {
            Text(text = "Detalhes Carta Condução", fontSize = 20.sp)
            Text("Tipo Carta Condução: ${citizen.tipoLicenca}", color = Color.White)
            Text("Numero Carta Condução: ${citizen.numeroLicenca}", color = Color.White)
            Text("Numero de Pontos da Carta: ${citizen.nPontosLicenca}", color = Color.White)
            Text("Data Emissao Carta: ${citizen.dataEmissaoLicenca}", color = Color.White)
            Text("Data Validade Carta: ${citizen.dataValidadeLicenca}", color = Color.White)
        }
        Spacer(modifier = Modifier.height(30.dp))
        LogoutButton(logout=logout)
    }
    if (error) {
        ErrorLogOutDialog(onClick = closeError)
    }
}

@Composable
fun LogoutButton(logout: KFunction0<Unit>) {
    Box(
        modifier = Modifier
            .clickable(onClick = logout)
            .background(color = Color(0xFFE97F7F), shape = RoundedCornerShape(20.dp))
            .padding(vertical = 5.dp, horizontal = 30.dp)
        ,
    ) {
        Text(text = "LOG OUT", color = Color(0xFF990302), fontWeight = FontWeight.SemiBold)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorLogOutDialog(onClick: () -> Unit) {
    BasicAlertDialog(
        onDismissRequest = { onClick() }
    ) {
        Surface(
            modifier = Modifier.wrapContentWidth().wrapContentHeight(),
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Erro na operação de logout por favor tente outra vez"
                )
                Spacer(modifier = Modifier.height(24.dp))
                TextButton(
                    onClick = onClick ,
                ) {
                    Text("Confirm")
                }
            }
        }
    }
}
