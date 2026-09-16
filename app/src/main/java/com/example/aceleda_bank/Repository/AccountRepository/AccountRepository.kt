package com.example.aceleda_bank.Repository.AccountRepository

import android.util.Log
import com.example.aceleda_bank.DAO.AccountDao
import com.example.aceleda_bank.Room.AccountEntity
import com.example.aceleda_bank.dataStore.TokenManager
import com.example.aceleda_bank.network.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class AccountRepository @Inject constructor(
    private val api: ApiService,
    private val accDao: AccountDao,
    private val tokenManager: TokenManager
) {

    fun getLocalAccount(): Flow<AccountEntity?> {
        return accDao.getAccount()
    }

    fun getAllAccounts(): Flow<List<AccountEntity>> {
        return accDao.getAllAccounts()
    }

    suspend fun refreshAccount() {
        Log.d("AccountRepository", "refreshAccount: Started")
        val token = tokenManager.token.firstOrNull()
        
        if (token.isNullOrEmpty()) {
            Log.e("AccountRepository", "Refresh failed: Token is null or empty")
            return
        }

        try {
            val response = api.getAccountInfo("Bearer $token")
            if (response.isSuccessful) {
                val apiResponse = response.body()
                val accounts = apiResponse?.data
                
                if (accounts != null) {
                    Log.d("AccountRepository", "API Success: Received ${accounts.size} accounts")
                    
                    accDao.clearAccount()
                    
                    accounts.forEach { account ->
                        accDao.insertAccount(
                            AccountEntity(
                                accountNumber = account.accountNumber,
                                balance = account.balance,
                                currency = account.currency,
                                type = account.type
                            )
                        )
                    }
                    Log.d("AccountRepository", "Room database updated with latest accounts")
                } else {
                    Log.e("AccountRepository", "API Error: Response data is null")
                }
            } else {
                Log.e("AccountRepository", "HTTP Error: ${response.code()} - ${response.message()}")
            }
        } catch (e: Exception) {
            Log.e("AccountRepository", "Exception in refreshAccount", e)
        }
    }
}