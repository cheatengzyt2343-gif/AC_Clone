package com.example.aceleda_bank.Repository.TransferRepository

import android.util.Log
import com.example.aceleda_bank.DAO.AccountDao
import com.example.aceleda_bank.DAO.TransactionDao
import com.example.aceleda_bank.DTO.TransactionInfoDTO.TransferRequest
import com.example.aceleda_bank.DTO.TransactionInfoDTO.TransferResponse
import com.example.aceleda_bank.Room.AccountEntity
import com.example.aceleda_bank.Room.TransactionEntity
import com.example.aceleda_bank.dataStore.TokenManager
import com.example.aceleda_bank.network.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class TransferRepository @Inject constructor(
    private val api: ApiService,
    private val transferDao: TransactionDao,
    private val tokenManager: TokenManager
) {

    fun getLocalTransactions(): Flow<List<TransactionEntity>> {
        return transferDao.getHistory()
    }

    suspend fun refreshTransactions() {

        val token = tokenManager.token.firstOrNull()
        if (token.isNullOrEmpty()) return

        try {
            val response = api.getTransactionInfo("Bearer $token")

            if (response.isSuccessful) {
                val body = response.body()?.data ?: return
                body.forEach { item ->

                    transferDao.insertHistory(
                        TransactionEntity(
                            id = item.id,
                            accountNumberSender = item.accountNumberSender,
                            fullNameSender = item.fullNameSender,
                            amount = item.amount,
                            fullNameReceiver = item.fullNameReceiver,
                            date = item.date
                        )
                    )
                }
            }

        } catch (e: Exception) {
            Log.e("TransferRepository", "Exception occurred", e)
        }
    }

    suspend fun transfer(request: TransferRequest): TransferResponse {
        val token = tokenManager.token.firstOrNull()
        if (token.isNullOrEmpty()) throw Exception("Token is null or empty")
        val response = api.transfer("Bearer $token", request)
        if (response.isSuccessful) {
            return response.body()?.data ?: throw Exception("Response data is null")
        } else {
            throw Exception("Response is not successful")
        }
    }
}