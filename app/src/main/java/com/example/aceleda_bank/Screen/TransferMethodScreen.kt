package com.example.aceleda_bank.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.aceleda_bank.Component.Backgroundimage
import com.example.aceleda_bank.Navigation.Routes
import com.example.aceleda_bank.R

@Composable
fun TransferMethodScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Backgroundimage()

        Column(modifier = Modifier.fillMaxSize()) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp, start = 16.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { navController.navigate(Routes.HOME) }
                )
                Text(
                    text = "Transfers",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 8.dp)
                )
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Info",
                    tint = Color.White,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(20.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(id = R.drawable.ac_logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(40.dp)
                )
            }

            // Illustration
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ac_super_app),
                    contentDescription = null,
                    modifier = Modifier.size(180.dp),
                    contentScale = ContentScale.Fit
                )
            }

            // Methods List
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                    .background(Color(0xFFF5F5F5))
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        TransferMethodItem(
                            icon = R.drawable.earth,
                            title = "Own Accounts",
                            subtitle = "Own account transfers",
                            onClick = { navController.navigate(Routes.TRANSFER) }
                        )
                    }
                    item {
                        TransferMethodItem(
                            icon = R.drawable.aceleda,
                            title = "ACLEDA Accounts | Phone",
                            subtitle = "Transfer to ACLEDA accounts | phone numbers",
                            onClick = { navController.navigate(Routes.TRANSFER) }
                        )
                    }
                    item {
                        TransferMethodItem(
                            icon = R.drawable.visa,
                            title = "Local Transfers",
                            subtitle = "Transfer to banks | MFIs | wallets",
                            showArrow = true,
                            onClick = { navController.navigate(Routes.TRANSFER) }
                        )
                    }
                    item {
                        TransferMethodItem(
                            icon = R.drawable.master,
                            title = "International Transfers",
                            subtitle = "Transfer worldwide to banks | agents",
                            showArrow = true,
                            onClick = { navController.navigate(Routes.TRANSFER) }
                        )
                    }
                    item {
                        TransferMethodItem(
                            icon = R.drawable.visa,
                            title = "Card Transfers",
                            subtitle = "Transfer to other bank cards",
                            showArrow = true,
                            hasBadges = true,
                            onClick = { navController.navigate(Routes.TRANSFER) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TransferMethodItem(
    icon: Int,
    title: String,
    subtitle: String,
    showArrow: Boolean = false,
    hasBadges: Boolean = false,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFF0F0F0).copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.size(28.dp)
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF18334F)
                )
                if (hasBadges) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.visa),
                            contentDescription = null,
                            modifier = Modifier.height(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Image(
                            painter = painterResource(id = R.drawable.master),
                            contentDescription = null,
                            modifier = Modifier.height(14.dp)
                        )
                    }
                }
            }
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }

        if (showArrow) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = Color(0xFFD4AF37)
            )
        }
    }
}
