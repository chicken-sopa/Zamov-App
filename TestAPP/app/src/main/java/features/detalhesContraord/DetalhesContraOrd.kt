package features.detalhesContraord

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.testapp.domain.Contraordenacao
import com.example.testapp.domain.ContraordenacaoState
import com.example.testapp.network.ApiResponse
import com.example.testapp.payment.PaymentOptionsScreen
import com.example.testapp.payment.PaymentTypes
import com.example.testapp.ui.theme.UserInfoGrey
import features.transfer.ResponseTransferPage
import features.transfer.TransferOffenderPage

@Composable
fun DetalhesContraordRoute(
    viewModel: DetalhesViewModel = hiltViewModel(),
    //viewModelPayment: PaymentViewModel = hiltViewModel(),
    hidTopBar: () -> Unit,
    goToBackPage: () -> Unit,
    navigateToPaymentOption: (Float, Int, PaymentTypes) -> Unit,
    reloadContraordDetalhesPage: (Int) -> Unit,


    ) {
    val detalhesUiState = viewModel.detalhesUiState.collectAsState().value
    val requestTransferringUiState = viewModel.requestTransferringUiState

    hidTopBar()
    DetalhesContraOrdScreen(
        viewModel.userId,
        viewModel.imgURLs,
        //viewModel.contraordenacao,
        goToBackPage,
        navigateToPaymentOption,
        detalhesUiState,
        newOffenderId = viewModel.newOffenderId,
        viewModel::makeRequestTransfer,
        viewModel.transferError.value,
        viewModel::answerTransferRequest,
        viewModel.transferApiResult.value,
        reloadContraordDetalhesPage,
        requestTransferringUiState.value,
    )
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetalhesContraOrdScreen(
    contraordID: String,
    imgURLs: List<String>,
    //contraordenacao: Contraordenacao,
    goToBackPage: () -> Unit,
    navigateToPaymentOption: (Float, Int, PaymentTypes) -> Unit,
    detalhesUiState: DetalhesUiState,
    newOffenderId: MutableState<String>,
    requestTransfer: () -> Unit,
    transferError: String,
    answerTransferRequest: (Boolean) -> Unit,
    transferApiResult: ApiResponse<Unit>?,
    reloadContraordDetalhesPage: (Int) -> Unit,
    requestTransferringUiState: RequestTransferringUiState,
) {

    //Top bar of detalhes

    val configuration = LocalConfiguration.current
    val pagerState = rememberPagerState(pageCount = { imgURLs.size })
    val scope = rememberCoroutineScope()
    val showZoomedPhotos = remember { mutableStateOf(false) }


    fun hidZoomedPhotos() {
        showZoomedPhotos.value = false
    }


    val showPayment = remember { mutableStateOf(false) }
    fun launchPaymentScreen() {
        showPayment.value = true
    }

    fun closePayScreen() {
        showPayment.value = false
    }

    val showTransferPage = remember { mutableStateOf(false) }
    fun launchTransferPage() {
        showTransferPage.value = true
    }

    fun closeTransferPage() {
        showTransferPage.value = false
    }

    val showAnswerTransferPage = remember { mutableStateOf(false) }
    fun launchAnswerTransferPage() {
        showAnswerTransferPage.value = true
    }

    fun closeAnswerTransferPage() {
        showAnswerTransferPage.value = false
    }


    //fun showZoomedPhotos(){showZoomedPhotos.value = true}


    ProvideTextStyle(
        value = TextStyle(
            color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Normal
        )
    ) {

        Column(
            modifier = Modifier
                .widthIn(configuration.screenWidthDp.dp)
                .fillMaxSize()
                .background(UserInfoGrey)
                .padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = goToBackPage) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "arrow to go back to list contrOrd",
                        tint = Color.White
                    )
                }
                Text(text = "Detalhes Contraordenação")
                Text(text = "$contraordID")
            }
            if (detalhesUiState is DetalhesUiState.Success) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    item {
                        SmallPhotosCarousel(
                            imgURLs = imgURLs, pagerState = pagerState, scope = scope
                        ) {
                            showZoomedPhotos.value = true
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        InfracaoTitle()
                        Spacer(modifier = Modifier.height(20.dp))
                        InfoContraord(detalhesUiState.contraordenacoes)
                        Spacer(modifier = Modifier.height(20.dp))
                        ActionsContraord(
                            currentContraordState = detalhesUiState.contraordenacoes.estado,
                            ::launchPaymentScreen,
                            ::launchTransferPage,
                            ::launchAnswerTransferPage
                        )
                    }
                }
            }
            if (detalhesUiState is DetalhesUiState.Loading) {
                CircularProgressIndicator()
                Text(text = "A BUSCAR INFORMAÇÃO SOBRE DETALHES")
            }
            if (detalhesUiState is DetalhesUiState.Error) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(0.5f)
                        .background(Color.Gray)
                ) {
                    Text(text = "ERRO A BUSCAR DETALHES CONTRAORDENAÇÃO")
                }
            }


        }
        if (showZoomedPhotos.value) {
            BigPhotosCarousel(
                imgURLs = imgURLs, pagerState = pagerState, scope = scope, ::hidZoomedPhotos
            )
        }
        if (showPayment.value && detalhesUiState is DetalhesUiState.Success) {
            detalhesUiState.contraordenacoes.valorCoima?.let { valorCoima ->
                PaymentOptionsScreen(
                    ::closePayScreen,
                    valorCoima,
                    detalhesUiState.contraordenacoes.nProcesso,
                    navigateToPaymentOption
                )
            }
        }
        if (showTransferPage.value) {
            TransferOffenderPage(
                newOffenderId,
                nProcesso = contraordID.toInt(),
                ::closeTransferPage,
                requestTransfer,
                transferError,
                reloadContraordDetalhesPage,
                requestTransferringUiState
            )

        }
        if (showAnswerTransferPage.value) {
            ResponseTransferPage(
                nProcesso = contraordID.toInt(),
                closeResponsePage = ::closeAnswerTransferPage,
                answerTransferRequest = answerTransferRequest,
                reloadContraordDetalhesPage=reloadContraordDetalhesPage
            )
        }
    }
}

