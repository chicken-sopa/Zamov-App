package com.example.testapp.network.modules

import com.example.testapp.data.Vehicle

data class UserVehiclesInputModel(
    val userOwnedVehicles: List<Vehicle>,
    val userBorrowedVehicles: List<Vehicle>
)