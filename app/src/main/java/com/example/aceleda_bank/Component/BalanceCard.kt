package com.example.aceleda_bank.Component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.aceleda_bank.Navigation.Routes
import com.example.aceleda_bank.R
import com.example.aceleda_bank.Room.AccountEntity
import com.example.aceleda_bank.ViewModel.AccountViewModel

@Composable
fun Balancecard(navController: NavController,accountViewModel: AccountViewModel = hiltViewModel()) {
    //all account info
    val accounts by accountViewModel.allAccounts.collectAsState()
    Box(
        modifier = Modifier
            .clickable {
                navController.navigate(Routes.PIN_BALANCE)
            }
            .fillMaxWidth()
            .size(180.dp)
            .padding(16.dp)
            .border(
                width = 1.dp,
                color = Color(0xFFD4A44D).copy(alpha = 0.8f),
                shape = RoundedCornerShape(16.dp),
            )
            .background(
                color = Color.Black.copy(alpha = 0.9f),
                shape = RoundedCornerShape(16.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(start=16.dp)
                    .size(120.dp)
            ) {

                Canvas(modifier = Modifier
                    .size(110.dp)) {

                    drawArc(
                        color = Color(0xFFD4A62A),
                        startAngle = 0f,
                        sweepAngle = 360f,
                        useCenter = false,
                        style = Stroke(width = 18f)
                    )

                    drawArc(
                        color=Color.Black,
                        startAngle = 240f,
                        sweepAngle=9f,
                        useCenter = false,
                        style = Stroke(width = 18f)
                    )

                    drawArc(
                        color=Color.Black,
                        startAngle = 265f,
                        sweepAngle=4f,
                        useCenter = false,
                        style = Stroke(width = 18f)
                    )

                    drawArc(
                        color = Color(0xFF29B6F6),
                        startAngle = 245f,
                        sweepAngle = 20f,
                        useCenter = false,
                        style = Stroke(width = 18f)
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Icon(
                        painter = painterResource(R.drawable.wallet_solid_full__1_),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(48.dp)
                    )


                    Text(
                        text = "Accounts",
                        color = Color.White
                    )
                }
            }
                Column(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                ) {
                    Row(
                        modifier = Modifier.padding(top = 15.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Total Balances",
                            color = Color.White,
                            modifier = Modifier
                        )
                        Spacer(modifier = Modifier.size(6.dp))
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .background(
                                    color = Color.Yellow.copy(alpha = 0.18f),
                                    shape = CircleShape
                                )
                        ) {

                            Image(
                                painter = painterResource(id = R.drawable.eyes),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(20.dp)
                                    .align(Alignment.Center)
                            )
                        }
                    }

                    accounts.forEach { account->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = 10.dp),
                        ) {
                            Text(
                                text = "${account?.balance}",
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.size(10.dp))
                            val icon = if (account.currency == "USD") {
                                R.drawable.dollar_mini
                            } else {
                                R.drawable.cambodia_riel_crop
                            }

                            Image(
                                painter = painterResource(id = icon),
                                contentDescription = null,
                                modifier = Modifier.size(40.dp),
                                colorFilter = ColorFilter.tint(Color.White)
                            )
                        }
                    }
                }
            }
        }
    }