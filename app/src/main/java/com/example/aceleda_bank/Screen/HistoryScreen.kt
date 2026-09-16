package com.example.aceleda_bank.Screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material.icons.outlined.Replay
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.aceleda_bank.Component.Backgroundimage
import com.example.aceleda_bank.Component.DashedDivider
import com.example.aceleda_bank.R
import com.example.aceleda_bank.Room.AccountEntity
import com.example.aceleda_bank.Room.TransactionEntity
import com.example.aceleda_bank.ViewModel.AccountViewModel
import com.example.aceleda_bank.ViewModel.TransactionViewModel
import com.example.aceleda_bank.ViewModel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    navController: NavController,
    viewModel: TransactionViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel(),
    accountViewModel: AccountViewModel = hiltViewModel()
) {
    val transactions by viewModel.localUser.collectAsState(initial = null)
    val user by userViewModel.localUser.collectAsState(initial = null)
    val accounts by accountViewModel.allAccounts.collectAsState(initial = emptyList())

    var searchQuery by remember { mutableStateOf("") }
    var selectedTransaction by remember { mutableStateOf<TransactionEntity?>(null) }
    var showDetailSheet by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.refreshProfile()
        userViewModel.refreshProfile()
        accountViewModel.refreshProfile()
    }

    val filteredTransactions = (transactions ?: emptyList()).filter {
        it.fullNameReceiver.contains(searchQuery, ignoreCase = true) ||
                it.fullNameSender.contains(searchQuery, ignoreCase = true)
    }

    val groupedTransactions = filteredTransactions.groupBy { 
        it.date.split("|").first().trim()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Backgroundimage()

        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier
                            .size(20.dp)
                            .clickable { navController.popBackStack() }
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "History",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.ac_logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(height = 30.dp, width = 40.dp)
                )
            }

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(56.dp),
                placeholder = { Text("Search...", color = Color.Gray) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray) },
                shape = RoundedCornerShape(28.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White.copy(alpha = 0.9f),
                    unfocusedContainerColor = Color.White.copy(alpha = 0.9f),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF2F2F2))
            ) {
                groupedTransactions.forEach { (date, items) ->
                    item {
                        Text(
                            text = date,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFE0E0E0))
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            color = Color.DarkGray,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    items(items, key = { it.id }) { tx ->
                        Column {
                            HistoryTransactionItem(
                                transaction = tx,
                                currentUserPhone = user?.phoneNumber,
                                userAccounts = accounts,
                                onClick = {
                                    selectedTransaction = tx
                                    showDetailSheet = true
                                }
                            )

                            HorizontalDivider(
                                color = Color.LightGray.copy(alpha = 0.5f),
                                thickness = 0.5.dp,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                    }
                }
            }
        }

        if (showDetailSheet && selectedTransaction != null) {
            ModalBottomSheet(
                onDismissRequest = { showDetailSheet = false },
                containerColor = Color.White,
                dragHandle = null,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            ) {
                HistoryDetailContent(selectedTransaction!!, user?.phoneNumber, accounts)
            }
        }
        
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(Color(0xFF362513))
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "SORT", color = Color.White, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
        }
    }
}

