package com.example.aceleda_bank.Component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aceleda_bank.R

@Preview(showBackground = true)
@Composable
fun Balancecard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .size(180.dp)
            .padding(16.dp)
            .border(
                width = 1.dp,
                color = Color.Yellow.copy(alpha = 0.5f),
                shape = RoundedCornerShape(16.dp),
            )
            .background(
                color = Color.Black.copy(alpha = 0.9f),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(120.dp)
            ) {

                Canvas(modifier = Modifier.fillMaxSize()) {

                    drawArc(
                        color = Color(0xFFD4A62A),
                        startAngle = 0f,
                        sweepAngle = 360f,
                        useCenter = false,
                        style = Stroke(width = 12f)
                    )

                    drawArc(
                        color=Color.Black,
                        startAngle = 240f,
                        sweepAngle=9f,
                        useCenter = false,
                        style = Stroke(width = 14f)
                    )

                    drawArc(
                        color=Color.Black,
                        startAngle = 265f,
                        sweepAngle=4f,
                        useCenter = false,
                        style = Stroke(width = 14f)
                    )

                    drawArc(
                        color = Color(0xFF29B6F6),
                        startAngle = 245f,
                        sweepAngle = 20f,
                        useCenter = false,
                        style = Stroke(width = 12f)
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

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = "Accounts",
                        color = Color.White
                    )
                }
            }
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                ) {
                    Row(
                        modifier = Modifier.padding(bottom = 15.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Total Balances",
                            color = Color.White,
                            modifier = Modifier
                        )
                        Spacer(modifier = Modifier.size(10.dp))
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 15.dp),
                    ) {
                        Text(
                            text = "100,000",
                            color = Color.White,
                        )
                        Spacer(modifier = Modifier.size(10.dp))
                        Image(
                            painter = painterResource(id = R.drawable.riel),
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(bottom = 15.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "1000",
                            color = Color.White,
                            modifier = Modifier
                        )
                        Spacer(modifier = Modifier.size(10.dp))
                        Image(
                            contentDescription = null,
                            painter = painterResource(id = R.drawable.dollar),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }