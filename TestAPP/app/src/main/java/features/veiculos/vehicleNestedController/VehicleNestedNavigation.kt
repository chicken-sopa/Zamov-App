package features.veiculos.vehicleNestedController

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import features.veiculos.addCar.addVehicleGraph
import features.veiculos.borrowCar.borrowVehicleGraph
import features.veiculos.vehicleDetails.vehicleDetailsGraph
import features.veiculos.vehicleList.VEHICLES_ROUTE
import features.veiculos.vehicleList.vehicleListGraph

class VehicleNestedNavigation {
}

const val VEHICLE_INIT_ROUTE = "vehicle_init_graph"
fun NavController.navigateToVehicleNestedGraph(navOptions: NavOptions) =
    navigate(VEHICLE_INIT_ROUTE, navOptions)

fun NavGraphBuilder.vehiclesInitGraph(
    showTopBar: () -> Unit,
    hidTopBar: () -> Unit,
) {
    composable(route = VEHICLE_INIT_ROUTE) {
        VehicleNestedScreens(showTopBar, hidTopBar)
    }
}


@Composable
fun VehicleNestedScreens(
    showTopBar: () -> Unit,
    hidTopBar: () -> Unit,
    //onShowSnackbar: suspend (String, String?) -> Boolean,
) {

    val state = rememberVehicleNestedNavState()

    NavHost(
        navController = state.navController,
        startDestination = VEHICLES_ROUTE,
    ) {
        vehicleListGraph(
            showTopBar = showTopBar,
            navigateToDetails = state::navigateToDetails,
            navigateToAddVehicle = state::navigateToAddVehicle
        )
        addVehicleGraph(hidTopBar = hidTopBar, goToBackPage = state::goToBackPage, )
        borrowVehicleGraph(hidTopBar, goToBackPage = state::goToBackPage)
        vehicleDetailsGraph(hidTopBar = hidTopBar, goToBackPage = state::goToBackPage,navigateToDetailsActions=state::navigateToVehicleDetailsActions)
    }


}