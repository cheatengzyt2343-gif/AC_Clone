package com.example.aceleda_bank.DTO.TransactionInfoDTO

data class TransferRequest(
    val receiverAccountNum: String,
    val amount: Long,
    val senderAccountNum: String
)
