package com.example.aceleda_bank.Repository.AuthRepository

import android.util.Log
import com.example.aceleda_bank.DTO.AuthDTO.FirebaseLoginRequest
import com.example.aceleda_bank.DTO.AuthDTO.LoginResponse
import com.example.aceleda_bank.DTO.AuthDTO.PinRequestDTO
import com.example.aceleda_bank.DTO.AuthDTO.RegisterRequestDTO
import com.example.aceleda_bank.DTO.AuthDTO.RegisterResponseDTO
import com.example.aceleda_bank.DTO.UserInfoDTO.SetPinRequestDTO
import com.example.aceleda_bank.dataStore.TokenManager
import com.example.aceleda_bank.network.AuthApi
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authApi: AuthApi,
    private val tokenManager: TokenManager,
) {
    suspend fun firebaseLogin(firebaseToken: String): LoginResponse {
        return authApi.firebaseLogin(
            FirebaseLoginRequest(firebaseToken)
        )
    }

    suspend fun register(request: RegisterRequestDTO?): RegisterResponseDTO? {
        val response = authApi.register(request!!)
        if (response.isSuccessful) {
            return response.body()?.data
        }
        return null
    }

    suspend fun verifyPin(pin: PinRequestDTO): Boolean? {
        val token = tokenManager.token.firstOrNull()
        Log.d("AuthRepository", "verifyPin: Token = $token")

        if (token.isNullOrEmpty()) {
            Log.e("AuthRepository", "verifyPin: No token found")
            return false
        }
        val response = authApi.verifyPin("Bearer $token", pin)
        if (response.isSuccessful) {
            return response.body()?.data
        } else {
            Log.e("AuthRepository", "verifyPin failed: ${response.errorBody()?.string()}")
        }
        return false
    }


    suspend fun checkUser(phone: String): Boolean?{
        val response = authApi.checkUser(phone)
        if (response.isSuccessful) {
            return response.body()?.data
        }
        return null
    }

    suspend fun setPin(request: SetPinRequestDTO): Boolean {
        val response = authApi.setPin(request)

        return if (response.isSuccessful) {
            response.body()?.data ?: false
        } else {
            false
        }
    }
}