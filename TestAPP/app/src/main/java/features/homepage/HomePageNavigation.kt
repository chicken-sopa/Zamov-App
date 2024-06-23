package features.homepage

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val HOMEPAGE_ROUTE = "homepage"
fun NavController.navigateToHomepage(navOptions: NavOptions) = navigate(HOMEPAGE_ROUTE, navOptions)

fun NavGraphBuilder.homepageGraph(showTopBar: () -> Unit,) {
    composable(route = HOMEPAGE_ROUTE) {
        HomepageRoute(showTopBar)
    }
}