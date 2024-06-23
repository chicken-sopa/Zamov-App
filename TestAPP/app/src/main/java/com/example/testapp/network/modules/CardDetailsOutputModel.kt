package com.example.testapp.network.modules

data class CardDetailsOutputModel(
    val amount: Float,
    val nProcesso: Int,
    val cardNumber: Int,
    val cvc: Int,
    val date: Int,
) {}