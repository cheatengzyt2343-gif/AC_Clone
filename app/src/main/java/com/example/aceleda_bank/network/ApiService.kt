package com.example.aceleda_bank.network

import com.example.aceleda_bank.DTO.AccountInfoDTO.AccountResponseDTO
import com.example.aceleda_bank.DTO.KhqrDTO.KHQRResponseDTO
import com.example.aceleda_bank.DTO.TransactionInfoDTO.TransactionResponseDTO
import com.example.aceleda_bank.DTO.TransactionInfoDTO.TransferRequest
import com.example.aceleda_bank.DTO.TransactionInfoDTO.TransferResponse
import com.example.aceleda_bank.DTO.UserInfoDTO.UserResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("api/user/generate")
    suspend fun generateQR(
        @Header("Authorization") token: String
    ): KHQRResponseDTO

    @GET("api/user/info")
    suspend fun getUserInfo(
        @Header("Authorization") token: String
    ): Response<ApiResponse<UserResponseDTO>>

    @GET("api/user/account/info")
    suspend fun getAccountInfo(
        @Header("Authorization") token: String
    ): Response<ApiResponse<List<AccountResponseDTO>>>

    @GET("api/user/transaction/info")
    suspend fun getTransactionInfo(
        @Header("Authorization") token: String
    ): Response<ApiResponse<List<TransactionResponseDTO>>>

    @POST("api/user/transfer")
    suspend fun transfer(
        @Header("Authorization") token: String,
        @Body transferRequest: TransferRequest
    ): Response<ApiResponse<TransferResponse>>
}