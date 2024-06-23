package com.example.testapp.ui

import android.content.Context
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import androidx.tracing.trace
import com.example.testapp.navigation.TopLevelDestination
import features.authPages.login.navigateToLogin
import features.contraordenacoes.nestedNav.CONTRAORDENACAO_INIT_ROUTE
import features.contraordenacoes.nestedNav.navigateToContraordNested
import features.homepage.HOMEPAGE_ROUTE
import features.homepage.navigateToHomepage
import features.userPage.navigateToUserPage
import features.veiculos.vehicleNestedController.VEHICLE_INIT_ROUTE
import features.veiculos.vehicleNestedController.navigateToVehicleNestedGraph
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun rememberZamovAppState(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    context: Context

): ZamovAppState {
    return remember(
        navController,
        coroutineScope,
        //dropInLauncher
    ) {
        ZamovAppState(
            navController,
            coroutineScope,
        )
    }
}


class ZamovAppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
) {

    val drawerState = mutableStateOf(DrawerState(initialValue = DrawerValue.Closed))
    val showTopBar = mutableStateOf(true)


    fun changeDrawerState() {
        coroutineScope.launch {
            if (drawerState.value.isClosed) {
                drawerState.value.open()

            } else {
                drawerState.value.close()
            }
        }
    }

    fun showTopBar() {
        showTopBar.value = true
    }

    fun hidTopBar() {
        showTopBar.value = false
    }

    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination
        @Composable get() = when (currentDestination?.route) {
            HOMEPAGE_ROUTE -> TopLevelDestination.HOMEPAGE
            CONTRAORDENACAO_INIT_ROUTE -> TopLevelDestination.CONTRAORDENACOES
            VEHICLE_INIT_ROUTE -> TopLevelDestination.VEICULOS
            else -> TopLevelDestination.HOMEPAGE
        }


    /**
     * UI logic for navigating to a top level destination in the app. Top level destinations have
     * only one copy of the destination of the back stack, and save and restore state whenever you
     * navigate to and from it.
     *
     * @param topLevelDestination: The destination the app needs to navigate to.
     */
    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        trace("Navigation: ${topLevelDestination.name}") {
            val topLevelNavOptions = navOptions {
                // Pop up to the start destination of the graph to
                // avoid building up a large stack of destinations
                // on the back stack as users select items
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                // Avoid multiple copies of the same destination when
                // re-selecting the same item
                launchSingleTop = true
                // Restore state when reselecting a previously selected item
                restoreState = true
            }


            when (topLevelDestination) {
                TopLevelDestination.HOMEPAGE -> navController.navigateToHomepage(topLevelNavOptions)

                TopLevelDestination.CONTRAORDENACOES -> navController.navigateToContraordNested(
                    topLevelNavOptions
                )

                TopLevelDestination.VEICULOS -> navController.navigateToVehicleNestedGraph(
                    topLevelNavOptions
                )
                TopLevelDestination.USER_PAGE -> navController.navigateToUserPage(topLevelNavOptions)
            }
        }
    }

    fun forceUserToValidateAuthAgain() {
        trace("force user login again: LOGIN PAGE") {
            val topLevelNavOptions = navOptions {
                popUpTo(navController.graph.findStartDestination().id) {
                    inclusive = true
                    saveState = false
                }
                // Avoid multiple copies of the same destination when
                // re-selecting the same item
                launchSingleTop = true
                // Restore state when reselecting a previously selected item
                restoreState = false
            }
            navController.navigateToLogin(topLevelNavOptions)
        }

    }

    fun sendAuthenticatedUserToHomepage() {
        trace("force user login again: LOGIN PAGE") {
            val topLevelNavOptions = navOptions {
                popUpTo(navController.graph.findStartDestination().id) {
                    inclusive = true
                    saveState = false
                }
                // Avoid multiple copies of the same destination when
                // re-selecting the same item
                launchSingleTop = true
                // Restore state when reselecting a previously selected item
                restoreState = false
            }
            navController.navigateToHomepage(topLevelNavOptions)
        }
    }
}