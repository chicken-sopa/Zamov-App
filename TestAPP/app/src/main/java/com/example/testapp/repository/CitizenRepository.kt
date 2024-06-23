package com.example.testapp.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.testapp.network.ApiResponse
import com.example.testapp.network.SafeAPiCall
import com.example.testapp.network.ZamovRetrofitPaymentNetwork
import com.example.testapp.network.modules.CitizenLoginOutputModel
import com.example.testapp.network.modules.CitizenToken
import features.authPages.AuthenticationUIState
import javax.inject.Inject

class CitizenRepository @Inject constructor(
    private val network: ZamovRetrofitPaymentNetwork,
    private val safeAPiCall: SafeAPiCall,
) {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun makeCitizenLoginRequest(loginRequest: CitizenLoginOutputModel): AuthenticationUIState {

        val result = safeAPiCall.makeSafeAPiCall { network.citizenLogin(loginRequest) }
        return if (result is ApiResponse.Success) {
            val citizenToken = CitizenToken(result.value.citizen.toCitizen(), result.value.token)
            AuthenticationUIState.Authenticated(citizenToken)
        } else {
            AuthenticationUIState.NotAuthenticated()

        }
    }

    suspend fun logoutUser(): Boolean {
        val result = safeAPiCall.makeSafeAPiCall { network.logoutUser() }
        return if (result is ApiResponse.Success) { true }
        else {
            false
        }
    }
}
