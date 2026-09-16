package com.example.aceleda_bank.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.aceleda_bank.DAO.AccountDao
import com.example.aceleda_bank.DAO.TransactionDao
import com.example.aceleda_bank.DAO.UserDao
import com.example.aceleda_bank.Room.UserEntity
import com.example.aceleda_bank.Room.AccountEntity
import com.example.aceleda_bank.Room.TransactionEntity


@Database(entities = [UserEntity::class, AccountEntity::class, TransactionEntity::class], version = 9)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun accountDao(): AccountDao
    abstract fun transactionDao(): TransactionDao
}