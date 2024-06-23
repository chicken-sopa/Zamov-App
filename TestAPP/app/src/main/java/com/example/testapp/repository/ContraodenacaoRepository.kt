package com.example.testapp.repository

import com.example.testapp.network.ApiResponse
import com.example.testapp.network.SafeAPiCall
import com.example.testapp.network.ZamovRetrofitPaymentNetwork
import com.example.testapp.network.modules.SimpleContraordenacao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ContraodenacaoRepository @Inject constructor(
    private val network: ZamovRetrofitPaymentNetwork,
    private val safeAPiCall: SafeAPiCall,
) {
    fun getUserContraodenacoes(): Flow<List<SimpleContraordenacao>?> {
        return flow {
            val result = safeAPiCall.makeSafeAPiCall { network.getUserContraordenacoes() }
            if (result is ApiResponse.Success) emit(result.value)
            else emit(null)
        }
    }
}