package com.example.aceleda_bank.ViewModel


import android.app.Activity
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aceleda_bank.DTO.AuthDTO.LoginResponse
import com.example.aceleda_bank.DTO.AuthDTO.PinRequestDTO
import com.example.aceleda_bank.DTO.AuthDTO.RegisterRequestDTO
import com.example.aceleda_bank.DTO.UserInfoDTO.SetPinRequestDTO
import com.example.aceleda_bank.Repository.AuthRepository.AuthRepository
import com.example.aceleda_bank.dataStore.TokenManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val auth: FirebaseAuth = Firebase.auth
    var storedVerificationId by mutableStateOf<String?>(null)
    var pin by mutableStateOf("")

    var phoneNumber by mutableStateOf("")

    init {
        viewModelScope.launch {
            tokenManager.phoneNumber.collect { savedPhone ->
                if (savedPhone != null && phoneNumber.isEmpty()) {
                    phoneNumber = savedPhone
                }
            }
        }
    }

    var firebaseToken: String? = null
        private set

    fun setFirebaseToken(token: String) {
        firebaseToken = token
    }

    fun setVerificationId(id: String) {
        storedVerificationId = id
    }

    fun verifyOtp(otp: String, onResult: (Boolean, String?, LoginResponse?) -> Unit) {

        val verificationId = storedVerificationId
        Log.d("OTP", "storedVerificationId = $storedVerificationId")

        if (verificationId == null) {
            onResult(false, "Verification ID is null", null)
            return
        }

        val credential = PhoneAuthProvider.getCredential(verificationId, otp)

        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {

                    Firebase.auth.currentUser
                        ?.getIdToken(true)
                        ?.addOnSuccessListener { result ->

                            val token = result.token

                            setFirebaseToken(token.toString())

                            if (token != null) {

                                firebaseLogin(token) { response ->
                                    onResult(true, null, response)
                                }
                            }
                        }

                } else {
                    onResult(false, task.exception?.message, null)
                }
            }
    }

    fun sendOTP(
        phoneNumber: String,
        activity: Activity,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ) {

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callbacks)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun firebaseLogin(
        firebaseToken: String,
        onResult: (LoginResponse) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = authRepository.firebaseLogin(firebaseToken)
                // Always save the token if it's provided, so registration flow 
                // has a valid session for subsequent actions like profile fetching.
                if (response.token.isNotEmpty()) {
                    tokenManager.saveToken(response.token)
                }
                onResult(response)
            } catch (e: Exception) {
                Log.e("LOGIN", e.message ?: "Error")
            }
        }
    }

    private val _registerSuccess = MutableStateFlow(false)
    val registerSuccess = _registerSuccess.asStateFlow()

    fun register(request: RegisterRequestDTO) {
        viewModelScope.launch {
            try {
                tokenManager.savePhoneNumber(request.phoneNumber)
                authRepository.register(request)
                _registerSuccess.value = true
            } catch (e: Exception) {
                _registerSuccess.value = false
            }
        }
    }

    fun verifyPin(pin: PinRequestDTO, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val result = authRepository.verifyPin(pin)
                onResult(result == true)
            } catch (e: Exception) {
                Log.e("VERIFY_PIN", e.message ?: "Error")
                onResult(false)
            }
        }
    }

    fun checkUser(phone: String,onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
               val result = authRepository.checkUser(phone)
                onResult(result==true)
            } catch (e: Exception) {
                Log.e("REGISTER", e.message ?: "Error")
                onResult(false)
            }
        }
    }

    fun setPinTemp(value: String) {
        pin = value
    }

    fun setPin(
        request: SetPinRequestDTO,
        onResult: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val result = authRepository.setPin(request)
                onResult(result)
            } catch (e: Exception) {
                Log.e("SET_PIN", e.message ?: "Error")
                onResult(false)
            }
        }
    }

    fun savePhone(phone: String) {
        viewModelScope.launch {
            tokenManager.savePhoneNumber(phone)
        }
    }

    fun formatPhoneNumber(phone: String): String {
        return phone
            .replace(" ", "")
            .replace("-", "")
    }
}
