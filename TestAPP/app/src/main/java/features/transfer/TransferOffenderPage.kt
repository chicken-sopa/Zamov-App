package features.transfer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import features.detalhesContraord.RequestTransferringUiState

@Composable
fun TransferOffenderPage(
    newOffenderText: MutableState<String>,
    nProcesso: Int,
    closeTransferPage: () -> Unit,
    requestTransfer: () -> Unit,
    transferError: String,
    reloadContraordDetalhesPage: (Int) -> Unit,
    requestTransferringUiState: RequestTransferringUiState
) {

    //val requestTransferringUiState =  RequestTransferringUiState.Success//requestTransferringUiStateFlow.collectAsState().value
    //val text = remember { mutableStateOf("") }

    if (requestTransferringUiState is RequestTransferringUiState.Success){
        reloadContraordDetalhesPage(nProcesso)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.9f))
            .clickable(onClick = closeTransferPage),
        Alignment.Center,

        ) {}

    Box(
        modifier = Modifier.fillMaxSize(),
        Alignment.Center,
    ) {
        Column(
            modifier = Modifier
                .background(Color(0xFF4855C9), shape = RoundedCornerShape(10.dp))
                .fillMaxHeight(0.5f)
                .padding(5.dp)
                .fillMaxWidth()
                .clickable(onClick = {}),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Transferir Contraordenção",
                fontSize = 25.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 50.dp)
            )
            Text(text = "Nº Processo: $nProcesso", fontSize = 20.sp, color = Color.White)
            Box(Modifier.padding(vertical = 10.dp)) {
                SimpleOutlinedTextFieldOffender(
                    label = "Offender ID",
                    text = newOffenderText,
                    widthFraction = 0.9f,
                    lastInput = true,
                    transferError = transferError,
                    isError = false//requestTransferringUiState is RequestTransferringUiState.Error
                )
            }
            Button(
                onClick = requestTransfer,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F8D2F))
            ) {
                Text(text = "Request Transfer")
            }
        }
    }

}


@Composable
fun SimpleOutlinedTextFieldOffender(
    label: String,
    widthFraction: Float = 1f,
    lastInput: Boolean = false,
    text: MutableState<String>,
    transferError: String,
    isError: Boolean
) {
    //var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text.value,
        onValueChange = { text.value = it },
        label = { Text(label) },
        shape = RoundedCornerShape(7.dp),
        modifier = Modifier//.fillMaxWidth(widthFraction)
            .width(LocalConfiguration.current.screenWidthDp.dp * widthFraction),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal,
            imeAction = if (lastInput) ImeAction.Done else ImeAction.Next,
        ),
        colors = OutlinedTextFieldDefaults.colors().copy(
            unfocusedTextColor = Color.White,
            focusedTextColor = Color.White,
            //errorLabelColor = Color.Red
        ),
        isError =isError ,
        supportingText = {
            //if (transferError != "") {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "${transferError}",
                    color = Color(0xFFB10C0C)
                )
            //}
        }
    )
}