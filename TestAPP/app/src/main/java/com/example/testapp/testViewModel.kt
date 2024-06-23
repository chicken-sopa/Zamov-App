package com.example.testapp

import androidx.compose.runtime.mutableStateOf
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class testViewModel  @Inject constructor(){
    val userAuthNotValid = mutableStateOf(false)
    fun setUserAuthNotValid(){
        userAuthNotValid.value = true
    }
    fun setUserAuthValid(){
        userAuthNotValid.value = false
    }
}