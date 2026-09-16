package com.example.aceleda_bank.ViewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.aceleda_bank.Repository.QrRepository.QRRepository
import com.example.aceleda_bank.DTO.KhqrDTO.KHQRResponseDTO

@HiltViewModel
class QRViewModel @Inject constructor(
    private val repository: QRRepository
) : ViewModel() {

    private val _qrResult = MutableStateFlow<KHQRResponseDTO?>(null)
    val qrResult: StateFlow<KHQRResponseDTO?> = _qrResult

    fun generateQR(token: String) {
        viewModelScope.launch {
            try {
                val result = repository.generateQR(token)
                _qrResult.value = result
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}