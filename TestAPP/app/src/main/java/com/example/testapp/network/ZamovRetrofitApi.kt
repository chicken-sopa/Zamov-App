package com.example.testapp.network

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.tracing.trace
import com.adyen.checkout.components.core.PaymentMethodsApiResponse
import com.example.testapp.authentication.dataStore
import com.example.testapp.data.VehicleDetails
import com.example.testapp.network.modules.AnswerLoanInviteOutputModel
import com.example.testapp.network.modules.CardDetailsOutputModel
import com.example.testapp.network.modules.CitizenLoginOutputModel
import com.example.testapp.network.modules.CitizenTokenInputModel
import com.example.testapp.network.modules.ContraordenacaoInputModel
import com.example.testapp.network.modules.NewLoanInviteOM
import com.example.testapp.network.modules.NewVehicleOutputModel
import com.example.testapp.network.modules.SimpleContraordenacao
import com.example.testapp.network.modules.TransferRequestOutputModel
import com.example.testapp.network.modules.TransferResponseOutputModel
import com.example.testapp.network.modules.UserVehiclesInputModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Interface representing network calls to the ZAMOV backend
 */
interface ZamovNetworkDataSource {
    suspend fun getPaymentMethods(): PaymentMethodsApiResponse
    suspend fun payWithMbway(): Boolean
    suspend fun payWithCard(cardDetails: CardDetailsOutputModel): Boolean
    suspend fun getUserContraordenacoes(): List<SimpleContraordenacao>
    suspend fun getContraordenacaoDetalhes(nProcesso: Int): ContraordenacaoInputModel?
    suspend fun requestTransfer(transferRequest: TransferRequestOutputModel)
    suspend fun answerTransferRequest(transferResponse: TransferResponseOutputModel)
    suspend fun getVehiclesFromUser():UserVehiclesInputModel
    suspend fun citizenLogin(citizenLoginOM: CitizenLoginOutputModel): CitizenTokenInputModel
    suspend fun createNewVehicle(newVehicleOM: NewVehicleOutputModel):Unit
    suspend fun getVehicleDetails(vehicleId: Int): VehicleDetails
    suspend fun loanVehicle(loanInvite: NewLoanInviteOM)
    suspend fun answerLoanInvite(answer: AnswerLoanInviteOutputModel)
    suspend fun cancelLoan(loanId: Int)
    suspend fun logoutUser()
}


private interface ZamovRetrofitPaymentApi {

    @GET(value = "/getPayMethods")
    suspend fun getPaymentMethods(): PaymentMethodsApiResponse

    @POST(value = "/payWithMbway")
    suspend fun payWithMbway(): Boolean

    @POST(value = "/payWithCard")
    suspend fun payWithCard(@Body cardDetails: CardDetailsOutputModel): Boolean

    @GET(value = "/user/contraordenacoes")
    suspend fun getUserContraordenacoes(): List<SimpleContraordenacao>

    @GET(value = "/contraordencao/id/{nProcesso}")
    suspend fun getContraordenacaoDetalhes(@Path("nProcesso") nProcesso: Int): ContraordenacaoInputModel?

    @POST(value = "/offender/requestTransfer")
    suspend fun requestTransfer(@Body transferRequest: TransferRequestOutputModel)

    @POST(value = "/offender/answerTransfer")
    suspend fun answerTransferRequest(@Body transferResponse: TransferResponseOutputModel)

    @GET(value= "user/vehicles")
    suspend fun getVehiclesFromUser():UserVehiclesInputModel

    @POST(value="/citizen/login")
    suspend fun citizenLogin(@Body citizenLoginOM: CitizenLoginOutputModel): CitizenTokenInputModel

    @POST(value="/vehicle")
    suspend fun createNewVehicle(@Body newVehicleOM: NewVehicleOutputModel)

    @GET(value="/vehicle/{vehicleId}/details")
    suspend fun getVehicleDetails(@Path("vehicleId")vehicleId: Int): VehicleDetails

    @POST(value= "/vehicle/loanInvite")
    suspend fun loanVehicle(@Body loanInvite: NewLoanInviteOM)

    @POST( value= "/vehicle/answerLoanInvite")
    suspend fun answerLoanInvite(@Body answer: AnswerLoanInviteOutputModel)

    @POST(value = "/vehicle/loanInvite/{loanId}/cancel")
    suspend fun cancelLoan(@Path("loanId") loanId: Int)

    @PUT(value ="/user/logout")
    suspend fun logoutUser()
}


