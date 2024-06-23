package features.pagamento

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument


const val CARTAO_PAGAMENTO_ROUTE = "pagmamento_cartao_route/{nProcesso}/{valor}"
fun NavController.navigateToCartaoPagamentoScreen(
    valor: Float,
    nProcesso: Int,
    navOptions: NavOptions
) {
    val newRoute = "pagmamento_cartao_route/${nProcesso}/${valor}"
    navigate(newRoute, navOptions)
}

fun NavGraphBuilder.cartaoPagamentoScreenGraph(
    hidTopBar: () -> Unit,
    goToBackPage: () -> Unit,
    updateScreenAfterPayment: (Int) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    composable(
        route = CARTAO_PAGAMENTO_ROUTE,
        arguments = listOf(
            navArgument("nProcesso") { type = NavType.IntType },
            navArgument("valor") { type = NavType.FloatType })

    ) {
        CartaoPgamentoScreenRoute(
            hidTopBar = hidTopBar,
            goToBackPage = goToBackPage,
            updateScreenAfterPayment = updateScreenAfterPayment,
            onShowSnackbar= onShowSnackbar
        )
    }
}

