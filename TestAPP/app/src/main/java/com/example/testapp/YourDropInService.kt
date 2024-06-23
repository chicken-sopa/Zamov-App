package com.example.testapp

import com.adyen.checkout.components.core.ActionComponentData
import com.adyen.checkout.components.core.PaymentComponentData
import com.adyen.checkout.components.core.PaymentComponentState
import com.adyen.checkout.components.core.action.Action
import com.adyen.checkout.dropin.DropInResult
import com.adyen.checkout.dropin.DropInService
import com.adyen.checkout.dropin.DropInServiceResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class YourDropInService : DropInService() {

    // The handler to make a /payments request.
    override fun onSubmit(state: PaymentComponentState<*>) {
        val paymentComponentJson = PaymentComponentData.SERIALIZER.serialize(state.data)
        // Your server makes a /payments request, including `paymentComponentJson`.
        // This is used in 4: Make a payment.

        // Create the `DropInServiceResult` based on the /payments response.
        // You must switch to a background thread before making an API request. For example, `launch(Dispatchers.IO)` if using coroutines.

        launch(Dispatchers.IO) {

        }

        // If the payment finished, handle the result.
        sendResult(DropInServiceResult.Finished("YOUR_RESULT"))

        // If additional action is needed, handle the action.
        val action = Action.SERIALIZER.deserialize(paymentComponentJson)
        sendResult(DropInServiceResult.Action(action))
    }

    // Handler to make a /payments/details request to send additional payment details.
    override fun onAdditionalDetails(actionComponentData: ActionComponentData) {
        val actionComponentJson = ActionComponentData.SERIALIZER.serialize(actionComponentData)
        // Your server makes a /payments/details request, including `actionComponentJson`.
        // This is used in Step 5: Submit additional payment details.

        // Create the `DropInServiceResult` based on the /payments/details response.
        sendResult(DropInServiceResult.Finished("YOUR_RESULT"))
    }

    fun onDropInResult(dropInResult: DropInResult?) {
        when (dropInResult) {
            // The payment finishes with a result.
            is DropInResult.Finished -> print(" dropIN result Finished") //handleResult(dropInResult.result)
            // The shopper dismisses Drop-in.
            is DropInResult.CancelledByUser -> print(" dropIn result Cancelled by Buyer")
            // Drop-in encounters an error.
            is DropInResult.Error -> print(" dropIn result Error")//handleError(dropInResult.reason)
            // Drop-in encounters an unexpected state.
            null -> print(" dropIn result null")
        }
    }


}


