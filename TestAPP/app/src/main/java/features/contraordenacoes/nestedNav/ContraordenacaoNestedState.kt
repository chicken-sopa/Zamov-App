package features.contraordenacoes.nestedNav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import androidx.tracing.trace
import com.example.testapp.payment.PaymentTypes
import features.detalhesContraord.ActionsContraord
import features.detalhesContraord.navigateTodetalhesContraord
import features.pagamento.navigateToCartaoPagamentoScreen


@Composable
fun rememberDetalhesContraordNestedNavState(
    navController: NavHostController = rememberNavController()
): ContraordenacaoNestedState {
    return remember(navController) {
        ContraordenacaoNestedState(navController)
    }

}


class ContraordenacaoNestedState(
    val navController: NavHostController,
) {


    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination


    fun navigateToNestedDestination(nestedDestination: ActionsContraord) {
        trace("Navigation: ${nestedDestination.name}") {
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


            when (nestedDestination) {
                ActionsContraord.PAGAR -> TODO("PAGINA PAGAR")
                ActionsContraord.CONTESTAR -> TODO("PAGINA CONTESTAR")
                ActionsContraord.TRANSFERIR -> TODO("PAGINA RELEGAR")
                //ActionsContraord.Detalhes -> TODO("PAGINA DETALHES")
                ActionsContraord.RESPONDER_PEDIDO_TRANSFERENCIA -> TODO("PAGINA PEDIDO TRANSFERENCIA")
            }
        }
    }

    fun navigateToDetalhes(contraordenacaoID: Int) {
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
            navController.navigateTodetalhesContraord(contraordenacaoID, nestedLevelNavOptions)
        }
    }

    fun navigateToPaymentOption(valorCoima: Float, nProcesso: Int, paymentOption: PaymentTypes) {
        trace("Navigation: Payment") {
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
            when (paymentOption) {
                PaymentTypes.Cartao -> navController.navigateToCartaoPagamentoScreen(
                    valorCoima,
                    nProcesso,
                    nestedLevelNavOptions
                )

                PaymentTypes.Mbway -> TODO()
                PaymentTypes.Multibanco -> TODO()
            }
        }
    }

    fun reloadContraordDetalhesPage(contraordenacaoID: Int) {
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
    }

    fun goToBackPage() {
        navController.popBackStack()
    }

}