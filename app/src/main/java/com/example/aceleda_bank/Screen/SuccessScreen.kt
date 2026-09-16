package com.example.aceleda_bank.Screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.aceleda_bank.Component.Backgroundimage
import com.example.aceleda_bank.Component.DashedDivider
import com.example.aceleda_bank.R
import com.example.aceleda_bank.ViewModel.TransactionViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.ui.platform.LocalLocale

@Composable
fun SuccessScreen(
    navController: NavController,
    transferViewModel: TransactionViewModel = hiltViewModel()
) {
    val uiState = transferViewModel.uiState
    val symbol = if (uiState.currency == "USD") "$" else "៛"
    val dateString = SimpleDateFormat("dd-MMM-yyyy | hh:mm a", LocalLocale.current.platformLocale).format(Date())

    Box(modifier = Modifier.fillMaxSize()) {
        Backgroundimage()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.size(20.dp))
            // ACLEDA Logo
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.size(width = 200.dp,height = 80.dp)
            )

            // Success Indicator (Ring + Checkmark)
            Box(contentAlignment = Alignment.Center) {
                Canvas(modifier = Modifier.size(90.dp)) {
                    drawArc(
                        color = Color.White.copy(alpha = 0.2f),
                        startAngle = 0f,
                        sweepAngle = 360f,
                        useCenter = false,
                        style = Stroke(width = 4.dp.toPx())
                    )
                    drawArc(
                        color = Color(0xFFC9A227), // Gold
                        startAngle = -90f,
                        sweepAngle = 280f,
                        useCenter = false,
                        style = Stroke(width = 4.dp.toPx())
                    )
                }
                Surface(
                    modifier = Modifier.size(64.dp),
                    shape = CircleShape,
                    color = Color(0xFF0D2B5E) // Navy Dark
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Success",
                        tint = Color(0xFFC9A227),
                        modifier = Modifier.padding(14.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Receipt "Ticket" Card
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth()
            ) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                    color = Color.White
                ) {
                    Column(
                        modifier = Modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Success",
                            color = Color(0xFF1A1A1A),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Transferred To Box
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            color = Color(0xFFF8F9FA),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFFE57385)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = if (uiState.merchantName.isNotEmpty()) uiState.merchantName.take(1).uppercase() else "R",
                                        color = Color.White,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = "Transferred to",
                                        color = Color.Gray,
                                        fontSize = 12.sp
                                    )
                                    Text(
                                        text = uiState.merchantName,
                                        color = Color(0xFF0D2B5E),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp
                                    )
                                    Text(
                                        text = "${uiState.amount} $symbol",
                                        color = Color(0xFFB71C1C),
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        DashedDivider(color = Color.LightGray)
                        Spacer(modifier = Modifier.height(16.dp))

                        // Transaction Details
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            color = Color(0xFFF8F9FA),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                ReceiptRow(label = "From Account", value = uiState.senderName, showArrow = true)
                                ReceiptRow(label = "Account No.", value = uiState.senderNumber)
                                ReceiptRow(label = "Debit Amount", value = "-${uiState.amount} $symbol", valueColor = Color(0xFFB71C1C))
                                
                                Spacer(modifier = Modifier.height(8.dp))
                                DashedDivider(color = Color.LightGray.copy(alpha = 0.5f))
                                Spacer(modifier = Modifier.height(8.dp))

                                ReceiptRow(label = "To Account", value = uiState.merchantName)
                                ReceiptRow(label = "Account No.", value = uiState.accountNumber)

                                Spacer(modifier = Modifier.height(8.dp))
                                DashedDivider(color = Color.LightGray.copy(alpha = 0.5f))
                                Spacer(modifier = Modifier.height(8.dp))

                                ReceiptRow(label = "Reference No.", value = "61577502706")
                                ReceiptRow(label = "Date", value = dateString)
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
                
                // Perforated edge effect
                PerforatedEdge(modifier = Modifier.fillMaxWidth())
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Bottom Navigation-like Bar
            Surface(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth()
                    .height(80.dp),
                shape = RoundedCornerShape(40.dp),
                color = Color.White,
                shadowElevation = 4.dp
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ActionItem(icon = Icons.Default.Home, label = "Home", onClick = { navController.popBackStack(navController.graph.startDestinationId, false) })
                    ActionItem(icon = Icons.Default.Refresh, label = "Repeat")
                    ActionItem(icon = Icons.Default.AccountBalanceWallet, label = "Accounts")
                    ActionItem(icon = Icons.Default.Share, label = "Share")
                }
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun ReceiptRow(
    label: String,
    value: String,
    valueColor: Color = Color(0xFF1A1A1A),
    showArrow: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = Color.Gray,
            fontSize = 13.sp,
            modifier = Modifier.weight(1.2f)
        )
        Text(
            text = ": ",
            color = Color.Gray,
            fontSize = 13.sp
        )
        Row(
            modifier = Modifier.weight(2f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = value,
                color = valueColor,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )
            if (showArrow) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = null,
                    tint = Color(0xFFC9A227),
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
fun PerforatedEdge(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.height(16.dp)) {
        val circleRadius = 6.dp.toPx()
        val circleSpacing = 8.dp.toPx()
        val circleCount = (size.width / (circleRadius * 2 + circleSpacing)).toInt()
        
        // Background white of the "ticket"
        drawRect(color = Color.White, size = size)
        
        // Draw the "cutouts"
        for (i in 0..circleCount) {
            val x = i * (circleRadius * 2 + circleSpacing) + circleRadius + (circleSpacing/2)
            drawCircle(
                color = Color.Black.copy(alpha = 0.9f),
                radius = circleRadius,
                center = Offset(x, size.height)
            )
        }
    }
}

@Composable
fun ActionItem(icon: ImageVector, label: String, onClick: () -> Unit = {}) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = Color(0xFF0D2B5E),
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = label,
            color = Color(0xFF0D2B5E),
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SuccessScreenPreview() {
    SuccessScreen(navController = NavController(LocalContext.current))
}
