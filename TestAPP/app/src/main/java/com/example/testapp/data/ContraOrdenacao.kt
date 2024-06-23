package com.example.testapp.data

data class ContraOrdenacao(
    val date: String,
    val location: String,
    val fine: Int,
    val severity: String,
    val carro: Vehicle,
    val user: User,
    val ID: String
)

