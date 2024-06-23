package com.example.testapp.network

sealed class ApiResponse<out T> {
    data class Success<out T>(val value: T) : ApiResponse<T>()
    data class GenericError(val code: Int? = null, val errorMessage: String? = null) :
        ApiResponse<Nothing>()

    data class NetworkError(val message: String = "NETWORK ERROR PLEASE TRY AGAIN") :
        ApiResponse<Nothing>()
}