package com.example.aceleda_bank.DTO.UserInfoDTO

data class SetPinRequestDTO(
    val firebaseToken: String,
    val pin: String,
    val confirmPin: String
)
