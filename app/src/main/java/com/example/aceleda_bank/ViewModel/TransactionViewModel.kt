package com.example.aceleda_bank.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aceleda_bank.DTO.TransactionInfoDTO.TransferRequest
import com.example.aceleda_bank.Model.TransferUiState

import com.example.aceleda_bank.Repository.AccountRepository.AccountRepository;
import com.example.aceleda_bank.Repository.TransferRepository.TransferRepository
import com.example.aceleda_bank.Repository.UserRepository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class TransferStatus {
    object Idle : TransferStatus()
    object Loading : TransferStatus()
    object Success : TransferStatus()
    data class Error(val message: String) : TransferStatus()
}

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val repository: TransferRepository
) : ViewModel() {

    private val _transferStatus = MutableStateFlow<TransferStatus>(TransferStatus.Idle)
    val transferStatus = _transferStatus.asStateFlow()

    val localUser = repository.getLocalTransactions()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            null
        )

    fun refreshProfile() {
        viewModelScope.launch {
            try {
                repository.refreshTransactions()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun transfer(request: TransferRequest) {
        viewModelScope.launch {
            _transferStatus.value = TransferStatus.Loading
            try {
                repository.transfer(request)
                _transferStatus.value = TransferStatus.Success
            } catch (e: Exception) {
                _transferStatus.value = TransferStatus.Error(e.message ?: "Transfer failed")
                e.printStackTrace()
            }
        }
    }

    fun resetTransferStatus() {
        _transferStatus.value = TransferStatus.Idle
    }

    var uiState by mutableStateOf(TransferUiState())
        private set

    fun updateAccountNumber(value: String) {
        uiState = uiState.copy(accountNumber = value)
    }

    fun updateMerchantName(value: String) {
        uiState = uiState.copy(merchantName = value)
    }

    fun updateCurrency(value: String) {
        uiState = uiState.copy(currency = value)
    }

    fun updateAmount(value: String) {
        uiState = uiState.copy(amount = value)
    }

    fun updateSenderName(value: String) {
        uiState = uiState.copy(senderName = value)
    }

    fun updateSenderNumber(value: String) {
        uiState = uiState.copy(senderNumber = value)
    }

    fun updateSenderAccNum(value: String) {
        uiState = uiState.copy(senderAccNum = value)
    }
}
