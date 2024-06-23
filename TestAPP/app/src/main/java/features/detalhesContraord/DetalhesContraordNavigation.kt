package features.detalhesContraord

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.testapp.payment.PaymentTypes

const val CONTRAORDENACAO_DETALHES_ROUTE = "contraordenacao_page_detalhes_route/{contraordenacaoID}"
fun NavController.navigateTodetalhesContraord(contraordenacaoID: Int, navOptions: NavOptions) {
    val newRoute = "contraordenacao_page_detalhes_route/${contraordenacaoID}"
    navigate(newRoute, navOptions)
}

fun NavGraphBuilder.detalhesContraordGraph(
    hidTopBar: () -> Unit,
    goToBackPage: () -> Unit,
    navigateToPaymentOption: (Float, Int, PaymentTypes) -> Unit,
    reloadContraordDetalhesPage: (Int) -> Unit,
) {
    composable(
        route = CONTRAORDENACAO_DETALHES_ROUTE,
        arguments = listOf(navArgument("contraordenacaoID") { type = NavType.StringType })
    ) {
        DetalhesContraordRoute(
            hidTopBar = hidTopBar,
            goToBackPage = goToBackPage,
            navigateToPaymentOption = navigateToPaymentOption,
            reloadContraordDetalhesPage = reloadContraordDetalhesPage
        )
    }
}