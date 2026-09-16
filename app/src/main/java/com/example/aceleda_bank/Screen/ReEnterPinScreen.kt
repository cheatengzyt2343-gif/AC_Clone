package com.example.aceleda_bank.Screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.aceleda_bank.Component.PinKeypad
import com.example.aceleda_bank.DTO.UserInfoDTO.SetPinRequestDTO
import com.example.aceleda_bank.Navigation.Routes
import com.example.aceleda_bank.ViewModel.AuthViewModel
import com.example.aceleda_bank.ViewModel.UserViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

private val NavyDark   = Color(0xFF0D2B5E)
@Composable
fun ReEnterPinScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel(),
    onConfirmClick: (String) -> Unit = {}
) {
    var confirmPin by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = NavyDark)
    ) {
        // Upper part
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            Icon(
                imageVector = Icons.Outlined.Lock,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Re-enter your PIN",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Normal
            )

            Spacer(modifier = Modifier.height(80.dp))

            // PIN Display
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(Color.Gray),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = if (confirmPin.isEmpty()) "" else "*".repeat(confirmPin.length),
                        color = Color.White,
                        fontSize = 32.sp,
                        letterSpacing = 8.sp
                    )
                    // Cursor
                    Box(
                        modifier = Modifier
                            .width(2.dp)
                            .height(30.dp)
                            .background(Color(0xFF8AB4F8))
                    )
                }

                // Bottom line
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(Color(0xFF8AB4F8))
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Action row above keypad
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Clear",
                    color = Color.White,
                    modifier = Modifier
                        .clickable { confirmPin = "" }
                        .padding(8.dp)
                )

                Button(
                    onClick = {

                        if (confirmPin == viewModel.pin) {

                            val token = viewModel.firebaseToken

                            if (token != null) {

                                viewModel.setPin(SetPinRequestDTO(
                                    firebaseToken = token,
                                    pin = viewModel.pin,
                                    confirmPin = confirmPin
                                )) { success ->

                                    if (success) {
                                        onConfirmClick(confirmPin)
                                        navController.navigate(Routes.CREATED_SCREEN)
                                    } else {
                                        Log.e("PIN", "Failed to set PIN")
                                    }
                                }

                            } else {
                                Log.e("PIN", "Token is null")
                            }

                        } else {
                            Log.e("PIN", "PIN mismatch")
                        }
                    }
                ) {
                    Text("Confirm", fontWeight = FontWeight.Bold)
                }
            }
        }

        // Keypad
        PinKeypad(
            onNumberClick = { if (confirmPin.length < 6) confirmPin += it },
            onBackspaceClick = { if (confirmPin.isNotEmpty()) confirmPin = confirmPin.dropLast(1) },
            onDoneClick = { if (confirmPin == viewModel.pin) onConfirmClick(confirmPin) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ReEnterPinScreenPreview() {
    ReEnterPinScreen(navController = NavController(LocalContext.current))
}
