package com.example.testapp.authentication

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.testapp.data.Citizen
import com.example.testapp.network.modules.CitizenToken
import com.google.gson.Gson
import com.plcoding.androidcrypto.CryptoManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton


private val USER_PREFERENCES_NAME = "user_preferences"

val Context.dataStore by preferencesDataStore(
    name = USER_PREFERENCES_NAME
)

@Singleton
class AuthRepository @Inject constructor(
    @ApplicationContext val context: Context,
    private val cryptoManager: CryptoManager
) {
    private companion object {
        val API_TOKEN = stringPreferencesKey("api_token")
        val CITIZEN = stringPreferencesKey("citizen")
    }

    val userAuthNotValid = mutableStateOf(false)
    fun setUserAuthNotValid() {
        userAuthNotValid.value = true
    }

    fun setUserAuthValid() {
        userAuthNotValid.value = false
    }


    val tokenAPI: Flow<CitizenToken?> =
        context.dataStore.data.map { preferences ->
            if (preferences[CITIZEN] == null || preferences[API_TOKEN] == null) {
                null
            } else {
                Log.println(Log.DEBUG, "citizen pref", preferences[CITIZEN].orEmpty())
                CitizenToken(
                    Gson().fromJson(preferences[CITIZEN], Citizen::class.java),
                    preferences[API_TOKEN]!!
                )
            }
        }


    val tokenAPIUiState: Flow<TokenUiSTate> =
        context.dataStore.data.map { preferences ->
            if (preferences[CITIZEN] == null || preferences[API_TOKEN] == null) {
                TokenUiSTate.Empty
            } else {
                Log.println(Log.DEBUG, "citizen pref", preferences[CITIZEN].orEmpty())
                TokenUiSTate.Success(
                    CitizenToken(
                        Gson().fromJson(preferences[CITIZEN], Citizen::class.java),
                        preferences[API_TOKEN]!!
                    )
                )
            }
        }.onStart { emit(TokenUiSTate.Loading) }

    suspend fun saveUserAuthToken(citizenToken: CitizenToken) {
        context.dataStore.edit { preferences ->
            preferences[API_TOKEN] = citizenToken.token
            preferences[CITIZEN] = Gson().toJson(citizenToken.citizen)
        }
    }

    fun eraseUerAuthToken() {
        runBlocking(context = Dispatchers.IO) {
            context.dataStore.edit { preferences ->
                preferences.remove(API_TOKEN)
            }

        }
    }

    fun createTestUerAuthToken() {
        runBlocking(context = Dispatchers.IO) {
            context.dataStore.edit { preferences ->
                preferences[API_TOKEN] = "TEST AUTH TOKEN"
            }

        }
    }

    fun logoutSession(){
        runBlocking(context = Dispatchers.IO) {
            context.dataStore.edit { preferences ->
                preferences.remove(API_TOKEN)
                preferences.remove(CITIZEN)
            }

        }
    }


    suspend fun checkIfUserHasToken(): Boolean {
        return context.dataStore.data.first()[API_TOKEN] != null
    }


}

sealed class TokenUiSTate{
    data object Loading : TokenUiSTate()
    data class Success(val citizenToken :CitizenToken) : TokenUiSTate()
    data object Empty : TokenUiSTate()
}