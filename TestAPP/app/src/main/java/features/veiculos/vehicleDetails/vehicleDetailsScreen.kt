package features.veiculos.vehicleDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.testapp.data.Vehicle
import com.example.testapp.network.modules.SimpleContraordenacao

@Composable
fun VehicleDetailsRoute(
    viewModel: VehicleDetailsViewModel = hiltViewModel(),
    hidTopBar: () -> Unit,
    goToBackPage: () -> Unit,
    navigateToDetailsActions: (VehicleDetailsAction, Vehicle) -> Unit,
    vehicleDetails: Vehicle,
) {
    hidTopBar()
    VehicleDetailsScreen(
        vehicleDetails = viewModel.vehicleDetailsState.collectAsState(),
        goToBackPage = goToBackPage,
        navigateToDetailsActions = { action: VehicleDetailsAction ->
            navigateToDetailsActions(
                action,
                vehicleDetails
            )
        },
        viewModel::cancelLoan,
        viewModel::answerInvite,
        reloadVehicleDetails = viewModel::updateVehicleDetails
    )
}

@Composable
fun VehicleDetailsScreen(
    vehicleDetails: State<VehicleDetailsUiState>,
    goToBackPage: () -> Unit,
    navigateToDetailsActions: (VehicleDetailsAction) -> Unit,
    cancelInvite: (Int) -> Unit,
    answerInvite: (Boolean, Int) -> Unit,
    reloadVehicleDetails: () -> Unit,
) {
    ProvideTextStyle(
        value = TextStyle(
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal
        )
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .background(color = Color(0xFF1D1B1B)),

            ) {

            if (vehicleDetails.value is VehicleDetailsUiState.Loading) {

                Text(text = "LOADING USER DETAILS", fontWeight = FontWeight.Bold, fontSize = 25.sp)
                CircularProgressIndicator()

            } else if (vehicleDetails.value is VehicleDetailsUiState.Success) {
                TopColumn(goToBackPage)
                LazyColumn {
                    item {
                        VehicleInfo((vehicleDetails.value as VehicleDetailsUiState.Success).vehicleDetails.vehicle)
                    }
                    item {
                        BorrowDetails(
                            (vehicleDetails.value as VehicleDetailsUiState.Success).vehicleDetails.vehicle,
                            (vehicleDetails.value as VehicleDetailsUiState.Success).vehicleDetails.userDriver,
                            navigateToDetailsActions,
                            cancelInvite = {
                                cancelInvite(
                                    (vehicleDetails.value as VehicleDetailsUiState.Success).vehicleDetails.userDriver!!.id
                                )
                            },
                            answerInvite = {
                                answerInvite(
                                    it,
                                    (vehicleDetails.value as VehicleDetailsUiState.Success).vehicleDetails.userDriver!!.id
                                )
                            },
                        )
                    }
                    item {
                        PendingInfrations(
                            pendingInfractions = (vehicleDetails.value as VehicleDetailsUiState.Success).vehicleDetails.pendingContraord,
                            test = reloadVehicleDetails
                        )
                    }
                }


            } else {
                Text(text = "ERROR NOT ABLE TO GET VEHICLE DETAILS PLEASE TRY AGAIN")
            }

        }

    }


}


@Composable
private fun VehicleInfo(vehicleDetails: Vehicle) {
    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.3f)
            .padding(horizontal = 10.dp)
            .background(color = Color.DarkGray, shape = RoundedCornerShape(10.dp))
            .padding(horizontal = 15.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text("Informações sobre carro", fontSize = 25.sp, fontWeight = FontWeight.ExtraBold)
        Column(
            modifier = Modifier
                .padding(vertical = 10.dp)
                .fillMaxHeight(), verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text("Dono: ${vehicleDetails.proprietarioNome}")
            Text("matricula: ${vehicleDetails.matricula}")
            Text("marca: ${vehicleDetails.marca}")
            Text("modelo ${vehicleDetails.modelo}")
            Text("tipo: ${vehicleDetails.tipo}")
            Text("TEST USER DONO : ${vehicleDetails.isUserProperty}")
        }
    }
}

@Composable
private fun TopColumn(goToBackPage: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 5.dp)
    ) {

        IconButton(onClick = goToBackPage) {

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "arrow to go back to user vehicle list ",
                tint = Color.White,
                modifier = Modifier
                    .background(
                        color = Color.Transparent,
                        shape = RoundedCornerShape(100)
                    )
                    .clickable(onClick = goToBackPage)
            )
        }

        Spacer(modifier = Modifier.width(20.dp))
        Text(text = "Detalhes Carro")
    }
}


@Composable
private fun PendingInfrations(pendingInfractions: List<SimpleContraordenacao>, test: () -> Unit) {
    Column(
        Modifier
            .clickable { test() }
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(horizontal = 10.dp, vertical = 10.dp)
            .background(color = Color.DarkGray, shape = RoundedCornerShape(10.dp))
            .padding(all = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Contraordenações Pendentes", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Box(Modifier.fillMaxSize().padding(vertical=10.dp), contentAlignment = Alignment.Center) {
            //ContraordCard(SimpleContraordenacao("INITIAL", 1, "ABC-123", 123f))
            ContraordenacaoPager(pendingInfractions = pendingInfractions)
        }
    }
}

@Composable
fun ContraordenacaoPager(pendingInfractions: List<SimpleContraordenacao>) {
    val pagerState = rememberPagerState(pageCount = { pendingInfractions.size })
    val scope = rememberCoroutineScope()
    Column {
        HorizontalPager(
            //pageCount = animals.size,
            state = pagerState,
            key = { pendingInfractions[it].nProcesso },
            pageSize = PageSize.Fill
        ) { index ->
            ContraordCard(pendingInfractions[index])
        }
        Spacer(modifier = Modifier.height(20.dp))
        DotIndicators(
            pageCount = pagerState.pageCount,
            pagerState = pagerState,
            //modifier = Modifier
        )
    }

}

@Composable
fun DotIndicators(
    pageCount: Int,
    pagerState: PagerState,
    //modifier: Modifier
) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        repeat(pageCount) { iteration ->
            val color = if (pagerState.currentPage == iteration) Color.Black else Color.LightGray
            Box(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(15.dp)

            )
        }

    }
}

@Composable
fun ContraordCard(contraOrdenacao: SimpleContraordenacao) {
    Box(Modifier.fillMaxSize()) {
        ElevatedCard(
            //onClick = { /* Do something */ },
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f),
            colors = CardDefaults.elevatedCardColors()
                .copy(containerColor = Color(0xfF1388E3)) //Color(0xFF1388E3))
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f)
                    .padding(vertical = 0.dp)
                    .padding(start = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "Contraordenção",
                    //Modifier.align(Alignment.Center),
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 27.sp
                )
                Column(
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(1f),
                    verticalArrangement = Arrangement.SpaceAround,
                ) {
                    Text(
                        text = "nProcesso: ${contraOrdenacao.nProcesso}",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Matricula : ${contraOrdenacao.matricula}",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Data : 22/06/2024",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Valor Multa : ${contraOrdenacao.valorCoima}$",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Estado da Infraçao: ${contraOrdenacao.estado}",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )

                }

            }
        }
    }
}