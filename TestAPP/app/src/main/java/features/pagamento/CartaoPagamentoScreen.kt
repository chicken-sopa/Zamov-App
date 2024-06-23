package features.pagamento

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.testapp.payment.CardPaymentViewModel
import com.example.testapp.payment.PaymentState
import com.example.testapp.ui.GeneralUI.SimpleOutlinedTextFieldSample
import com.example.testapp.ui.theme.UserInfoGrey


@Composable
fun CartaoPgamentoScreenRoute(
    //viewModel: DetalhesViewModel = hiltViewModel(),
    viewModelPayment: CardPaymentViewModel = hiltViewModel(),
    hidTopBar: () -> Unit,
    goToBackPage: () -> Unit,
    updateScreenAfterPayment: (Int) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    hidTopBar()
    CartaoPagamentoScreen(
        viewModelPayment.valorCoima,
        viewModelPayment.nProcesso,
        viewModelPayment.cardNumber,
        viewModelPayment.cvc,
        viewModelPayment.date,
        viewModelPayment.paymentState.collectAsState(initial = PaymentState.PaymentNotStarted),
        viewModelPayment.paymentError.collectAsState(initial = null),
        viewModelPayment::testPayWithCard,
        updateScreenAfterPayment,
        onShowSnackbar,
        goToBackPage,
    )

}


@Composable
fun CartaoPagamentoScreen(
    valorCoima: Float,
    nProcesso: Int,
    cardNumber: MutableState<String>,
    cvc: MutableState<String>,
    date: MutableState<String>,
    paymentState: State<PaymentState>,
    paymentError: State<String?>,
    payWithCard: () -> Unit,
    updateScreenAfterPayment: (Int) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    goToBackPage: () -> Unit,
) {

    if (paymentState.value is PaymentState.PaymentFinished) {
        updateScreenAfterPayment(nProcesso)
    }


    //if (paymentError.value != null) {
    //onShowSnackbar(paymentError.value!!, null)

    //}


    ProvideTextStyle(
        value = TextStyle(
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal
        )
    ) {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(UserInfoGrey)//Color(0xFF1E1E20))//
                .padding(horizontal = 10.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.Top
        ) {


            Row() {

                IconButton(onClick = goToBackPage) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "arrow to go back to list contrOrd",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))
                Text(text = "Pagamento")

            }
            Spacer(modifier = Modifier.height(60.dp))

            Column(
                modifier = Modifier
                    .border(
                        width = 3.dp,
                        color = Color.White,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(20.dp)
            ) {
                Row() {
                    Box() { Text("Total", fontSize = 25.sp) }
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterEnd
                    ) { Text(text = "${valorCoima}€", fontSize = 25.sp) }
                }
                Row() {
                    Box() { Text("Nº Processo", fontSize = 25.sp) }
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterEnd
                    ) { Text(text = "${nProcesso}", fontSize = 25.sp) }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Text("Pagamento", fontSize = 30.sp)

            Spacer(modifier = Modifier.height(25.dp))

            Box() {
                SimpleOutlinedTextFieldSample(label = "Card Number", text = cardNumber)
            }

            Spacer(modifier = Modifier.height(25.dp))

            Row() {
                Box() {
                    SimpleOutlinedTextFieldSample(
                        label = "MM/AA",
                        widthFraction = 0.4f,
                        text = date
                    )
                }
                Box(
                    contentAlignment = Alignment.CenterEnd,
                    modifier = Modifier
                        .fillMaxWidth()
                    //.background(color = Color.Green)
                ) {
                    SimpleOutlinedTextFieldSample(
                        label = "CVC",
                        widthFraction = 0.4f,
                        lastInput = true,
                        text = cvc
                    )
                }
            }

            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Bottom
            ) {
                if (paymentError.value != null) {
                    ShowPaymentErrorMessage(msg = paymentError.value)
                }
                Button(
                    onClick = { payWithCard() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonColors(
                        containerColor = Color.White,
                        contentColor = UserInfoGrey,
                        disabledContainerColor = Color.LightGray,
                        disabledContentColor = UserInfoGrey
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    if (paymentState.value is PaymentState.PaymentProcessing) {
                        CircularProgressIndicator()
                    } else {
                        Text(
                            text = "Pagar",
                            color = UserInfoGrey,
                            fontWeight = FontWeight.Bold, fontSize = 20.sp
                        )
                    }
                }
            }
        }

    }
}


@Composable
@Preview
fun PagamentoPreview() {
    //CartaoPagamentoScreen(2,2, Mut(PaymentState.PaymentFinished))
}

@Composable
fun ShowPaymentErrorMessage(msg: String?) {
    return Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
            .background(Color(0xFF8A1212), shape = RoundedCornerShape(15))
            .padding(15.dp)
    ) {
        Text(text = msg.toString(), color = Color.White, fontSize = 12.sp)
    }
}