@Composable
fun HistoryTransactionItem(
    transaction: TransactionEntity,
    currentUserPhone: String?,
    userAccounts: List<AccountEntity>,
    onClick: () -> Unit
) {
    val cleanSenderAcc = transaction.accountNumberSender.replace(" ", "")
    val cleanUserPhone = currentUserPhone?.replace(" ", "") ?: ""
    
    // Check if sender matches user's phone or any of user's account numbers
    val isSender = cleanSenderAcc == cleanUserPhone || 
                   userAccounts.any { it.accountNumber.replace(" ", "") == cleanSenderAcc }

    Log.d("HistoryLogic", "TX Sender: $cleanSenderAcc | User Phone: $cleanUserPhone | isSender: $isSender")

    val displayLabel = if (isSender) "Transaction QR to" else "Transaction QR from"
    val displayName = if (isSender) transaction.fullNameReceiver else transaction.fullNameSender
    val amountPrefix = if (isSender) "-" else "+"
    val amountColor = if (isSender) Color(0xFFB71C1C) else Color(0xFF2E7D32)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val initials = if (displayName.isNotEmpty()) {
            displayName.split(" ").filter { it.isNotEmpty() }.take(2).map { it.first() }.joinToString("").uppercase()
        } else "TX"

        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color(0xFF5D4037)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = initials, color = Color.White, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(text = displayLabel, color = Color.Gray, fontSize = 12.sp)
            Text(text = displayName, color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }

        Text(
            text = "$amountPrefix${transaction.amount} \u17db",
            color = amountColor,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}

@Composable
fun HistoryDetailContent(tx: TransactionEntity, currentUserPhone: String?, userAccounts: List<AccountEntity>) {
    val cleanSenderAcc = tx.accountNumberSender.replace(" ", "")
    val cleanUserPhone = currentUserPhone?.replace(" ", "") ?: ""
    val isSender = cleanSenderAcc == cleanUserPhone || 
                   userAccounts.any { it.accountNumber.replace(" ", "") == cleanSenderAcc }

    val displayName = if (isSender) tx.fullNameReceiver else tx.fullNameSender
    val symbol = "\u17db"

    Column(modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp)) {
        Row(
            modifier = Modifier.padding(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(64.dp).clip(CircleShape).background(Color(0xFF1976D2)),
                contentAlignment = Alignment.Center
            ) {
                val initials = displayName.split(" ").filter { it.isNotEmpty() }.take(2).map { it.first() }.joinToString("").uppercase()
                Text(text = initials, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = if (isSender) "Transaction QR to" else "Transaction QR from", fontSize = 16.sp, color = Color.Black)
                Text(text = displayName, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Text(
                    text = "${if (isSender) "-" else "+"}${tx.amount} $symbol", 
                    fontSize = 20.sp, 
                    color = if (isSender) Color(0xFFB71C1C) else Color(0xFF2E7D32), 
                    fontWeight = FontWeight.Bold
                )
            }
        }

        DashedDivider(color = Color.LightGray, modifier = Modifier.padding(horizontal = 24.dp))

        Column(modifier = Modifier.padding(24.dp)) {
            if (isSender) {
                HistoryDetailRowContent("Paid From", tx.fullNameSender)
                HistoryDetailRowContent("Account No.", tx.accountNumberSender)
                HistoryDetailRowContent("Debit Amount", "-${tx.amount} KHR", valueColor = Color(0xFFB71C1C))
            } else {
                HistoryDetailRowContent("Received From", tx.fullNameSender)
                HistoryDetailRowContent("Paid To", "Me")
                HistoryDetailRowContent("Amount Received", "+${tx.amount} KHR", valueColor = Color(0xFF2E7D32))
            }

            Spacer(modifier = Modifier.height(16.dp))
            DashedDivider(color = Color.LightGray)
            Spacer(modifier = Modifier.height(16.dp))

            HistoryDetailRowContent("Receiver", tx.fullNameReceiver)
            HistoryDetailRowContent("Amount", "${tx.amount} KHR")

            Spacer(modifier = Modifier.height(16.dp))
            DashedDivider(color = Color.LightGray)
            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Reference No.", color = Color.Gray, fontSize = 14.sp)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "61584433695", color = Color.Black, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color.LightGray)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            HistoryDetailRowContent("Date", tx.date)
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            HistoryActionItem(Icons.Outlined.History, "History")
            HistoryActionItem(Icons.Outlined.Replay, "Repeat")
            HistoryActionItem(Icons.Outlined.PhotoCamera, "Capture")
            HistoryActionItem(Icons.Outlined.Share, "Share")
        }
    }
}

@Composable
fun HistoryDetailRowContent(label: String, value: String, valueColor: Color = Color.Black) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = Color.Gray, fontSize = 14.sp)
        Text(text = ": $value", color = valueColor, fontSize = 14.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun HistoryActionItem(icon: ImageVector, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(imageVector = icon, contentDescription = null, tint = Color(0xFF0D47A1), modifier = Modifier.size(24.dp))
        Text(text = label, color = Color(0xFF0D47A1), fontSize = 12.sp)
    }
}
