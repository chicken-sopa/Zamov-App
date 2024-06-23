package features.userPage

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val HOMEPAGE_ROUTE = "userPage"
fun NavController.navigateToUserPage(navOptions: NavOptions) = navigate(HOMEPAGE_ROUTE, navOptions)

fun NavGraphBuilder.userPageGraph(showTopBar: () -> Unit,) {
    composable(route = HOMEPAGE_ROUTE) {
        UserPageRoute(showTopBar = showTopBar)
    }
}