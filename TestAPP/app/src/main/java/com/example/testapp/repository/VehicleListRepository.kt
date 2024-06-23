package com.example.testapp.repository

import com.example.testapp.data.UserVehicles
import com.example.testapp.network.modules.AnswerLoanInviteOutputModel
import com.example.testapp.network.ApiResponse
import com.example.testapp.network.SafeAPiCall
import com.example.testapp.network.ZamovRetrofitPaymentNetwork
import com.example.testapp.network.modules.NewLoanInviteOM
import com.example.testapp.network.modules.NewVehicleOutputModel
import features.veiculos.vehicleDetails.VehicleDetailsUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VehicleRepository @Inject constructor(
    private val network: ZamovRetrofitPaymentNetwork,
    private val safeAPiCall: SafeAPiCall,
) {
    fun getUserVehicles(): Flow<UserVehicles?> {
        return flow {
            val result =
                safeAPiCall.makeSafeAPiCall { network.getVehiclesFromUser() }
            if (result is ApiResponse.Success) {
                emit(
                    UserVehicles(
                        result.value.userOwnedVehicles,
                        result.value.userBorrowedVehicles
                    )
                )
            } else {
                emit(null)
            }
        }
    }

    fun createNewVehicle(newVehicle: NewVehicleOutputModel): Flow<Boolean> {
        return flow {
            val result =
                safeAPiCall.makeSafeAPiCall { network.createNewVehicle(newVehicle) }
            if (result is ApiResponse.Success) {
                emit(true)
            } else {
                emit(false)
            }
        }
    }

    fun createLoanInviteVehicle(newLoanInvite: NewLoanInviteOM): Flow<Boolean> {
        return flow {
            val result =
                safeAPiCall.makeSafeAPiCall { network.loanVehicle(newLoanInvite) }
            if (result is ApiResponse.Success) {
                emit(true)
            } else {
                emit(false)
            }
        }
    }

    fun getVehicleDetails(vehicleId: Int): Flow<VehicleDetailsUiState> {
        return flow {
            val result =
                safeAPiCall.makeSafeAPiCall { network.getVehicleDetails(vehicleId) }
            if (result is ApiResponse.Success) {
                emit(VehicleDetailsUiState.Success(result.value))
            } else {
                emit(VehicleDetailsUiState.Error)
            }
        }
    }

    suspend fun answerLoanInvite(answer: AnswerLoanInviteOutputModel): Boolean {
        //  if error than false and make UI try again
        val result =
            safeAPiCall.makeSafeAPiCall { network.answerLoanInvite(answer) }
        return if (result is ApiResponse.Success) {
            true
        } else {
            false
        }
    }


    suspend fun cancelLoan(loanId:Int): Boolean {
        //  if error than false and make UI try again
        val result =
            safeAPiCall.makeSafeAPiCall { network.cancelLoan(loanId) }
        return if (result is ApiResponse.Success) {
            true
        } else {
            false
        }
    }

}