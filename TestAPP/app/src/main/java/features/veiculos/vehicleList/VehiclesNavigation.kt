package features.veiculos.vehicleList

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.testapp.data.Vehicle

const val VEHICLES_ROUTE = "vehicle_list"
fun NavController.navigateToVehicleList(navOptions: NavOptions) =
    navigate(VEHICLES_ROUTE, navOptions)

fun NavGraphBuilder.vehicleListGraph(
    navigateToDetails: (vehicle: Vehicle) -> Unit,
    showTopBar: () -> Unit,
    navigateToAddVehicle: ()->Unit,
) {
    composable(route = VEHICLES_ROUTE) {
        //ContraOrdenacaoRoute(onDetalhesClick = onDetalhesClick, showTopBar = showTopBar)
        VehicleListRoute(
            showTopBar = showTopBar, navigateToDetails = navigateToDetails, navigateToAddVehicle=navigateToAddVehicle

        )
    }
}