package features.veiculos.addCar

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val VEHICLE_ADD_ROUTE = "vehicle_add"
fun NavController.navigateToAddVehicle(navOptions: NavOptions) {
    navigate(VEHICLE_ADD_ROUTE, navOptions)
}

fun NavGraphBuilder.addVehicleGraph(
    hidTopBar: () -> Unit,
    goToBackPage: () -> Unit,
) {
    composable(
        route = VEHICLE_ADD_ROUTE,
    ) {
        AddVehicleRoute(
            hidTopBar = hidTopBar, goToBackPage = goToBackPage
        )
    }
}