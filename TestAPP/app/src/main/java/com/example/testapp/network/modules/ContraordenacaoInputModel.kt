package com.example.testapp.network.modules


import com.example.testapp.domain.Contraordenacao
import com.example.testapp.domain.toContraordenacaoState

data class ContraordenacaoInputModel(

    val nAuto: Int,

    val nProcesso: Int,

    val dataCometido: String,

    val descricao: String,

    val sancaoDescricao: String?,

    val sancaoDataLimite: String?,

    val estado: String,

    val matricula: String,

    val valorCoima: Float?
){
    fun toContraordenacao(): Contraordenacao{
        return Contraordenacao(
            this.nAuto,
            this.nProcesso ,
            this.dataCometido,
            this.descricao,
            this.sancaoDescricao,
            this.sancaoDataLimite,
            this.estado.toContraordenacaoState() ,
            this.matricula,
            this.valorCoima,
        )
    }
}
