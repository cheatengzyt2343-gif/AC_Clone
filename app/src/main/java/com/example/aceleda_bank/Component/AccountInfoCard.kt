package com.example.aceleda_bank.Component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.aceleda_bank.R
import androidx.compose.material3.HorizontalDivider
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AccountInfoCard(title: String,value: String,icon: Int){
    Column(modifier = Modifier
        .padding(start = 30.dp,top=30.dp)) {
        Text(text = title, color = Color.Gray)
        Spacer(modifier = Modifier.size(10.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id=icon),
                contentDescription = null,
                modifier = Modifier
                    .padding(start=14.dp)
                    .size(25.dp)
            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(text=value,color=Color.Black, fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }
        HorizontalDivider(
            thickness = 1.dp,
            color = Color.LightGray
        )
    }
}