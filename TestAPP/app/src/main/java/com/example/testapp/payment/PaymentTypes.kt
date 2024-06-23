package com.example.testapp.payment

import com.example.testapp.R

enum class PaymentTypes(val nome: String, val logo: Int, val logoSize: Int = 24) {
    Cartao("Cartao de Credito ", R.drawable.baseline_credit_card_24, ),
    Mbway("MBWAY", R.drawable.mbway, 35),
    Multibanco("Multibanco", R.drawable.multibanco)

}