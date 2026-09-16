package com.example.aceleda_bank.dataStore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "auth")

class TokenManager(
    private val context: Context
) {

    companion object {
        private val JWT_TOKEN = stringPreferencesKey("jwt_token")
        private val PHONE_KEY = stringPreferencesKey("phone_number")
    }

    // Save phone number
    suspend fun savePhoneNumber(phone: String) {
        context.dataStore.edit { prefs ->
            prefs[PHONE_KEY] = phone
        }
    }

    // Get phone number
    val phoneNumber: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[PHONE_KEY]
    }

    // Optional: clear phone
    suspend fun clearPhoneNumber() {
        context.dataStore.edit { prefs ->
            prefs.remove(PHONE_KEY)
        }
    }
    suspend fun saveToken(token: String) {
        context.dataStore.edit { prefs ->
            prefs[JWT_TOKEN] = token.toString()
        }
    }

    val token: Flow<String?> =
        context.dataStore.data.map { prefs ->
            prefs[JWT_TOKEN]
        }

    suspend fun clearToken() {
        context.dataStore.edit { prefs ->
            prefs.remove(JWT_TOKEN)
        }
    }
}