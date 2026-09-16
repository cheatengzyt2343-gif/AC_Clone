package com.example.aceleda_bank.network

import com.example.aceleda_bank.DTO.AuthDTO.FirebaseLoginRequest
import com.example.aceleda_bank.DTO.AuthDTO.LoginResponse
import com.example.aceleda_bank.DTO.AuthDTO.PinRequestDTO
import com.example.aceleda_bank.DTO.AuthDTO.RegisterRequestDTO
import com.example.aceleda_bank.DTO.AuthDTO.RegisterResponseDTO
import com.example.aceleda_bank.DTO.UserInfoDTO.SetPinRequestDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface AuthApi {
    @POST("api/auth/firebase-login")
    suspend fun firebaseLogin(
        @Body request: FirebaseLoginRequest
    ): LoginResponse

    @POST("api/auth/register")
    suspend fun register(
        @Body request: RegisterRequestDTO
    ): Response<ApiResponse<RegisterResponseDTO>>

    @POST("api/user/verify/pin")
    suspend fun verifyPin(
        @Header("Authorization") token: String?,
        @Body pin: PinRequestDTO
    ): Response<ApiResponse<Boolean>>

    @PUT("api/auth/setPin")
    suspend fun setPin(
        @Body requestPin: SetPinRequestDTO
    ): Response<ApiResponse<Boolean>>

    @GET("api/auth/user/check")
    suspend fun checkUser(
        @Query("phoneNumber") phone: String
    ): Response<ApiResponse<Boolean>>
}
