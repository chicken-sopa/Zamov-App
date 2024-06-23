package features.veiculos.vehicleDetails

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testapp.data.Vehicle
import com.example.testapp.data.VehicleDriver
import java.time.LocalDateTime


@Composable
fun BorrowDetails(
    vehicleDetails: Vehicle,
    vehicleBorrow: VehicleDriver?,
    navigateToDetailsActions: (VehicleDetailsAction) -> Unit,
    answerInvite: (Boolean) -> Unit,
    cancelInvite: () -> Unit
) {
    val borrowState = getBorrowState(vehicleBorrow, vehicleDetails)  // NOT ERROR
    Column(
        Modifier
            .fillMaxWidth()
            //.fillMaxHeight(0.3f)
            .padding(horizontal = 10.dp)
            .padding(top = 10.dp)
            .background(
                color = getBorrowBackgroundColor(borrowState)/*Color(0xFF0C51B9)*/,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(all = 20.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            Modifier
                //.background(Color(0xFF1389E4), RoundedCornerShape(5.dp))
                //.fillMaxHeight(0.4f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                "Data Emprestimo: ${vehicleBorrow?.dataHoraInicio}",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text(
                "Estado Carro: Não Emprestado",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text(
                "RESPOSTA CONVITE: ${vehicleBorrow?.respostaConvite}",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            ShowButtons(
                navigateToDetailsActions, borrowState, answerInvite, cancelInvite
            )
        }

    }
}


@RequiresApi(Build.VERSION_CODES.O)
fun getBorrowState(vehicleBorrow: VehicleDriver?, vehicleDetails: Vehicle): BorrowState {
    if (vehicleDetails.isUserProperty) {
        when {
            (vehicleBorrow == null) -> return BorrowState.READY_TO_BORROW
            (vehicleBorrow.dataHoraFim >= LocalDateTime.now()) -> return BorrowState.BORROW_ENDED
            (vehicleBorrow.dataHoraFimReal != null) -> return BorrowState.BORROW_ENDED
            (vehicleBorrow.respostaConvite == null) -> return BorrowState.AWAITING_RESPONSE
            (vehicleBorrow.respostaConvite == true) -> return BorrowState.BORROW_ACCEPTED
            (vehicleBorrow.respostaConvite == false) -> return BorrowState.BORROW_DECLINED
        }
    } else {
        when {
            (vehicleBorrow == null) -> return BorrowState.ERROR
            (vehicleBorrow.dataHoraFim >= LocalDateTime.now()) -> return BorrowState.BORROW_ENDED
            (vehicleBorrow.dataHoraFimReal != null) -> return BorrowState.BORROW_ENDED
            (vehicleBorrow.respostaConvite == null) -> return BorrowState.INVITED
            (vehicleBorrow.respostaConvite == true) -> return BorrowState.BORROWING
            (vehicleBorrow.respostaConvite == false) -> return BorrowState.BORROW_DECLINED
        }
    }
    return BorrowState.ERROR
}

@Composable
fun ShowButtons(
    navigateToDetailsActions: (VehicleDetailsAction) -> Unit,
    borrowState: BorrowState,
    answerInvite: (Boolean) -> Unit,
    cancelInvite: () -> Unit
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp).padding(top=10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (borrowState == BorrowState.READY_TO_BORROW
            || borrowState == BorrowState.BORROW_ENDED
            || borrowState == BorrowState.BORROW_DECLINED
        )
            GoToBorrowScreenButton(
                text = "Emprestar Carro",
                navigateToDetailsActions = navigateToDetailsActions
            )
        if (borrowState == BorrowState.INVITED)
        //AnswerBorrowInviteButton(
            AnswerBorrowInviteButton(
                text = "Responder Convite",
                onAnswer = answerInvite
            )
        if (borrowState == BorrowState.AWAITING_RESPONSE || borrowState == BorrowState.BORROW_ACCEPTED || borrowState == BorrowState.BORROWING)
            GoToCancelLoanScreenButton(
                text = "Cancelar Emprestimo",
                onCancel = cancelInvite
            )
        Box(modifier = Modifier.padding(top = 20.dp)) {
            ShowListOfPastBorrowersButton(text = "Registo Users")
        }
    }
}

@Composable
fun GoToBorrowScreenButton(
    text: String,
    navigateToDetailsActions: (VehicleDetailsAction) -> Unit
) {
    Column(
        Modifier
            .background(Color(0xF9D4FDFF), shape = RoundedCornerShape(20.dp))
            //.border(4.dp,  Color(0xF9083880), shape = RoundedCornerShape(20.dp))
            .padding(10.dp)
            .clickable { navigateToDetailsActions(VehicleDetailsAction.BorrowVehicle) },
        verticalArrangement = Arrangement.Center

    ) {
        Row {
            Text(
                text = text,
                color = Color(0xF9083880),
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(start = 0.dp),
                fontSize = 17.sp
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoToCancelLoanScreenButton(
    text: String,
    onCancel: () -> Unit
) {
    val showDialog = remember {
        mutableStateOf(false)
    }
    Column(
        Modifier
            .background(Color(0xF9FFD4D4), shape = RoundedCornerShape(20.dp))
            .padding(5.dp)
            //.border(4.dp,  Color(0xF9083880), shape = RoundedCornerShape(20.dp))

            .clickable { showDialog.value = true },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        //Row {
        Text(
            text = text,
            color = Color(0xF9800808),
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier
                .padding(start = 5.dp),
            //.fillMaxWidth(0.5f),
            fontSize = 17.sp
        )
        //}
    }
    if (showDialog.value) {
        CancelLoanDialog(openDialog = showDialog, onCancel)
    }
}

@Composable
fun AnswerBorrowInviteButton(
    text: String,
    onAnswer: (Boolean) -> Unit
) {
    val showDialog = remember {
        mutableStateOf(false)
    }

    Column(
        Modifier
            .background(Color(0xF9D4FFFF), shape = RoundedCornerShape(20.dp))
            .padding(10.dp)
            //.border(4.dp,  Color(0xF9083880), shape = RoundedCornerShape(20.dp))
            .clickable { showDialog.value = true },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        //Row {
        Text(
            text = text,
            color = Color(0xF9088072),
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier
                .padding(start = 5.dp),
            fontSize = 17.sp
        )
        //}
    }
    if (showDialog.value) {
        AnswerLoanDialog(openDialog = showDialog, onAnswer)
    }
}

@Composable
fun ShowListOfPastBorrowersButton(text: String) {
    Column(
        Modifier
            .background(Color(0xF9FFF9D4), shape = RoundedCornerShape(20.dp))
            //.border(4.dp,  Color(0xF9083880), shape = RoundedCornerShape(20.dp))
            .padding(10.dp), verticalArrangement = Arrangement.Center
    ) {
        Row {
            Text(
                text = text,
                color = Color(0xF9BBB315),
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(start = 0.dp),
                fontSize = 17.sp
            )
        }
    }
}

fun getBorrowBackgroundColor(borrowState: BorrowState): Color {
    return when (borrowState) {
        BorrowState.READY_TO_BORROW -> Color(0xFF365F9C)
        BorrowState.AWAITING_RESPONSE -> Color(0xFF999C36)
        BorrowState.INVITED -> Color(0xFF999C36)
        BorrowState.BORROW_DECLINED -> Color(0xFF9C3656)
        BorrowState.BORROW_ACCEPTED -> Color(0xFF409C36)
        BorrowState.BORROWING -> Color(0xFF409C36)
        BorrowState.BORROW_ENDED -> Color(0xFF9C5636)
        BorrowState.ERROR -> Color(0xFFFA0000)
    }
}

enum class BorrowState() {
    READY_TO_BORROW,
    AWAITING_RESPONSE,
    INVITED,
    BORROW_DECLINED,
    BORROW_ACCEPTED,
    BORROWING,
    BORROW_ENDED,
    ERROR
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CancelLoanDialog(openDialog: MutableState<Boolean>, onCancel: () -> Unit) {
    if (openDialog.value) {
        BasicAlertDialog(
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back
                // button. If you want to disable that functionality, simply use an empty
                // onDismissRequest.
                openDialog.value = false
                //onClick()
            }
        ) {
            Surface(
                color = Color(0xFF26182B),
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                shape = MaterialTheme.shapes.large,
                tonalElevation = AlertDialogDefaults.TonalElevation
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Tem a certeza que quer cancelar o emprestimo do carro",
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Row() {
                        TextButton(
                            onClick = {
                                openDialog.value = false
                                onCancel()
                            },
                            //modifier = Modifier.align(LineHeightStyle.Alignment.Bottom)
                        ) {
                            Text("Confirmar")
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnswerLoanDialog(openDialog: MutableState<Boolean>, onAnswer: (Boolean) -> Unit) {
    if (openDialog.value) {
        BasicAlertDialog(
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back
                // button. If you want to disable that functionality, simply use an empty
                // onDismissRequest.
                openDialog.value = false
                //onClick()
            }
        ) {
            Surface(
                color = Color(0xFF26182B),
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                shape = MaterialTheme.shapes.large,
                tonalElevation = AlertDialogDefaults.TonalElevation
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Quer aceitar convite de emprestimo de carro ",
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Row() {
                        TextButton(
                            onClick = {
                                openDialog.value = false
                                onAnswer(true)
                            },
                            //modifier = Modifier.align(LineHeightStyle.Alignment.Bottom)
                        ) {
                            Text("Aceitar")
                        }
                        TextButton(
                            onClick = {
                                openDialog.value = false
                                onAnswer(false)
                            },
                            //modifier = Modifier.align(LineHeightStyle.Alignment.Bottom)
                        ) {
                            Text("Rejeitar")
                        }
                    }
                }
            }
        }
    }
}