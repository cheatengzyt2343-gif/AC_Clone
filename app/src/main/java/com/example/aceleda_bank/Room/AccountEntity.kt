package com.example.aceleda_bank.Room

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "account")
data class AccountEntity(
    @PrimaryKey
    val accountNumber: String,
    val balance: Long,
    val type: String,
    val currency: String
)
