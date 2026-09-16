package com.example.aceleda_bank.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.aceleda_bank.Room.AccountEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccount(account: AccountEntity)

    @Query("SELECT * FROM account LIMIT 1")
    fun getAccount(): Flow<AccountEntity?>

    @Query("DELETE FROM account")
    suspend fun clearAccount()

    @Query("SELECT * FROM account")
    fun getAllAccounts(): Flow<List<AccountEntity>>

}
