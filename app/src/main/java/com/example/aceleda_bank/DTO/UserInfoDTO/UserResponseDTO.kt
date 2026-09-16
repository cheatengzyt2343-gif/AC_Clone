package com.example.aceleda_bank.DTO.UserInfoDTO

data class UserResponseDTO(
    val firstName: String,
    val lastName:String,
    val phoneNumber: String,
    val gender: String,
    val dob: String,
    val profession: String,
    val nationality: String,
    val address: String,
    val imageUrl: String
)
