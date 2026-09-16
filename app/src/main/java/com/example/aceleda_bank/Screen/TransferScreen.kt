package com.example.aceleda_bank.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.QrCodeScanner
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
import com.example.aceleda_bank.Navigation.Routes
import com.example.aceleda_bank.R
import com.example.aceleda_bank.Room.AccountEntity
import com.example.aceleda_bank.ViewModel.AccountViewModel
import com.example.aceleda_bank.ViewModel.TransactionViewModel
import com.example.aceleda_bank.ViewModel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransferScreen(
    navController: NavController,
    transferViewModel: TransactionViewModel = hiltViewModel(),
    accountViewModel: AccountViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel()
) {
    val accounts by accountViewModel.allAccounts.collectAsState()
    var selectedAccount by remember { mutableStateOf<AccountEntity?>(null) }
    var showAccountSheet by remember { mutableStateOf(false) }
    val user by userViewModel.localUser.collectAsState()
    var toAccount by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var isPurposeEnabled by remember { mutableStateOf(false) }
    var isSaveToFavoritesEnabled by remember { mutableStateOf(false) }

    LaunchedEffect(accounts) {
        if (selectedAccount == null && accounts.isNotEmpty()) {
            selectedAccount = accounts.first()
        }
    }

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
                        .clickable { navController.popBackStack() }
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

            Spacer(modifier = Modifier.height(20.dp))

            // Icon and Title
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF18334F)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ac_logo),
                        contentDescription = null,
                        modifier = Modifier.size(35.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "ACLEDA Accounts | Phone Numbers",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Form Container
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                    .background(Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                ) {
                    // From Account
                    Text(text = "From Account", color = Color.Gray, fontSize = 14.sp)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showAccountSheet = true }
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = selectedAccount?.accountNumber ?: "Select Account",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF18334F)
                            )
                            Text(
                                text = if (selectedAccount != null) "${selectedAccount!!.balance} ${selectedAccount!!.currency}" else "",
                                fontSize = 14.sp,
                                color = Color(0xFFD4AF37)
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            tint = Color(0xFFD4AF37)
                        )
                    }
                    HorizontalDivider(thickness = 1.dp, color = Color.LightGray.copy(alpha = 0.5f))

                    Spacer(modifier = Modifier.height(20.dp))

                    // To Account
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextField(
                            value = toAccount,
                            onValueChange = { toAccount = it },
                            placeholder = { Text("To Account") },
                            modifier = Modifier.weight(1f),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.LightGray,
                                unfocusedIndicatorColor = Color.LightGray
                            ),
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Default.QrCodeScanner,
                                    contentDescription = null,
                                    tint = Color.LightGray,
                                    modifier = Modifier.clickable{
                                        navController.navigate(Routes.SCAN)
                                    }
                                )
                            }
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                                .clickable { },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.FavoriteBorder,
                                contentDescription = null,
                                tint = Color(0xFFD4AF37)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Amount
                    TextField(
                        value = amount,
                        onValueChange = { amount = it },
                        placeholder = { Text("Amount") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.LightGray,
                            unfocusedIndicatorColor = Color.LightGray
                        ),
                        trailingIcon = {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(Color(0xFFF0F0F0)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = if (selectedAccount?.currency == "KHR") "៛" else "$",
                                    color = Color(0xFF18334F),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Switches
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Purpose", color = Color(0xFF18334F))
                        Switch(
                            checked = isPurposeEnabled,
                            onCheckedChange = { isPurposeEnabled = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = Color(0xFF18334F)
                            )
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Save to Favorites", color = Color(0xFF18334F))
                        Switch(
                            checked = isSaveToFavoritesEnabled,
                            onCheckedChange = { isSaveToFavoritesEnabled = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = Color(0xFF18334F)
                            )
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    // OK Button
                    Button(
                        onClick = {
                            selectedAccount?.let { account ->
                                //store data fo verify screen
                                transferViewModel.updateAccountNumber( toAccount?: "")
                                transferViewModel.updateAmount(amount)
                                transferViewModel.updateSenderName(user?.firstName.toString())
                                transferViewModel.updateSenderNumber(user?.phoneNumber.toString())
                                transferViewModel.updateSenderAccNum(account?.accountNumber.toString())
                                navController.navigate(Routes.VERIFY_SCREEN)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF362513)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(text = "OK", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        if (showAccountSheet) {
            ModalBottomSheet(
                onDismissRequest = { showAccountSheet = false },
                containerColor = Color.White,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            ) {
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
@Preview(showBackground = true)
@Composable
fun TransferPreview(){
    TransferScreen(navController = NavController(LocalContext.current))
}
