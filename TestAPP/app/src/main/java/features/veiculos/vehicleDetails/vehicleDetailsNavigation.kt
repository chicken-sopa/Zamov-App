package features.veiculos.vehicleDetails

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.testapp.data.Vehicle

/*const val VEHICLE_DETAILS_ROUTE = "vehicle_details/{vehicleID}"
fun NavController.navigateToVehicleDetails(vehicleID: Int, navOptions: NavOptions) {
    val newRoute = "vehicle_details/${vehicleID}"
    navigate(newRoute, navOptions)
}*/

fun NavController.navigateToVehicleDetails(vehicle: Vehicle, navOptions: NavOptions){
    navigate(vehicle, navOptions)
}


/*fun NavGraphBuilder.vehicleDetailsGraph(
    hidTopBar: () -> Unit,
    goToBackPage: () -> Unit,
    navigateToDetailsActions: (VehicleDetailsAction, Vehicle) -> Unit,
) {
    composable(
        route = VEHICLE_DETAILS_ROUTE,
        arguments = listOf(navArgument("vehicleID") { type = NavType.StringType })
    ) {
        VehicleDetailsRoute(
            hidTopBar = hidTopBar,
            goToBackPage = goToBackPage,
            navigateToDetailsActions = navigateToDetailsActions,
            vehicleDetails = it.toRoute<Vehicle>()
        )
    }
}*/

fun NavGraphBuilder.vehicleDetailsGraph(
    hidTopBar: () -> Unit,
    goToBackPage: () -> Unit,
    navigateToDetailsActions: (VehicleDetailsAction, Vehicle) -> Unit,
) {
    composable<Vehicle>(){
        VehicleDetailsRoute(
            hidTopBar = hidTopBar,
            goToBackPage = goToBackPage,
            navigateToDetailsActions = navigateToDetailsActions,
            vehicleDetails = it.toRoute<Vehicle>()
        )
    }
}

