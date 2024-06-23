package features.veiculos.vehicleNestedController

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import androidx.tracing.trace
import com.example.testapp.data.Vehicle
import features.veiculos.addCar.navigateToAddVehicle
import features.veiculos.borrowCar.navigateToBorrowVehicle
import features.veiculos.vehicleDetails.VehicleDetailsAction
import features.veiculos.vehicleDetails.navigateToVehicleDetails


@Composable
fun rememberVehicleNestedNavState(
    navController: NavHostController = rememberNavController()
): VehicleNestedState {
    return remember(navController) {
        VehicleNestedState(navController)
    }

}

class VehicleNestedState(val navController: NavHostController) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    fun navigateToDetails(vehicle: Vehicle) {
        trace("Navigation: detalhes") {
            val nestedLevelNavOptions = navOptions {
                // Pop up to the start destination of the graph to
                // avoid building up a large stack of destinations
                // on the back stack as users select items
                /*popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }*/
                // Avoid multiple copies of the same destination when
                // re-selecting the same item
                launchSingleTop = true
                // Restore state when reselecting a previously selected item
                restoreState = true
            }
            navController.navigateToVehicleDetails(vehicle, nestedLevelNavOptions)
        }
    }


    /*fun reloadToVehicleDetailsPage(contraordenacaoID: Int) {
        trace("Navigation: detalhes after payment") {
            val nestedLevelNavOptions = navOptions {
                // Pop up to the start destination of the graph to
                // avoid building up a large stack of destinations
                // on the back stack as users select items
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = false
                    inclusive = true
                }
                // Avoid multiple copies of the same destination when
                // re-selecting the same item
                launchSingleTop = true
                // Restore state when reselecting a previously selected item
                restoreState = true
            }
            navController.navigateTodetalhesContraord(contraordenacaoID, nestedLevelNavOptions)

        }
    }*/

    fun navigateToAddVehicle() {
        trace("Navigation: detalhes") {
            val nestedLevelNavOptions = navOptions {
                // Pop up to the start destination of the graph to
                // avoid building up a large stack of destinations
                // on the back stack as users select items
                /*popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }*/
                // Avoid multiple copies of the same destination when
                // re-selecting the same item
                launchSingleTop = true
                // Restore state when reselecting a previously selected item
                restoreState = true
            }
            navController.navigateToAddVehicle(nestedLevelNavOptions)
        }
    }

    fun navigateToVehicleDetailsActions(action: VehicleDetailsAction, vehicle: Vehicle) {
        trace("Navigation: detalhes") {
            val nestedLevelNavOptions = navOptions {
                // Pop up to the start destination of the graph to
                // avoid building up a large stack of destinations
                // on the back stack as users select items
                /*popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }*/
                // Avoid multiple copies of the same destination when
                // re-selecting the same item
                launchSingleTop = true
                // Restore state when reselecting a previously selected item
                restoreState = true
            }
            when (action) {
                VehicleDetailsAction.BorrowVehicle -> navController.navigateToBorrowVehicle(
                    vehicle.id,
                    nestedLevelNavOptions
                )

                else -> navController.navigateToVehicleDetails(vehicle, nestedLevelNavOptions)
            }
        }
    }

    fun goToBackPage() {
        navController.popBackStack()
    }


}

