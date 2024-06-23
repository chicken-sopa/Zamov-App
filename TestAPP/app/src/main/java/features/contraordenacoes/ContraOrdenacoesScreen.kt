package features.contraordenacoes


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.testapp.data.ContraOrdenacao
import com.example.testapp.network.modules.SimpleContraordenacao
import com.example.testapp.ui.theme.Pink80


@Composable
fun ContraOrdenacaoRoute(
    viewModel: ContraOrdenacoesViewModel = hiltViewModel(),
    onDetalhesClick: (Int) -> Unit,
    showTopBar: () -> Unit,
) {
    showTopBar()
    val userContraordenacao = viewModel.ContraordenacaoUiState.collectAsState()

    ContraOrdenacoesScreen(viewModel, onDetalhesClick, userContraordenacao)
}


@Composable
fun ContraOrdenacoesScreen(
    viewModel: ContraOrdenacoesViewModel,
    onDetalhesClick: (Int) -> Unit,
    userContraordenacao: State<UserContrordUiState>,
) {


    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Contra Ordenacoes1")

        LazyColumn(
            modifier = Modifier.fillMaxHeight(0.9f),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {

            if (userContraordenacao.value is UserContrordUiState.Error) {
                item {
                    Column(
                        Modifier
                            .fillMaxSize(0.5f)
                            .background(Color.White, shape = RoundedCornerShape(15))
                    ) { Text(text = "SEM CONTRAORDENAÇÕES") }
                }
            } else if (userContraordenacao.value is UserContrordUiState.Sucess) {
                items(items = (userContraordenacao.value as UserContrordUiState.Sucess).contraordenacoes) { contraordenacao ->
                    SimpleContraordenacaoNotification(
                        contraOrdenacao = contraordenacao,
                        onDetalhesClick = onDetalhesClick
                    )
                }
            }
            if (userContraordenacao.value is UserContrordUiState.Loading) {
                item {
                    Column(
                        Modifier
                            .fillMaxSize(0.5f)
                            .background(Color.White, shape = RoundedCornerShape(15)),
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(
                            strokeWidth = 10.dp,
                            modifier = Modifier.size(70.dp)
                        )
                        Text(text = "A IR BUSCAR CONTRAORDENAÇOES", fontWeight = FontWeight.Medium)
                    }
                }
            }

        }
    }

}


@Composable
fun ContraOrdenacaoNotification(
    contraOrdenacao: ContraOrdenacao,
    onDetalhesClick: (String) -> Unit
) {
    Surface(
        contentColor = Color.White,
    ) {
        Column(
            Modifier
                .clickable(enabled = true, onClick = { onDetalhesClick(contraOrdenacao.ID) })
                .clip(RoundedCornerShape(15.dp))
                .background(color = Pink80)
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.7f),
            horizontalAlignment = Alignment.CenterHorizontally,
        )
        {
            Text(text = "Nome Condutor : ${contraOrdenacao.user.name}")
            Text(text = "Matricula : ${contraOrdenacao.carro.matricula}")
            Text(text = "Localização : ${contraOrdenacao.location}")
            Text(text = "Data : ${contraOrdenacao.date}")
            Text(text = "Gravidade : ${contraOrdenacao.severity}")
            Text(text = "Valor Multa : ${contraOrdenacao.fine}$")
        }

    }
}

@Composable
fun SimpleContraordenacaoNotification(
    contraOrdenacao: SimpleContraordenacao,
    onDetalhesClick: (Int) -> Unit,
    widthFraction: Float = 0.95f,
    heightFraction: Float = 0.2f
) {
    /*Surface(
        contentColor = Color.White,
    ) {}*/
    Column(
        Modifier
            .background(color = Color(0xFF7E9AFF), shape = RoundedCornerShape(10))
            .clickable(
                enabled = true,
                onClick = { onDetalhesClick(contraOrdenacao.nProcesso) })


            .width(LocalConfiguration.current.screenWidthDp.dp * widthFraction)
            .height(LocalConfiguration.current.screenHeightDp.dp * heightFraction),
        horizontalAlignment = Alignment.CenterHorizontally,
    )
    {
        Text(text = "nProcesso: ${contraOrdenacao.nProcesso}")
        Text(text = "Matricula : ${contraOrdenacao.matricula}")
        //Text(text = "Data : ${contraOrdenacao.dataCometido}")
        Text(text = "Valor Multa : ${contraOrdenacao.valorCoima}$")
        Text(text = "Estado da Infraçao: ${contraOrdenacao.estado}")
    }


}