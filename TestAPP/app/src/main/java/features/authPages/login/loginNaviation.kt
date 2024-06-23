package features.authPages.login

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val LOGIN_ROUTE = "login_page"
fun NavController.navigateToLogin(navOptions: NavOptions) = navigate(LOGIN_ROUTE, navOptions)

fun NavGraphBuilder.loginGraph(showTopBar: () -> Unit, sendAuthenticatedUserTOHomepage: () -> Unit,) {
    composable(route = LOGIN_ROUTE) {
        AuthScreenRoute(showTopBar= showTopBar, sendAuthenticatedUserTOHomepage=sendAuthenticatedUserTOHomepage)
    }
}