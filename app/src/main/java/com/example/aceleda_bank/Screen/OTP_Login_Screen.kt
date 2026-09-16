package com.example.aceleda_bank.Screen
import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.aceleda_bank.ViewModel.AuthViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.example.aceleda_bank.Component.acledaTextFieldColors
import com.example.aceleda_bank.Component.FieldLabel
import com.example.aceleda_bank.Component.ShieldIcon
import com.example.aceleda_bank.Navigation.Routes

private val NavyDark   = Color(0xFF0D2B5E)



// ── Screen ────────────────────────────────────────────────────────────────────
@Composable
fun PhoneNumber(
    navController: NavController,
    viewModel: AuthViewModel = viewModel()
) {
    val context= LocalContext.current
    var phoneNumber by remember { mutableStateOf("+855 23 454 5768") }
    val activity = LocalActivity.current


    val callbacks =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(
                credential: PhoneAuthCredential
            ) {
                Log.d("OTP", "onVerificationCompleted")
            }

            override fun onVerificationFailed(
                e: FirebaseException
            ) {
                Log.e("OTP", "onVerificationFailed: ${e.message}", e)
                Toast.makeText(context, "Verification failed: ${e.message}", Toast.LENGTH_LONG).show()
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                Log.d("OTP", "onCodeSent: $verificationId")
                viewModel.storedVerificationId = verificationId
                navController.navigate(Routes.OTP_SCREEN)
            }
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NavyDark)
            .verticalScroll(rememberScrollState())
    ) {

        // ── Header ────────────────────────────────────────────────────────────
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp, bottom = 36.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text       = "ACLEDA Authentication",
                color      = Color.White,
                fontSize   = 20.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.3.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Shield graphic
            ShieldIcon()

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text     = "©ACLEDA Bank Plc. Version 3.1",
                color    = Color(0xFFB0C4DE),
                fontSize = 12.sp
            )
        }

        // ── White Card Form ───────────────────────────────────────────────────
        Surface(
            modifier = Modifier
                .height(600.dp)
                .fillMaxWidth(),
            shape    = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
            color    = Color.White,
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 32.dp)
            ) {

                // User ID
                FieldLabel("Phone Number")
                Spacer(Modifier.height(4.dp))
                OutlinedTextField(
                    value         = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    modifier      = Modifier.fillMaxWidth(),
                    singleLine    = true,
                    shape         = RoundedCornerShape(8.dp),
                    colors        = acledaTextFieldColors(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                )

                Spacer(Modifier.height(16.dp))

                Spacer(Modifier.height(32.dp))


                // Confirm Button
                Button(
                    onClick = {
                        val sanitizedPhone = phoneNumber.replace("\\s".toRegex(), "")
                        viewModel.phoneNumber = sanitizedPhone // Store sanitized number for later use
                        
                        activity?.let{
                            viewModel.sendOTP(
                                sanitizedPhone,
                                it,
                                callbacks
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape  = RoundedCornerShape(26.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = NavyDark)
                ) {
                    Text(
                        text       = "Confirm",
                        color      = Color.White,
                        fontSize   = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(Modifier.height(40.dp))
            }
        }
    }
}




// ── Preview ───────────────────────────────────────────────────────────────────
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PhoneNumberPreview() {
    MaterialTheme {
        PhoneNumber(navController = NavController(LocalContext.current))
    }
}
