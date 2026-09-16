package com.example.aceleda_bank.Room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val phoneNumber: String,
    val firstName: String,
    val lastName: String,
    val gender: String,
    val dob: String,
    val profession: String,
    val nationality: String,
    val address: String,
    val imageUrl: String?
)
