package com.example.aceleda_bank.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Backspace
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.aceleda_bank.Component.Backgroundimage
import com.example.aceleda_bank.DTO.TransactionInfoDTO.TransferRequest
import com.example.aceleda_bank.Navigation.Routes
import com.example.aceleda_bank.R
import com.example.aceleda_bank.Room.AccountEntity
import com.example.aceleda_bank.ViewModel.AccountViewModel
import com.example.aceleda_bank.ViewModel.TransactionViewModel
import com.example.aceleda_bank.ViewModel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PayScreen(
    navController: NavController,
    transferViewModel: TransactionViewModel = hiltViewModel(),
    accountViewModel: AccountViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel()
) {
    var amount by remember { mutableStateOf("0") }
    val scrollState = rememberScrollState()

    val accounts by accountViewModel.allAccounts.collectAsState()
    var selectedAccount by remember { mutableStateOf<AccountEntity?>(null) }
    var showAccountSheet by remember { mutableStateOf(false) }

    // Initialize selectedAccount with the first available account if none selected
    LaunchedEffect(accounts) {
        if (selectedAccount == null && accounts.isNotEmpty()) {
            selectedAccount = accounts.first()
        }
    }

    //Fetch account
    accountViewModel.localAccount.collectAsState()
    LaunchedEffect(Unit) {
        accountViewModel.refreshProfile()
    }

    //get current login user
    val user by userViewModel.localUser.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Backgroundimage()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp, start = 16.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier
                            .size(32.dp)
                            .clickable { navController.popBackStack() }
                    )
                    Text(
                        text = "Scan QR",
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
                }
                Image(
                    painter = painterResource(id = R.drawable.ac_logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Profile Section
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.profile),
                        contentDescription = "Profile",
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = transferViewModel.uiState.merchantName,
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Account Number: ${transferViewModel.uiState.accountNumber}",
                    color = Color.LightGray,
                    fontSize = 10.sp
                )
                Box(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .background(Color.DarkGray.copy(alpha = 0.5f), RoundedCornerShape(20.dp))
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text(text = "Aceleda Bank", color = Color.White, fontSize = 10.sp)
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    ActionIcon(icon = R.drawable.chat, label = "Purpose")
                    Spacer(modifier = Modifier.width(30.dp))
                    ActionIcon(icon = R.drawable.like, label = "Favorites")
                }
            }


            // Pay From Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clickable { showAccountSheet = true }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Pay From", color = Color.LightGray, fontSize = 14.sp)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = selectedAccount?.accountNumber ?: "Select Account",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            tint = Color.Yellow
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (selectedAccount != null) "${selectedAccount!!.balance} ${selectedAccount!!.currency}" else "******",
                        color = Color.LightGray,
                        modifier = Modifier.padding(end = 4.dp)
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.eyes),
                        contentDescription = null,
                        tint = Color.LightGray,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            // Keypad Section
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Amount Display
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .background(Color(0xFFE5E5E5), RoundedCornerShape(25.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        val currency = transferViewModel.uiState.currency
                        val dis_currency:String = if(currency=="USD"){
                            "$"
                        }else{
                            "៛"
                        }
                        Text(
                            text = "$amount $dis_currency",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF18334F)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Numeric Keypad
                    val keys = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", ".", "0", "DEL")
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        modifier = Modifier.height(240.dp),
                        userScrollEnabled = false
                    ) {
                        items(keys) { key ->
                            KeypadButton(key) {
                                if (key == "DEL") {
                                    if (amount.length > 1) amount = amount.dropLast(1)
                                    else if (amount.length == 1) amount = "0"
                                } else {
                                    if (amount == "0" && key != ".") amount = key
                                    else amount += key
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Pay Now Button
                    Button(
                        onClick = {
                            selectedAccount?.let { account ->
                                 //store data fo verify screen
                                transferViewModel.updateAmount(amount)
                                transferViewModel.updateSenderName(user?.firstName.toString())
                                transferViewModel.updateSenderNumber(user?.phoneNumber.toString())
                                transferViewModel.updateSenderAccNum(account?.accountNumber.toString())
                                navController.navigate(Routes.VERIFY_SCREEN)
                            }
                        },
                        enabled = selectedAccount != null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 32.dp)
                            .height(55.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF362513)),
                        shape = RoundedCornerShape(27.dp)
                    ) {
                        Text(text = "Pay Now", color = Color.White, fontSize = 18.sp)
                    }
                }
            }
        }

        if (showAccountSheet) {
            ModalBottomSheet(
                onDismissRequest = { showAccountSheet = false },
                containerColor = Color.White,
                dragHandle = null,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    // Header
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF362513))
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Select Account",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 32.dp)
                    ) {
                        items(accounts) { account ->
                            AccountItem(account) {
                                selectedAccount = account
                                showAccountSheet = false
                            }
                            HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f), thickness = 0.5.dp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AccountItem(account: AccountEntity, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = account.accountNumber,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = account.type,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = account.currency,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = if (account.currency == "KHR") Color(0xFFD4AF37) else Color(0xFF0D2B5E)
            )
            Text(
                text = "${account.balance}",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun ActionIcon(icon: Int, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(30.dp)
                .background(Color.White.copy(alpha = 0.1f), RoundedCornerShape(12.dp))
                .border(1.dp, Color.Gray.copy(alpha = 0.5f), RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(painter = painterResource(id = icon), contentDescription = label, tint = Color.White, modifier = Modifier.size(20.dp))
        }
        Text(text = label, color = Color.LightGray, fontSize = 12.sp, modifier = Modifier.padding(top = 4.dp))
    }
}

@Composable
fun KeypadButton(key: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .height(60.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (key == "DEL") {
            Icon(imageVector = Icons.AutoMirrored.Filled.Backspace, contentDescription = null, tint = Color(0xFF18334F))
        } else {
            Text(
                text = key,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF18334F)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPayScreen() {
    PayScreen(navController = NavController(LocalContext.current))
}
