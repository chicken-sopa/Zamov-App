package features.veiculos.borrowCar

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

const val BORROW_VEHICLE_ROUTE = "borrow_vehicle/{vehicleID}"
fun NavController.navigateToBorrowVehicle(vehicleID: Int, navOptions: NavOptions) {
    val newRoute = "borrow_vehicle/${vehicleID}"
    navigate(newRoute, navOptions)
}

fun NavGraphBuilder.borrowVehicleGraph(
    hidTopBar: () -> Unit,
    goToBackPage: () -> Unit,
) {
    composable(
        route = BORROW_VEHICLE_ROUTE,
        arguments = listOf(navArgument("vehicleID") { type = NavType.IntType })
    ) {
        BorrowVehicleRoute(
            hidTopBar = hidTopBar, goToBackPage = goToBackPage,
        )
    }
}