package com.example.aceleda_bank.DTO.TransactionInfoDTO

data class TransactionResponseDTO(
    val id:Long,
    val accountNumberSender: String,
    val fullNameSender: String,
    val amount: Long,
    val fullNameReceiver: String,
    val date: String
)
