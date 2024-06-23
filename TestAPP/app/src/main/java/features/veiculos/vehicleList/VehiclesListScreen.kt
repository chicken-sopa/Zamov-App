package features.veiculos.vehicleList

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.testapp.R
import com.example.testapp.data.Vehicle


@Composable
fun VehicleListRoute(
    viewModel: VehicleListViewModel = hiltViewModel(),
    navigateToDetails: (vehicle: Vehicle) -> Unit,
    showTopBar: () -> Unit,
    navigateToAddVehicle: () -> Unit,
) {
    showTopBar()
    val vehicleList = viewModel.vehicleListUiState.collectAsState()
    VehicleListScreen(
        vehicleList = vehicleList,
        navigateToDetails,
        navigateToAddVehicle = navigateToAddVehicle,
        vehicleListType = viewModel.selectedCarListType.value,
        changeListType = viewModel::changeSelectedCarListType
    )

}


@Composable
fun VehicleListScreen(
    vehicleList: State<VehicleListUiState>,
    navigateToDetails: (vehicle: Vehicle) -> Unit,
    navigateToAddVehicle: () -> Unit,
    vehicleListType: VehicleListType,
    changeListType: () -> Unit,
) {
    Box(
        Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopStart
    ) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ShowListTypeButons(currentListType = vehicleListType, changeListType)
            if (vehicleList.value is VehicleListUiState.Loading) {
                CircularProgressIndicator()
            }
            if (vehicleList.value is VehicleListUiState.Sucess) {
                //Text(text = "SUCESS")
                Log.e(
                    "user vehicle borrow",
                    (vehicleList.value as VehicleListUiState.Sucess).userVehicles.userBorrowedVehicles.toString()
                )
                val currentList =
                    if (vehicleListType == VehicleListType.MY_CARS)
                        (vehicleList.value as VehicleListUiState.Sucess).userVehicles.userOwnedVehicles
                    else {
                        (vehicleList.value as VehicleListUiState.Sucess).userVehicles.userBorrowedVehicles
                    }
                Log.e("user vehicle current list", currentList.toString())

                if (currentList != null) {
                    ShowVehicleList(
                        vehicleList = currentList,
                        navigateToDetails = navigateToDetails
                    )
                } else {
                    //showEmptyList()
                }
            }
            //if

            AddCarButton(navigateToAddVehicle = navigateToAddVehicle)
        }
    }
}


@Composable
fun AddCarButton(navigateToAddVehicle: () -> Unit) {
    Row(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            Modifier
                //.background(color = Color(0xFF0D5994), RoundedCornerShape(10.dp))
                .background(color = Color(0xFF0D944E), RoundedCornerShape(20.dp))
                .padding(horizontal = 10.dp, vertical = 10.dp)
                .clickable(onClick = navigateToAddVehicle),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.round_add_circle_24),
                contentDescription = "adicionar novo carro",
                modifier = Modifier
                    .clickable { }
            )
            Text(
                text = "ADICIONAR CARRO",
                fontWeight = FontWeight.Medium,
                color = Color.White
            )

        }
    }
}


@Composable
fun ShowVehicleList(
    vehicleList: List<Vehicle>,
    navigateToDetails: (vehicle:Vehicle) -> Unit,
) {
    LazyColumn(
        Modifier
            //.fillMaxSize(0.9f)
            .fillMaxWidth(0.95f)
            .fillMaxHeight(0.8f)
        //.background(Color(0xFF202020), shape = RoundedCornerShape(10.dp))
        //.background(Color(0xFF060827), shape = RoundedCornerShape(10.dp))
        //.background(UserInfoGrey, shape = RoundedCornerShape(10.dp))
    ) {
        items(items = vehicleList) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 20.dp)
                    .clickable { navigateToDetails(it) }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.sharp_directions_car_24_white),
                    contentDescription = "adicionar novo carro",
                    modifier = Modifier
                        .size(50.dp)
                        .clickable { }
                    /*.background(
                        Color(0xFF1D4B83),
                        shape = RoundedCornerShape(10.dp)
                    )*/
                )

                Column {
                    Text(
                        text = "matricula: ${it.matricula}",
                        modifier = Modifier.padding(
                            vertical = 0.dp,
                            horizontal = 10.dp
                        ),
                        fontWeight = FontWeight.Bold,
                        color = Color.LightGray
                    )
                    Text(
                        text = "marca: ${it.marca}",
                        modifier = Modifier.padding(
                            vertical = 0.dp,
                            horizontal = 10.dp
                        ),
                        fontWeight = FontWeight.Bold,
                        color = Color.LightGray
                    )
                    Text(
                        text = "modelo: ${it.modelo}",
                        modifier = Modifier.padding(
                            vertical = 0.dp,
                            horizontal = 10.dp
                        ),
                        fontWeight = FontWeight.Bold,
                        color = Color.LightGray
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Row(
                        Modifier
                            .padding(end = 0.dp)
                            .background(Color(0xFF0C3D66), shape = RoundedCornerShape(15.dp))

                    ) {
                        Text(
                            text = "ver mais",
                            modifier = Modifier.padding(
                                vertical = 6.dp,
                                horizontal = 7.dp
                            ),
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 15.sp
                        )
                    }

                }
            }
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                Box(
                    Modifier
                        .fillMaxWidth(0.8f)
                        .height(3.dp)
                        //.background(Color(0xFF1D4B83))
                        .background(Color.DarkGray)

                ) {}
            }
        }
    }
}


@Composable
fun ShowListTypeButons(currentListType: VehicleListType, changeListType: () -> Unit) {
    //var checked by remember { mutableStateOf(true) }


    Row(
        Modifier
            .padding(bottom = 10.dp)
            .background(Color.White, shape = RoundedCornerShape(10.dp))
            .padding(all = 2.dp)
            .clickable { changeListType() }
    ) {
        Text(
            modifier = Modifier
                .padding(end = 10.dp)
                .background(
                    if (currentListType == VehicleListType.MY_CARS) Color(0xFF0C3D66) else Color.Transparent,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(all = 5.dp),
            text = "Os meus carros",
            color = if (currentListType == VehicleListType.MY_CARS) Color.White else Color.Black,
            fontWeight = FontWeight.Bold

        )
        Text(
            text = "Carros Emprestados",
            modifier = Modifier
                .padding(end = 10.dp)
                .background(
                    if (currentListType == VehicleListType.BORROWED_CARS) Color(0xFF0C3D66) else Color.Transparent,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(all = 5.dp),
            //.background(Color(0xFF042B4B), shape = RoundedCornerShape(10.dp))
            //.background(Color.White, shape = RoundedCornerShape(10.dp))
            //.border(width = 2.dp, color = Color(0xFF91B3EB), RoundedCornerShape(10.dp))
            color = if (currentListType == VehicleListType.BORROWED_CARS) Color.White else Color.Black,
            fontWeight = FontWeight.Bold
        )


    }

}
