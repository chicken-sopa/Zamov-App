package com.example.testapp.network.modules

data class NewLoanInviteOM(
    val invitedUserId: Int,
    val vehicleID: Int,
    val startTime: String,
    val endTime: String
)