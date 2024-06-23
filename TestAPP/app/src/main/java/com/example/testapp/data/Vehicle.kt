package com.example.testapp.data

import kotlinx.serialization.Serializable

@Serializable
data class Vehicle(
    val id: Int,
    val matricula: String,
    val tipo: String,
    val modelo: String,
    val marca: String,
    val proprietarioNome: String,
    val isUserProperty: Boolean,

    //val getLatestBorrowRecord: VehicleDriver?
)




