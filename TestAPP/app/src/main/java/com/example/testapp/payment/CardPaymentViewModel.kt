package com.example.testapp.payment

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.network.ApiResponse
import com.example.testapp.network.modules.CardDetailsOutputModel
import com.example.testapp.repository.PaymentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CardPaymentViewModel @Inject constructor(
    val paymentRepository: PaymentRepository,
    savedStateHandle: SavedStateHandle

) : ViewModel() {

    private val _paymentResponseState =
        MutableStateFlow<PaymentState>(PaymentState.PaymentNotStarted)
    val paymentState: Flow<PaymentState> = _paymentResponseState

    private val _paymentError = MutableStateFlow<String?>(null)
    val paymentError: Flow<String?> = _paymentError


    val nProcesso: Int = checkNotNull(savedStateHandle["nProcesso"])
    val valorCoima: Float = checkNotNull(savedStateHandle["valor"])

    val cardNumber = mutableStateOf("")
    val cvc = mutableStateOf("")
    val date = mutableStateOf("")


    /*init {
        viewModelScope.launch {
            if (_cardComponentDataFlow.value == null) {
                //fetchPaymentMethods()
            }
        }
    }*/


    /*private suspend fun fetchPaymentMethods() =
        withContext(Dispatchers.IO) {
            Log.println(Log.WARN, "PAYMENT TRYING INIT", "ITS STARTING PAYMENT")
            val result: PaymentMethodsApiResponse = paymentRepository.getPaymentMethods()
            //val result = PaymentMethodsApiResponse()
            _cardComponentDataFlow.emit(result)
            _cardViewState.emit(CardViewState.ShowComponent)
            Log.println(Log.WARN, "PAYMENT TRYING INIT", "ITS FINISHED PAYMENT")
        }*/


    fun testPayWithCard() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _paymentResponseState.emit(PaymentState.PaymentProcessing)

                val cardDetails = CardDetailsOutputModel(
                    amount = valorCoima,
                    nProcesso = nProcesso,
                    cardNumber = cardNumber.value.toInt(),
                    date =date.value.toInt(),
                    cvc = cvc.value.toInt(),
                )

                val result = paymentRepository.payWithCard(cardDetails)

                if (result is ApiResponse.Success) {
                    _paymentResponseState.emit(PaymentState.PaymentFinished)
                } else {

                    _paymentResponseState.emit(PaymentState.PaymentNotStarted)

                    if (result is ApiResponse.GenericError) {
                        _paymentError.emit(result.errorMessage)
                    }
                    if (result is ApiResponse.NetworkError) {
                        _paymentError.emit(result.message)
                    }

                }
            }
        }
    }


}

sealed class PaymentState {
    data object PaymentNotStarted : PaymentState()
    data object PaymentProcessing : PaymentState()
    data object PaymentFinished : PaymentState()
}
