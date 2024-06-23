package com.example.testapp.domain

import features.detalhesContraord.ActionsContraord

data class Contraordenacao(
    val nAuto: Int,

    val nProcesso: Int,

    val dataCometido: String,

    val descricao: String,

    val sancaoDescricao: String?,

    val sancaoDataLimite: String?,

    val estado: ContraordenacaoState,

    val matricula: String,

    val valorCoima: Float?
)


enum class ContraordenacaoState(val listOfActions: List<ActionsContraord>) {
    INITIAL(
        listOfActions = listOf(
            ActionsContraord.PAGAR,
            ActionsContraord.CONTESTAR,
            ActionsContraord.TRANSFERIR
        )
    ),
    TRANSFERRING(
        listOf(
            ActionsContraord.PAGAR,
            ActionsContraord.CONTESTAR,
            ActionsContraord.RESPONDER_PEDIDO_TRANSFERENCIA
        )
    ),

    TRANSFERRED(
        listOf(
            ActionsContraord.PAGAR,
            ActionsContraord.CONTESTAR,
        )
    ),

    PROCESSING_PAY(
        listOf(
            ActionsContraord.CONTESTAR,
            ActionsContraord.TRANSFERIR
        )
    ),
    PAYED(
        listOf(
            ActionsContraord.TRANSFERIR,
            ActionsContraord.CONTESTAR
        )
    ),

    EXPIRED(emptyList());
}

fun String.toContraordenacaoState(): ContraordenacaoState {
    return ContraordenacaoState.entries.filter { it.name == this }.firstOrNull()
        ?: throw Error("NOT POSSIBLE TO CREATE CONTRODENACAO STATE")
}
