package com.example.aceleda_bank.DTO.AccountInfoDTO

data class AccountResponseDTO(
    val accountNumber: String,
    val balance: Long,
    val type: String,
    val currency: String
)
