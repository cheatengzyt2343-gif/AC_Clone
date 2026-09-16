package com.example.aceleda_bank.Repository.QrRepository

import com.example.aceleda_bank.DTO.KhqrDTO.KHQRResponseDTO
import com.example.aceleda_bank.network.ApiService
import javax.inject.Inject

class QRRepository @Inject constructor(
    private val api: ApiService
) {

    suspend fun generateQR(token: String): KHQRResponseDTO {
        return api.generateQR("Bearer $token")
    }
}