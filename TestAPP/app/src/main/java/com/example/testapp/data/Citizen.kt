package com.example.testapp.data

import java.time.LocalDate

class Citizen(
    val id: Int,
    val cc: String,
    val nome: String,
    val numeroLicenca: String?,
    val dataNascimento: LocalDate,
    val dataEmissaoLicenca: LocalDate?,
    val dataValidadeLicenca: LocalDate?,
    val tipoLicenca: String?,
    val nPontosLicenca: Int,
    val nif: String,
    val email: String?,
    val telemovel: String?,
){

}