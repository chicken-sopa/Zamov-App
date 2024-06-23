package features.contraordenacoes.nestedNav

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import features.contraordenacoes.CONTRAORDENACAO_ROUTE
import features.contraordenacoes.contraordenacaoGraph
import features.detalhesContraord.detalhesContraordGraph
import features.pagamento.cartaoPagamentoScreenGraph


const val CONTRAORDENACAO_INIT_ROUTE = "contraordenacao_nested_page"
fun NavController.navigateToContraordNested(navOptions: NavOptions) =
    navigate(CONTRAORDENACAO_INIT_ROUTE, navOptions)

fun NavGraphBuilder.contraordenacaoInitGraph(
    showTopBar: () -> Unit,
    hidTopBar: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    composable(route = CONTRAORDENACAO_INIT_ROUTE) {
        ContraordenacaoNestedScreens(showTopBar, hidTopBar, onShowSnackbar)
    }
}




@Composable
fun ContraordenacaoNestedScreens(
    showTopBar: () -> Unit,
    hidTopBar: () -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {

    val state = rememberDetalhesContraordNestedNavState()

    NavHost(
        navController = state.navController,
        startDestination = CONTRAORDENACAO_ROUTE,
    ) {
        contraordenacaoGraph(onDetalhesClick = state::navigateToDetalhes, showTopBar)
        detalhesContraordGraph(hidTopBar, state::goToBackPage, state::navigateToPaymentOption, state::reloadContraordDetalhesPage)
        cartaoPagamentoScreenGraph(hidTopBar, state::goToBackPage, state::reloadContraordDetalhesPage, onShowSnackbar)
    }


}