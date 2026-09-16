package com.example.aceleda_bank.Screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.aceleda_bank.Component.ShieldIcon
import com.example.aceleda_bank.Component.acledaTextFieldColors
import com.example.aceleda_bank.Navigation.Routes
import com.example.aceleda_bank.ViewModel.AuthViewModel

private val NavyDark = Color(0xFF0D2B5E)

@Composable
fun OTP_SCREEN(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    var otp1 by remember { mutableStateOf("") }
    var otp2 by remember { mutableStateOf("") }
    var otp3 by remember { mutableStateOf("") }
    var otp4 by remember { mutableStateOf("") }
    var otp5 by remember { mutableStateOf("") }
    var otp6 by remember { mutableStateOf("") }

    // Focus Requesters for each field
    val fr1 = remember { FocusRequester() }
    val fr2 = remember { FocusRequester() }
    val fr3 = remember { FocusRequester() }
    val fr4 = remember { FocusRequester() }
    val fr5 = remember { FocusRequester() }
    val fr6 = remember { FocusRequester() }

    // Auto-focus the first field on launch
    LaunchedEffect(Unit) {
        fr1.requestFocus()
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
                text = "ACLEDA Authentication",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.3.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Shield graphic
            ShieldIcon()

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "©ACLEDA Bank Plc. Version 3.1",
                color = Color(0xFFB0C4DE),
                fontSize = 12.sp
            )
        }

        // ── White Card Form ───────────────────────────────────────────────────
        Surface(
            modifier = Modifier
                .height(600.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
            color = Color.White,
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text("Enter OTP Code", fontSize = 20.sp)
                Spacer(Modifier.height(20.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    OutlinedTextField(
                        value = otp1,
                        onValueChange = {
                            if (it.length <= 1 && it.all { char -> char.isDigit() }) {
                                otp1 = it
                                if (it.isNotEmpty()) fr2.requestFocus()
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .focusRequester(fr1),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp),
                        colors = acledaTextFieldColors(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )
                    )

                    OutlinedTextField(
                        value = otp2,
                        onValueChange = {
                            if (it.length <= 1 && it.all { char -> char.isDigit() }) {
                                otp2 = it
                                if (it.isNotEmpty()) fr3.requestFocus()
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .focusRequester(fr2)
                            .onKeyEvent { event ->
                                if (event.type == KeyEventType.KeyDown && event.key == Key.Backspace && otp2.isEmpty()) {
                                    fr1.requestFocus()
                                    true
                                } else {
                                    false
                                }
                            },
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp),
                        colors = acledaTextFieldColors(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )
                    )

                    OutlinedTextField(
                        value = otp3,
                        onValueChange = {
                            if (it.length <= 1 && it.all { char -> char.isDigit() }) {
                                otp3 = it
                                if (it.isNotEmpty()) fr4.requestFocus()
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .focusRequester(fr3)
                            .onKeyEvent { event ->
                                if (event.type == KeyEventType.KeyDown && event.key == Key.Backspace && otp3.isEmpty()) {
                                    fr2.requestFocus()
                                    true
                                } else {
                                    false
                                }
                            },
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp),
                        colors = acledaTextFieldColors(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )
                    )

                    OutlinedTextField(
                        value = otp4,
                        onValueChange = {
                            if (it.length <= 1 && it.all { char -> char.isDigit() }) {
                                otp4 = it
                                if (it.isNotEmpty()) fr5.requestFocus()
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .focusRequester(fr4)
                            .onKeyEvent { event ->
                                if (event.type == KeyEventType.KeyDown && event.key == Key.Backspace && otp4.isEmpty()) {
                                    fr3.requestFocus()
                                    true
                                } else {
                                    false
                                }
                            },
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp),
                        colors = acledaTextFieldColors(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )
                    )

                    OutlinedTextField(
                        value = otp5,
                        onValueChange = {
                            if (it.length <= 1 && it.all { char -> char.isDigit() }) {
                                otp5 = it
                                if (it.isNotEmpty()) fr6.requestFocus()
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .focusRequester(fr5)
                            .onKeyEvent { event ->
                                if (event.type == KeyEventType.KeyDown && event.key == Key.Backspace && otp5.isEmpty()) {
                                    fr4.requestFocus()
                                    true
                                } else {
                                    false
                                }
                            },
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp),
                        colors = acledaTextFieldColors(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )
                    )

                    OutlinedTextField(
                        value = otp6,
                        onValueChange = {
                            if (it.length <= 1 && it.all { char -> char.isDigit() }) {
                                otp6 = it
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .focusRequester(fr6)
                            .onKeyEvent { event ->
                                if (event.type == KeyEventType.KeyDown && event.key == Key.Backspace && otp6.isEmpty()) {
                                    fr5.requestFocus()
                                    true
                                } else {
                                    false
                                }
                            },
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp),
                        colors = acledaTextFieldColors(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )
                    )
                }

                Spacer(Modifier.height(32.dp))

                Button(
                    onClick = {
                        val otpCode = otp1 + otp2 + otp3 + otp4 + otp5 + otp6

                        if (otpCode.length == 6) {

                            viewModel.verifyOtp(otpCode) { success, error, response ->

                                if (success && response != null) {

                                    when (response.action) {

                                        "LOGIN" -> {
                                            navController.navigate(Routes.HOME) {
                                                popUpTo(Routes.LOGIN_SCREEN) { inclusive = true }
                                            }
                                        }

                                        "REGISTER" -> {
                                            navController.navigate(Routes.REGISTER_SCREEN)
                                        }
                                    }

                                } else {
                                    Toast.makeText(context, error ?: "Verification failed", Toast.LENGTH_SHORT).show()
                                }
                            }

                        } else {
                            Toast.makeText(
                                context,
                                "Please enter 6 digits",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(26.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = NavyDark)
                ) {
                    Text(
                        text = "Verify",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(Modifier.height(40.dp))
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OTP_PREVIEW() {
    MaterialTheme {
        OTP_SCREEN(navController = rememberNavController())
    }
}
