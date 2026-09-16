package com.example.aceleda_bank.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.aceleda_bank.Room.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: TransactionEntity)

    @Query("SELECT * FROM `transaction` ORDER BY id DESC")
    fun getHistory(): Flow<List<TransactionEntity>>

    @Query("DELETE FROM `transaction`")
    suspend fun clearHistory()
}
