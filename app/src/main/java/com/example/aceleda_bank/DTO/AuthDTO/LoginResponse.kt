package com.example.aceleda_bank.DTO.AuthDTO

data class LoginResponse(
    val phoneNumber: String,
    val token: String,
    val action: String
)