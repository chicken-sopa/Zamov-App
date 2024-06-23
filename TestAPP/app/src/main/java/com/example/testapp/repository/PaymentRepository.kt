package com.example.testapp.repository

import com.adyen.checkout.components.core.PaymentMethodsApiResponse
import com.example.testapp.network.ApiResponse
import com.example.testapp.network.SafeAPiCall
import com.example.testapp.network.ZamovRetrofitPaymentNetwork
import com.example.testapp.network.modules.CardDetailsOutputModel
import javax.inject.Inject


class PaymentRepository @Inject constructor(
    private val network: ZamovRetrofitPaymentNetwork,
    private val safeAPiCall: SafeAPiCall,
) {

    suspend fun getPaymentMethods(): ApiResponse<PaymentMethodsApiResponse> {
        return safeAPiCall.makeSafeAPiCall {  network.getPaymentMethods()}
    }

    suspend fun payWithMbway(): ApiResponse<Boolean> {
        return safeAPiCall.makeSafeAPiCall {  network.payWithMbway()}
        //return ApiResponse.Success(network.payWithMbway()) erase after debug phase
    }
    suspend fun payWithCard(cardDetails: CardDetailsOutputModel):ApiResponse<Boolean>{
        return safeAPiCall.makeSafeAPiCall {  network.payWithCard(cardDetails)}
    }

}