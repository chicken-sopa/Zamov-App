package com.example.testapp.network.modules

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.testapp.data.Citizen
import java.time.LocalDate

data class CitizenInputModel(
    val id: Int,
    val cc: String,
    val nome: String,
    val numeroLicenca: String?,
    val dataNascimento: String,
    val dataEmissaoLicenca: String?,
    val dataValidadeLicenca: String?,
    val tipoLicenca: String?,
    val nPontosLicenca: Int,
    val nif: String,
    val email: String?,
    val telemovel: String?,
) {

    @RequiresApi(Build.VERSION_CODES.O)
    fun toCitizen(): Citizen {
        return Citizen(
            this.id,
            this.cc,
            this.nome,
            this.numeroLicenca,
            LocalDate.parse(this.dataNascimento),
            LocalDate.parse(this.dataEmissaoLicenca),
            LocalDate.parse(this.dataValidadeLicenca),
            this.tipoLicenca,
            this.nPontosLicenca,
            this.nif,
            this.email,
            this.telemovel
        )
    }
}