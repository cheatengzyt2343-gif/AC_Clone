package com.example.aceleda_bank.Repository.UserRepository

import android.util.Log
import com.example.aceleda_bank.DAO.UserDao
import com.example.aceleda_bank.Room.UserEntity
import com.example.aceleda_bank.dataStore.TokenManager
import com.example.aceleda_bank.network.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val api: ApiService,
    private val userDao: UserDao,
    private val tokenManager: TokenManager
) {

    fun getLocalUser(): Flow<UserEntity?> {
        return userDao.getUser()
    }

    suspend fun refreshUser() {
        Log.d("UserRepository", "refreshUser: Started")

        val token = tokenManager.token.firstOrNull()
        Log.d("UserRepository", "refreshUser: Token is [$token]")

        if (token.isNullOrEmpty()) {
            Log.e("UserRepository", "refreshUser: Aborting - Token is null or empty.")
            return
        }

        try {
            val response = api.getUserInfo("Bearer $token")

            Log.d("UserRepository", "refreshUser: API Response code: ${response.code()}")

            if (response.isSuccessful) {

                val apiResponse = response.body()

                if (apiResponse != null) {

                    val body = apiResponse.data

                    if (body != null) {
                        // Clear existing user data in Room
                        userDao.clearUser()

                        userDao.insertUser(
                            UserEntity(
                                firstName = body.firstName,
                                lastName = body.lastName,
                                phoneNumber = body.phoneNumber,
                                gender = body.gender,
                                dob = body.dob,
                                profession = body.profession,
                                nationality = body.nationality,
                                address = body.address,
                                imageUrl = body.imageUrl
                            )
                        )

                        Log.d("UserRepository", "SUCCESS! User inserted into Room.")
                    } else {
                        Log.e("UserRepository", "User data is null")
                    }

                } else {
                    Log.e("UserRepository", "API Error: ${apiResponse?.message}")
                }

            } else {
                Log.e("UserRepository", "HTTP Error: ${response.errorBody()?.string()}")
            }

        } catch (e: Exception) {
            Log.e("UserRepository", "Exception occurred", e)
        }
    }
}