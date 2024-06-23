package com.example.testapp.data

import com.example.testapp.network.modules.SimpleContraordenacao

data class VehicleDetails(
    val vehicle: Vehicle,
    val userDriver: VehicleDriver?,
    val pendingContraord: List<SimpleContraordenacao>
)
