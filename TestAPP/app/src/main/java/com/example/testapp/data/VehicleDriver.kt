package com.example.testapp.data

import java.time.LocalDateTime


data class VehicleDriver(
    val id: Int,

    val respostaConvite: Boolean?,

    val dataHoraInicio: LocalDateTime,

    val dataHoraFim: LocalDateTime,

    val dataHoraFimReal: LocalDateTime?,
)
