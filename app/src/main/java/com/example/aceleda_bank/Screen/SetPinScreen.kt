package com.example.aceleda_bank.Screen

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.aceleda_bank.Component.PinKeypad
import com.example.aceleda_bank.Navigation.Routes
import com.example.aceleda_bank.ViewModel.AuthViewModel
import com.example.aceleda_bank.ViewModel.UserViewModel
import com.example.aceleda_bank.dataStore.TokenManager

private val NavyDark   = Color(0xFF0D2B5E)
@Composable
fun SetPinScreen(
    navController: NavController,
    viewModel: AuthViewModel=hiltViewModel(),
    onNextClick: (String) -> Unit = {}
) {
    var pin by remember { mutableStateOf("") }

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
                text = "Set screen lock",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Normal
            )

            Text(
                text = "For security, set PIN",
                color = Color.White,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(64.dp))

            // PIN Display (Custom horizontal line)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(Color.Gray),
                contentAlignment = Alignment.Center
            ) {

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = if (pin.isEmpty()) "" else "*".repeat(pin.length),
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

            Text(
                text = "PIN must be at least 4 digits",
                color = Color.Gray,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 16.dp)
            )

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
                        .clickable { pin = "" }
                        .padding(8.dp)
                )

                Button(
                    onClick = { if (pin.length >= 4)
                        viewModel.setPinTemp(pin)
                        onNextClick(pin)
                        navController.navigate(Routes.CONFIRM_SCREEN)
                              },
                    enabled = pin.length >= 4,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF3C4043),
                        disabledContainerColor = Color.White,
                        contentColor = Color.White,
                        disabledContentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Next")
                }
            }
        }

        // Keypad
        PinKeypad(
            onNumberClick = { if (pin.length < 6) pin += it },
            onBackspaceClick = { if (pin.isNotEmpty()) pin = pin.dropLast(1) },
            onDoneClick = { if (pin.length >= 4) onNextClick(pin) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SetPinScreenPreview() {
    SetPinScreen(navController = NavController(LocalContext.current))
}
