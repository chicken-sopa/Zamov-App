package com.example.testapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.example.testapp.ui.ZamovAppState
import features.authPages.login.loginGraph
import features.contraordenacoes.nestedNav.contraordenacaoInitGraph
import features.homepage.HOMEPAGE_ROUTE
import features.homepage.homepageGraph
import features.userPage.userPageGraph
import features.veiculos.vehicleNestedController.vehiclesInitGraph

/**
 * Top-level navigation graph. Navigation is organized as explained at
 * https://d.android.com/jetpack/compose/nav-adaptive
 *
 * The navigation graph defined in this file defines the different top level routes. Navigation
 * within each route is handled using state and Back Handlers.
 */
@Composable
fun ZamovNavHost(
    //navController: NavHostController,
    //onNavigateToDestination: (NiaNavigationDestination, String) -> Unit,
    //onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    startDestination: String = HOMEPAGE_ROUTE,//LOGIN_ROUTE,
    appState: ZamovAppState,
    showTopBar: () -> Unit,
    hidTopBar: () -> Unit,
    onShowSnackbar: suspend (String, String?)-> Boolean,
) {

    NavHost(
        navController = appState.navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        loginGraph(showTopBar, appState::sendAuthenticatedUserToHomepage)
        homepageGraph(showTopBar)
        //contraordenacaoGraph()
        contraordenacaoInitGraph(showTopBar, hidTopBar, onShowSnackbar)
        vehiclesInitGraph(showTopBar, hidTopBar)
        userPageGraph(showTopBar)
    }

}


