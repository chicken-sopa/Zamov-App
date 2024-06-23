package com.example.testapp.repository

import com.example.testapp.domain.Contraordenacao
import com.example.testapp.network.ApiResponse
import com.example.testapp.network.SafeAPiCall
import com.example.testapp.network.ZamovRetrofitPaymentNetwork
import com.example.testapp.network.modules.TransferRequestOutputModel
import com.example.testapp.network.modules.TransferResponseOutputModel
import features.detalhesContraord.RequestTransferringUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DetalhesRepository @Inject constructor(
    private val network: ZamovRetrofitPaymentNetwork,
    private val safeAPiCall: SafeAPiCall,
) {
    fun getContraordenacaoDetalhes(nProcesso: Int): Flow<Contraordenacao?> {
        return flow {
            val result =
                safeAPiCall.makeSafeAPiCall { network.getContraordenacaoDetalhes(nProcesso = nProcesso) }
            if (result is ApiResponse.Success) {
                emit(result.value?.toContraordenacao())
            } else {
                emit(null)
            }
        }
    }

     fun makeRequestTransfer(transferRequest: TransferRequestOutputModel): Flow<RequestTransferringUiState> {
        return flow {
            val result =
                safeAPiCall.makeSafeAPiCall { network.requestTransfer(transferRequest) }
            if (result is ApiResponse.Success) {
                emit(RequestTransferringUiState.Success)
            } else {
                emit(RequestTransferringUiState.Error)
            }
        }
    }

    suspend fun answerTransferRequest(transferResponse: TransferResponseOutputModel): ApiResponse<Unit> {
        return safeAPiCall.makeSafeAPiCall { network.answerTransferRequest(transferResponse) }
    }

}