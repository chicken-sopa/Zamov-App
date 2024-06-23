package features.contraordenacoes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val CONTRAORDENACAO_ROUTE = "contraordenacao_page_route"
fun NavController.navigateToContraordenacao(navOptions: NavOptions) =
    navigate(CONTRAORDENACAO_ROUTE, navOptions)

fun NavGraphBuilder.contraordenacaoGraph(
    onDetalhesClick: (Int) -> Unit,
    showTopBar: () -> Unit,
) {
    composable(route = CONTRAORDENACAO_ROUTE) {
        ContraOrdenacaoRoute(onDetalhesClick = onDetalhesClick, showTopBar = showTopBar)
    }
}


