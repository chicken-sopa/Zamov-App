package com.example.testapp.payment

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PaymentOptionsScreen(
    /*viewModel: PaymentViewModel, //= hiltViewModel(),
    context: Context*/
    closeScreen: () -> Unit,
    valorCoima: Float,
    nProcesso: Int,
    navigateToPaymentOption: (Float, Int, PaymentTypes) -> Unit,
) {
    /*val checkOutConfigs = viewModel.createCheckoutConfigs()

    val getPaymentMethods = viewModel.cardComponentDataFlow.collectAsState()*/
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .clickable(onClick = closeScreen),
        Alignment.BottomCenter
    ) {

        Box(
            modifier = Modifier
                .fillMaxHeight(0.55f)
                .fillMaxWidth()
                .background(
                    Color(0xFF1E1E20),
                    shape = RoundedCornerShape(topStartPercent = 7, topEndPercent = 7)
                )
                .clickable(onClick = { }, enabled = false)
                .padding(start = 40.dp, top = 30.dp),
            contentAlignment = Alignment.TopStart
        ) {
            Column {

                Text(
                    "Detalhes Infração", fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

                Text(
                    text = "VALOR COIMA: ${valorCoima}€",
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp
                )
                Text(
                    text = "nº Processo: $nProcesso ",
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp
                )
                Text(
                    text = "Data cometida: 12/2/2025",
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp
                )


                Text(
                    text = "PAGAMENT0S DISPONIVEIS",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 20.dp)
                )

                CreatePaymentOptions(valorCoima, nProcesso, navigateToPaymentOption)

            }
        }
    }
}


@Composable
fun CreatePaymentOptions(
    valorCoima: Float,
    nProcesso: Int,
    navigateToPaymentOption: (Float, Int, PaymentTypes) -> Unit
) {
    Column(modifier = Modifier.padding(top = 0.dp)) {
        PaymentTypes.entries.forEach {
            Row(
                modifier = Modifier
                    .padding(top = 30.dp, start = 0.dp)
                    .clickable { navigateToPaymentOption(valorCoima, nProcesso, it) },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = it.logo),
                    contentDescription = null, // decorative element
                    //modifier = Modifier.size(it.logoSize.dp)
                )

                Text(
                    text = it.nome,
                    modifier = Modifier.padding(top = 0.dp, start = 20.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W500
                )
            }
        }
    }

}

@Composable
@Preview
fun TestPaymentScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue),
        Alignment.BottomCenter
    ) {
        PaymentOptionsScreen({},
            1.2f,
            2
        ) { _, _, _ -> }
    }
}