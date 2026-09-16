package com.example.aceleda_bank.DTO.AuthDTO

import android.net.Uri

data class RegisterRequestDTO(
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val gender: String,
    val profession: String,
    val address: String,
    val dob: String,
    val nationality: String,
    val imageUrl: Uri?,
)
