package com.example.aceleda_bank.DTO.AuthDTO

data class RegisterResponseDTO(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val gender: String,
    val profession: String,
    val imageUrl: String
)