@Singleton
class ZamovRetrofitPaymentNetwork @Inject constructor(
    // okhttpCallFactory: dagger.Lazy<Call.Factory>,
    authInterceptor: AuthInterceptor
) : ZamovNetworkDataSource {

    val ZAMOV_BASE_URL = "https://trusty-secondly-meerkat.ngrok-free.app"

    /*val client = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor())
        .build()*/
    @RequiresApi(Build.VERSION_CODES.O)
    var gson: Gson? = GsonBuilder().registerTypeAdapter(
        LocalDateTime::class.java,
        JsonDeserializer { json, type, jsonDeserializationContext ->
            //ZonedDateTime.parse(json.getAsJsonPrimitive().asString).toLocalDateTime()
            LocalDateTime.parse(json.getAsJsonPrimitive().asString)
        }).create()


    @RequiresApi(Build.VERSION_CODES.O)
    private val networkApi = trace("RetrofitNiaNetwork") {
        val client = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
        Retrofit.Builder()
            .baseUrl(ZAMOV_BASE_URL)
            // We use callFactory lambda here with dagger.Lazy<Call.Factory>
            // to prevent initializing OkHttp on the main thread.
            //.callFactory { okhttpCallFactory.get().newCall(it) }
            .addConverterFactory(
                GsonConverterFactory.create(gson)
            )
            .client(client)
            .build()
            .create(ZamovRetrofitPaymentApi::class.java)
    }


    override suspend fun getPaymentMethods(): PaymentMethodsApiResponse {
        println("PAYMENT METHODS STARTED")
        val response = networkApi.getPaymentMethods()
        println(response)
        println("PAYMENT METHODS FINNISHED")
        return response
    }

    override suspend fun payWithMbway(): Boolean {
        val response = networkApi.payWithMbway()
        return response
    }

    override suspend fun payWithCard(cardDetails: CardDetailsOutputModel): Boolean {
        val response = networkApi.payWithCard(cardDetails)
        return response
    }

    override suspend fun getUserContraordenacoes(): List<SimpleContraordenacao> {

        val response = networkApi.getUserContraordenacoes()
        Log.println(Log.DEBUG, "TEST USER CONTRAORDENACOES", response.toString())
        return response
    }

    override suspend fun getContraordenacaoDetalhes(nProcesso: Int): ContraordenacaoInputModel? {
        Log.println(Log.DEBUG, "TEST USER DETALHES CONTRORD", "START GETTING THIS DETALHES")
        val response = networkApi.getContraordenacaoDetalhes(nProcesso)
        Log.println(Log.DEBUG, "TEST USER DETALHES CONTRORD", "after result")
        Log.println(Log.DEBUG, "TEST USER DETALHES CONTRORD", response.toString())
        return response
    }

    override suspend fun requestTransfer(transferRequest: TransferRequestOutputModel) {
        val response = networkApi.requestTransfer(transferRequest)
        return response
    }

    override suspend fun answerTransferRequest(transferResponse: TransferResponseOutputModel) {
        val response = networkApi.answerTransferRequest(transferResponse)
        return response
    }

    override suspend fun getVehiclesFromUser(): UserVehiclesInputModel {
        //val apiToken = authRepository.tokenAPI.value
        val response = networkApi.getVehiclesFromUser()
        return response
    }

    override suspend fun citizenLogin(citizenLoginOM: CitizenLoginOutputModel): CitizenTokenInputModel {
        val response = networkApi.citizenLogin(citizenLoginOM = citizenLoginOM)
        return response
    }

    override suspend fun createNewVehicle(newVehicleOM: NewVehicleOutputModel) {
        val response = networkApi.createNewVehicle(newVehicleOM)
        return response
    }

    override suspend fun getVehicleDetails(vehicleId: Int): VehicleDetails {
        val response = networkApi.getVehicleDetails(vehicleId)
        return response
    }

    override suspend fun loanVehicle(loanInvite: NewLoanInviteOM) {
        val response = networkApi.loanVehicle(loanInvite)
        return response
    }

    override suspend fun answerLoanInvite(answer: AnswerLoanInviteOutputModel) {
        val response = networkApi.answerLoanInvite(answer)
        return response
    }

    override suspend fun cancelLoan(loanId: Int) {
        val response = networkApi.cancelLoan(loanId)
        return response
    }

    override suspend fun logoutUser() {
        val response = networkApi.logoutUser()
        return response
    }


}

class AuthInterceptor @Inject constructor(@ApplicationContext val context: Context): Interceptor {

    val API_TOKEN = stringPreferencesKey("api_token")

    suspend fun getToken(): String? {
            return context.dataStore.data.first()[API_TOKEN]
    }

    override fun intercept(chain: Interceptor.Chain): Response {

        return runBlocking {
            val token = getToken() ?: "NO API TOKEN"
            val currentRequest = chain.request().newBuilder()
            currentRequest.addHeader("Authorization", "Bearer $token")

            val newRequest = currentRequest.build()
            return@runBlocking chain.proceed(newRequest)
        }

    }
}