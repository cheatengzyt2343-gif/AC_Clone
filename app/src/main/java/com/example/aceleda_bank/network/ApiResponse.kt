package com.example.aceleda_bank.network

data class ApiResponse<T>(
    val message: String,
    val data: T
)