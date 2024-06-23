package features.detalhesContraord

import com.example.testapp.R

enum class ActionsContraord(
    val icon: Int,
    val title: String,
    val description: String
) {
    PAGAR(
        icon = R.drawable.baseline_credit_card_24,
        title = "Pagar Coima",
        description = "Pagar e aceitar culpa sobre a contraordenação"
    ),

    CONTESTAR(
        icon = R.drawable.baseline_warning_24,
        title = "Contestar Contraordenação",
        description = "Caso não aceite a culpa sobre a contraordenação pode contestar tendo também que apresentar uma razão"
    ),

    TRANSFERIR(
        icon = R.drawable.baseline_person_add_alt_1_24,
        title = "Transferir Culpa",
        description = "Caso não seja o responsavel pela infração pode indicar o utilizador culpado e transferir a culpa caso este aceite"
    ),

    RESPONDER_PEDIDO_TRANSFERENCIA(
        icon = R.drawable.baseline_send_24,
        title = "Responder Pedido Transferencia Culpa",
        description= "Caso seja responsavel pode aceitar o pedido de transferencia que irá ficar com a responsabilidade e a capacidade de efetuar o pagamento da contraordenacao"
    )

}