package com.example.aceleda_bank.Room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction")
data class TransactionEntity(
    @PrimaryKey
    val id: Long,
    val accountNumberSender: String,
    val fullNameSender: String,
    val amount: Long,
    val fullNameReceiver: String,
    val date: String
)
