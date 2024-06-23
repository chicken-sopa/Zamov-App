package com.example.testapp.network

import android.util.Log
import com.example.testapp.authentication.AuthRepository
import com.example.testapp.testViewModel
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SafeAPiCall @Inject constructor(val testViewModel: testViewModel, val authRepository: AuthRepository) {

    suspend fun <T> makeSafeAPiCall(apiCall: suspend () -> T): ApiResponse<T> {
        try {
            val result = apiCall()
            Log.println(Log.DEBUG, "HTTP ERROR", result.toString())
            return ApiResponse.Success(value = result)
        } catch (throwable: Throwable) {
            Log.println(Log.DEBUG, "HTTP ERROR", throwable.message.toString())

            return when (throwable) {
                is IOException -> {
                    ApiResponse.NetworkError()
                }

                is HttpException -> {
                    val code = throwable.code()
                    if (code == 404) {
                        return ApiResponse.GenericError(
                            code,
                            "SERVER PROBLEM PLEASE TRY AGAIN"
                        )
                    }
                    if (code == 401) {
                        //context.userAuthenticationInvalid.value = true
                        //testViewModel.setUserAuthNotValid()
                        authRepository.eraseUerAuthToken()
                    }
                    if(code == 500){
                        Log.e("HTTP ERROR", "USER AUTH ERROR")
                        testViewModel.setUserAuthNotValid() // TODO only make this for error 401 user not auth
                        Log.i("HTTP ERROR", "HEELLLE")
                    }

                    val errorResponse = throwable.response()?.errorBody().toString()
                    ApiResponse.GenericError(code, errorResponse)
                }

                else -> {
                    ApiResponse.GenericError(null, null)
                }
            }


        }
    }
}

