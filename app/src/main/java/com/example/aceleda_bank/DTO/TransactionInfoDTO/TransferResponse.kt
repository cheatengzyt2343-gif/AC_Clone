package com.example.aceleda_bank.DTO.TransactionInfoDTO

data class TransferResponse(
    val fullNameSender: String,
    val accountNumberSender: String,
    val amount: Long,
    val fullNameReceiver: String,
    val date: String
)
