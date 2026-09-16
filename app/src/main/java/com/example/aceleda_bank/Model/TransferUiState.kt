package com.example.aceleda_bank.Model

data class TransferUiState(
    val accountNumber: String = "",
    val merchantName: String = "",
    val currency: String = "",
    val amount: String = "0",
    val senderName: String = "",
    val senderNumber: String = "",
    val senderAccNum: String = ""
)