@Composable
fun InfracaoTitle() {
    Column {
        Text(text = "Infração", fontSize = 35.sp)
        Text(text = "Excesso Velocidade", fontSize = 35.sp)
    }
}


@Composable
fun InfoContraord(contraord: Contraordenacao) {
    val statusColor = getStatusColor(contraord.estado)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(statusColor, shape = RoundedCornerShape(10))
            .padding(5.dp)
    ) {

        Box(
            modifier = Modifier
                .background(Color.White, CircleShape)
                .size(20.dp)
                .padding(5.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = "Estado: ${contraord.estado}",
            color = Color.White,
            fontWeight = FontWeight.Medium
        )
    }


    Column {
        Text(text = "Detalhes", fontSize = 25.sp)
        Text(text = "Nº Processo: ${contraord.nProcesso}")
        Text(text = "Localização: Lisboa, rua da sta esperta")
        Text(text = "coima : ${contraord.valorCoima}€")
        Text(text = "Data: ${contraord.dataCometido}")
        Text(text = "Matricula: ${contraord.matricula}")


        /*Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Normas Infringidas", fontSize = 25.sp)
        Text(text = "Limite Velocidade Local: 60 KM/H")
        Text(text = "Velocidade do Infrator: 90KM/H ")
        Text(text = "Distancia Minima: 100M")
        Text(text = "Velocidade Media: 120KM/H  ")
        Text(text = "Tempo Viagem: 20S")
        Text(text = "Velocidade de disparo: 80 Ms")*/

    }
}

/**
 *
 * Ver o padding dentro das açoes
 * pode estar mal
 *
 * **/
@Composable
fun ActionsContraord(
    currentContraordState: ContraordenacaoState,
    launchPayment: () -> Unit,
    launchTransferPage: () -> Unit,
    launchAnswerPage: () -> Unit
) {
    Column {
        Text(text = "Ações", fontSize = 35.sp)
        //Spacer(modifier = Modifier.height(20.dp))


        currentContraordState.listOfActions.forEach { action ->
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(10.dp)
                    .clickable {
                        if (action == ActionsContraord.PAGAR) {
                            launchPayment()
                        }
                        if (action == ActionsContraord.TRANSFERIR) {
                            launchTransferPage()
                        }
                        if (action == ActionsContraord.RESPONDER_PEDIDO_TRANSFERENCIA) {
                            launchAnswerPage()
                        }
                    }) {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = ImageVector.vectorResource(action.icon),
                        action.description,
                        tint = Color.White
                    )
                }

                Spacer(modifier = Modifier.width(15.dp))
                Column {
                    Text(text = action.title, fontSize = 20.sp, fontWeight = FontWeight.Medium)
                    Text(text = action.description)
                }
            }
            //Spacer(modifier = Modifier.height(20.dp))
        }


    }

}


fun getStatusColor(state: ContraordenacaoState): Color {
    return when (state) {
        ContraordenacaoState.INITIAL -> Color(0xFF1F44B1)
        ContraordenacaoState.TRANSFERRING -> Color(0xFFE6C317)
        ContraordenacaoState.TRANSFERRED -> Color(0xFF28C4B2)
        ContraordenacaoState.PROCESSING_PAY -> Color.Yellow
        ContraordenacaoState.PAYED -> Color(0xFF0F8D2F)
        ContraordenacaoState.EXPIRED -> Color.Red
    }
}

@Composable
@Preview
fun DetalhesPreview() {
    //DetalhesContraOrdScreen("1", listOf("HELLO"), {}, )
}

