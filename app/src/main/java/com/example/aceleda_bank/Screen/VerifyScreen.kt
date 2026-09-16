package com.example.aceleda_bank.Screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.aceleda_bank.Component.Backgroundimage
import com.example.aceleda_bank.Component.DashedDivider
import com.example.aceleda_bank.DTO.TransactionInfoDTO.TransferRequest
import com.example.aceleda_bank.Navigation.Routes
import com.example.aceleda_bank.R
import com.example.aceleda_bank.ViewModel.TransactionViewModel
import com.example.aceleda_bank.ViewModel.TransferStatus

@Composable
fun VerifyScreen(
    navController: NavController,
    transferViewModel: TransactionViewModel = hiltViewModel()
) {
    val uiState = transferViewModel.uiState
    val transferStatus by transferViewModel.transferStatus.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(transferStatus) {
        when (transferStatus) {
            is TransferStatus.Success -> {
                navController.navigate(Routes.SUCCESS_SCREEN) {
                    popUpTo(Routes.PAYSCREEN) { inclusive = true }
                }
                transferViewModel.resetTransferStatus()
            }
            is TransferStatus.Error -> {
                Toast.makeText(context, (transferStatus as TransferStatus.Error).message, Toast.LENGTH_LONG).show()
                transferViewModel.resetTransferStatus()
            }
            else -> {}
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Backgroundimage()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.chevron_left),
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier
                            .size(32.dp)
                            .clickable { if (transferStatus !is TransferStatus.Loading) navController.popBackStack() }
                    )
                    Text(
                        text = "Transfers",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.ac_logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Please verify transaction",
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Transaction Card
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    // Recipient Section
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFE57385)), // Pinkish color for initials
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (uiState.merchantName.isNotEmpty()) uiState.merchantName.take(1) else "R",
                                color = Color.White,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                text = uiState.merchantName,
                                color = Color(0xFF0D2B5E),
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                            Text(
                                text = "${uiState.accountNumber} (${uiState.currency})",
                                color = Color.Gray,
                                fontSize = 14.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Amount Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Amount", color = Color.Gray, fontSize = 14.sp)
                        val symbol = if (uiState.currency == "USD") "$" else "៛"
                        Text(
                            text = "${uiState.amount} $symbol",
                            color = Color(0xFFC9A227), // Gold color
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    DashedDivider(color = Color.LightGray)
                    Spacer(modifier = Modifier.height(16.dp))

                    // Transfer From Section
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Transfer From", color = Color.Gray, fontSize = 14.sp)
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = uiState.senderName,
                                color = Color(0xFF0D2B5E),
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                            Text(
                                text = uiState.senderNumber,
                                color = Color.Gray,
                                fontSize = 12.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    DashedDivider(color = Color.LightGray)
                    Spacer(modifier = Modifier.height(16.dp))

                    // Details Section
                    DetailRow(label = "Remark", value = "Transfer")
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Confirm Button
            Button(
                onClick = {
                    transferViewModel.transfer(
                        TransferRequest(
                            receiverAccountNum = uiState.accountNumber,
                            amount = uiState.amount.toLongOrNull() ?: 0L,
                            senderAccountNum = uiState.senderAccNum
                        )
                    )
                },
                enabled = transferStatus !is TransferStatus.Loading,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 100.dp)
                    .height(55.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF362513)),
                shape = RoundedCornerShape(16.dp)
            ) {
                if (transferStatus is TransferStatus.Loading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Text(text = "Confirm", color = Color.White, fontSize = 18.sp)
                }
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String, isBold: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = Color.Gray, fontSize = 14.sp)
        Text(
            text = value,
            color = if (isBold) Color.Black else Color.DarkGray,
            fontSize = 14.sp,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewVerifyScreen() {
    VerifyScreen(navController = rememberNavController())
}
