package com.example.aceleda_bank.Screen

import android.graphics.Bitmap
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.VerticalAlignBottom
import androidx.compose.material.icons.outlined.LocalOffer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.aceleda_bank.Component.Backgroundimage
import com.example.aceleda_bank.Component.DashedDivider
import com.example.aceleda_bank.Component.TopBar
import com.example.aceleda_bank.R
import com.example.aceleda_bank.Util.QRGenerator
import com.example.aceleda_bank.ViewModel.AccountViewModel
import com.example.aceleda_bank.ViewModel.QRViewModel
import com.example.aceleda_bank.ViewModel.TokenViewModel
import com.example.aceleda_bank.ViewModel.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun QR_Code(
    navController: NavController,
    qrViewModel: QRViewModel = hiltViewModel(),
    tokenViewModel: TokenViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel(),
    accountViewModel: AccountViewModel = hiltViewModel()
) {
    Backgroundimage()
    var qrBitmap by remember { mutableStateOf<Bitmap?>(null) }
    val token by tokenViewModel.token.collectAsState(initial = null)
    val qrResult by qrViewModel.qrResult.collectAsState()
    val user by userViewModel.localUser.collectAsState()
    val accounts by accountViewModel.allAccounts.collectAsState()

    LaunchedEffect(token) {
        token?.let { qrViewModel.generateQR(it) }
    }

    LaunchedEffect(qrResult) {
        qrResult?.let { response ->
            if (!response.qrData.isNullOrEmpty()) {
                val bitmap = withContext(Dispatchers.Default) {
                    QRGenerator.generate(response.qrData)
                }
                qrBitmap = bitmap
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        TopBar(modifier = Modifier.align(Alignment.TopCenter), "My KHQR", navController)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 130.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Main QR Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column {
                    // KHQR Red Header
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFE31E24))
                            .padding(vertical = 14.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "KHQR",
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    }

                    Column(
                        modifier = Modifier.padding(top = 24.dp, bottom = 30.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "${user?.firstName ?: ""} ${user?.lastName ?: ""}".uppercase().trim().ifEmpty { "USER NAME" },
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color.Black,
                            modifier = Modifier.align(Alignment.Start).padding(start = 24.dp)
                        )

                        Text(
                            text = "0",
                            fontWeight = FontWeight.Bold,
                            fontSize = 26.sp,
                            color = Color.Black,
                            modifier = Modifier.align(Alignment.Start).padding(start = 24.dp, top = 4.dp)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        DashedDivider(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            color = Color.LightGray.copy(alpha = 1f),
                            dashWidth = 6.dp,
                            dashGap = 4.dp
                        )

                        // QR Code Container
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.size(330.dp)
                        ) {
                            qrBitmap?.let {
                                Image(
                                    bitmap = it.asImageBitmap(),
                                    contentDescription = "QR Code",
                                    modifier = Modifier.fillMaxSize()
                                )
                                // KHQR/Bakong Center Logo
                                Box(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(CircleShape)
                                        .background(Color.White)
                                        .border(2.dp, Color.White, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        painter = painterResource(R.drawable.bakong),
                                        contentDescription = null,
                                        modifier = Modifier.size(42.dp).clip(CircleShape)
                                    )
                                }
                            } ?: CircularProgressIndicator(color = Color(0xFFE31E24))
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(7.dp))


            // Default Accounts Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
            ) {
                Text(
                    text = "Default Accounts",
                    color = Color.White,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(bottom = 8.dp, start = 4.dp),
                    fontWeight = FontWeight.Bold
                )
                
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.Black.copy(alpha = 0.5f))
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            if (accounts.isEmpty()) {
                                Text(text = "096 576 3438 (KHR)", color = Color.White, fontSize = 15.sp)
                                Text(text = "096 576 3438 (USD)", color = Color.White, fontSize = 15.sp, modifier = Modifier.padding(top = 4.dp))
                            } else {
                                accounts.forEachIndexed { index, account ->
                                    if (index < 2) {
                                        Text(
                                            text = "${account.accountNumber} (${account.currency})",
                                            color = Color.White,
                                            fontSize = 15.sp,
                                            modifier = Modifier.padding(vertical = 2.dp)
                                        )
                                    }
                                }
                            }
                        }
                        
                        // Switch Account Icon
                        Surface(
                            modifier = Modifier.size(42.dp),
                            shape = CircleShape,
                            color = Color.White.copy(alpha = 0.1f),
                            border = BorderStroke(0.5.dp, Color.White.copy(alpha = 0.3f))
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    painter = painterResource(id = R.drawable.synwallet),
                                    contentDescription = "Switch Account",
                                    tint = Color.White,
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Bottom Action Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 50.dp, start = 30.dp, end = 30.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                QRActionButton(
                    icon = Icons.Default.VerticalAlignBottom,
                    label = "SAVE",
                    onClick = { /* Save logic */ }
                )
                QRActionButton(
                    icon = Icons.Outlined.LocalOffer,
                    label = "SET AMOUNT",
                    onClick = { /* Set amount logic */ }
                )
                QRActionButton(
                    icon = Icons.Default.Share,
                    label = "SHARE",
                    onClick = { /* Share logic */ }
                )
            }
        }
    }
}

@Composable
fun QRActionButton(icon: ImageVector, label: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Surface(
            shape = CircleShape,
            color = Color.White.copy(alpha = 0.15f),
            modifier = Modifier.size(65.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(14.dp))
        Text(
            text = label,
            color = Color.White,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp
        )
    }
}
