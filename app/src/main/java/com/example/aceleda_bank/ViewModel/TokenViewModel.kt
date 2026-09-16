package com.example.aceleda_bank.ViewModel

import androidx.lifecycle.ViewModel
import com.example.aceleda_bank.dataStore.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TokenViewModel @Inject constructor(
    private val tokenManager: TokenManager
) : ViewModel() {

    val token = tokenManager.token
}